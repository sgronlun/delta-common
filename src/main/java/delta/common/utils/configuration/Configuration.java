package delta.common.utils.configuration;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import delta.common.utils.BooleanTools;
import delta.common.utils.NumericTools;
import delta.common.utils.misc.HexString2BufferCodec;
import delta.common.utils.traces.UtilsLoggers;

/**
 * This class is a tool that reads a Windows '.ini' like configuration file and
 * allows information retrieval given a section name and a entry name.<br>
 * The config file is made of sections, which are in turn composed of entries.<br>
 * Each section has a name and 0 or more entries.<br>
 * Each entry is composed of an entry name (variable) and an entry value
 * (possibly void) separated by a '=' symbol.<br>
 * ';' at the beginning of a line introduces a comment Value part of an entry
 * may reference the value of another entry of the same config file (same
 * section or another). You may use a special section name 'ENV' to retrieve
 * values from the OS environment variable.<br>
 *
 * <pre>
 *  [SECTION1]
 *  VAR1=contents
 *
 *  [SECTION2]
 *  VAR1=MY
 *  VAR2=PROJECT
 *  ; VAR3 resolves to the string 'MY PROJECT'
 *  VAR3=${VAR1} ${VAR2}
 *  ; VAR4 resolves to the string 'Incredible contents'
 *  VAR4=Incredible ${SECTION1|VAR1}
 *  ; VAR5 resolves to the string 'My OS username is root' (for instance)
 *  VAR5=My OS username is ${ENV|USER}
 * </pre>
 */
public class Configuration
{
  private static final Logger _logger=UtilsLoggers.getCfgLogger();

  private HashMap<String,HashMap<String,String>> _infos;
  private SimpleCryptDecrypt _cryptManager;

  private static final String BEGIN_VARIABLE_NAME="${";
  private static final String END_VARIABLE_NAME="}";
  private static final String SECTION_ENTRY_SEPARATOR="|";
  private static final String ENV="ENV";
  private static final String CRYPT="CRYPT";
  /**
   * IF_EXISTS keyword.
   */
  public static final String IF_EXISTS="IF_EXISTS";

  /**
   * Constructor.
   */
  public Configuration()
  {
    _infos=new HashMap<String,HashMap<String,String>>();
    _cryptManager=new SimpleCryptDecrypt();
  }

  /**
   * Method that reads a configuration value.
   * @param sectionName Section.
   * @param key Key.
   * @param defaultValue Default value.
   * @return the value
   * @throws IllegalArgumentException if the arguments are not valid
   */
  public String getStringValue(String sectionName, String key,String defaultValue)
  {
    String info=defaultValue;
    if (sectionName==null)
    {
      throw new IllegalArgumentException("section_p==null");
    }
    if (key==null)
    {
      throw new IllegalArgumentException("key_p==null");
    }
    if (sectionName.length()==0)
    {
      throw new IllegalArgumentException("section_p.equals(\"\")");
    }
    if (key.length()==0)
    {
      throw new IllegalArgumentException("key_p.equals(\"\")");
    }

    HashMap<String,String> section=_infos.get(sectionName);
    if (section!=null)
    {
      info=section.get(key);
    }
    return info;
  }

  /**
   * Method that reads a configuration integer value.
   * @param section the section
   * @param key the key
   * @param defaultValue Default value (used if no entry found).
   * @return the value
   */
  public int getIntValue(String section, String key, int defaultValue)
  {
    String value=getStringValue(section,key,null);
    int ret=NumericTools.parseInt(value,defaultValue);
    return ret;
  }

  /**
   * Method that reads a configuration double value.
   * @param section the section
   * @param key the key
   * @param defaultValue Default value (used if no entry found).
   * @return the value
   */
  public double getDoubleValue(String section, String key, double defaultValue)
  {
    String value=getStringValue(section,key,null);
    double ret=NumericTools.parseDouble(value,defaultValue);
    return ret;
  }

  /**
   * Method that reads a file value.
   * @param sectionName Section name.
   * @param variableName Variable name.
   * @param defaultFile Default value if not found.
   * @return A file.
   */
  public File getFileValue(String sectionName, String variableName, File defaultFile)
  {
    File ret=defaultFile;
    String value=getStringValue(sectionName,variableName,null);
    if (value!=null)
    {
      String trimedValue=value.trim();
      ret=new File(trimedValue);
    }
    return ret;
  }

  /**
   * Method that reads a configuration boolean value.
   * @param section the section
   * @param key the key
   * @param defaultValue Default value (used if no entry found).
   * @return the value (null if section/entry not found)
   */
  public boolean getBooleanValue(String section, String key, boolean defaultValue)
  {
    String value=getStringValue(section,key,null);
    boolean ret=BooleanTools.parseBoolean(value,defaultValue);
    return ret;
  }

  /**
   * Set the <tt>value</tt> for the variable <tt>variableName</tt> in the
   * section <tt>sectionName</tt>.
   * @param sectionName Name of the section to use.
   * @param variableName name of the variable to set.
   * @param value Value to use.
   */
  public void putStringValue(String sectionName, String variableName, String value)
  {
    if (_infos!=null)
    {
      HashMap<String,String> section=_infos.get(sectionName);
      if (section==null)
      {
        section=new HashMap<String,String>();
        _infos.put(sectionName,section);
      }
      section.put(variableName,value);
    }
  }

  /**
   * Get section names.
   * @return an array of section names.
   */
  public String[] getSectionNames()
  {
    ArrayList<String> sortedSections_l=new ArrayList<String>(_infos.keySet());
    Collections.sort(sortedSections_l);
    String[] ret=new String[sortedSections_l.size()];
    return sortedSections_l.toArray(ret);
  }

  /**
   * Get variable names for a given section.
   * @param sectionName name of section to use.
   * @return an array of variable names.
   */
  public String[] getVariableNames(String sectionName)
  {
    HashMap<String,String> section=_infos.get(sectionName);
    if (section!=null)
    {
      ArrayList<String> sortedVariableNames=new ArrayList<String>(section.keySet());
      Collections.sort(sortedVariableNames);
      String[] ret=new String[sortedVariableNames.size()];
      return sortedVariableNames.toArray(ret);
    }
    return new String[0];
  }

  /**
   * Removes the section whose name is <tt>sectionName</tt> from this
   * configuration.
   * @param sectionName Name of the section to remove.
   */
  public void removeSection(String sectionName)
  {
    _infos.remove(sectionName);
  }

  /**
   * Removes all the sections from this configuration.
   */
  public void clear()
  {
    _infos.clear();
  }

  /**
   * Resolve indirect values.
   * @return ok (true) or not (false)
   */
  boolean resolveValues()
  {
    boolean ret=true;

    int nbUnresolved=0;
    boolean resolutionOccurred=false;

    do
    {
      nbUnresolved=0;
      resolutionOccurred=false;
      String sectionName, key, value;
      for(Enumeration<String> it=Collections.enumeration(_infos.keySet());it.hasMoreElements();)
      {
        sectionName=it.nextElement();
        HashMap<String,String> section=_infos.get(sectionName);
        for(Enumeration<String> it2=Collections.enumeration(section
            .keySet());it2.hasMoreElements();)
        {
          key=it2.nextElement();
          value=section.get(key);
          if (value.indexOf(BEGIN_VARIABLE_NAME)!=-1)
          {
            boolean returnResolve=resolveValue(sectionName,key,value);
            if (returnResolve)
            {
              resolutionOccurred=true;
            }
            else
            {
              nbUnresolved++;
            }
          }
        }
      }
    }
    while (resolutionOccurred);

    if (nbUnresolved>0)
    {
      ret=false;
      _logger.error("!!! "+nbUnresolved+" entries unresolved !");
    }
    return ret;
  }

  /**
   * Try to resolve indirect value.
   * @param sectionName section name of the value to resolve
   * @param entryName entry name of the value to resolve
   * @param value value to resolve
   * @return resolved (true) or not (false)
   */
  boolean resolveValue(String sectionName, String entryName, String value)
  {
    boolean ret=false;
    String newValue=value;
    String[] sectionEntry=new String[2];
    while (true)
    {
      int[] beginEnd=findSectionEntryReference(newValue,sectionName,
          sectionEntry);
      int found=beginEnd[0];
      if (found>=0)
      {
        if ((sectionEntry[0].equals(sectionName)) &&(sectionEntry[1].equals(entryName)))
        {
          _logger.error("Self reference for entry ["+entryName+"] of section ["+sectionName+"]");
          ret=false; // Even if resolve took place : this is a fatal error
          break;
        }
        String tmpValue=null;
        if (sectionEntry[0].equals(ENV))
        {
          tmpValue=System.getenv(sectionEntry[1]);
        }
        else
        {
          if (sectionEntry[0].equals(CRYPT))
          {
            String cryptedBuffer=sectionEntry[1];
            byte[] buffer=HexString2BufferCodec.stringToBuffer(cryptedBuffer);
            tmpValue=_cryptManager.decrypt(buffer);
          }
          else
          {
            if (sectionEntry[0].equals(IF_EXISTS))
            {
              tmpValue=handleIfExists(sectionEntry[1]);
            }
            else
            {
              tmpValue=getStringValue(sectionEntry[0],sectionEntry[1],null);
            }
          }
        }
        if (tmpValue!=null)
        {
          String solvedValue=newValue.substring(0,found)+tmpValue;
          int endIndex=beginEnd[1];
          if (endIndex+1<newValue.length())
          {
            solvedValue+=newValue.substring(endIndex+SECTION_ENTRY_SEPARATOR.length(),newValue.length());
          }
          newValue=solvedValue;
          // We succeeded at least one resolve
          ret=true;
        }
        else
        {
          _logger.error("Entry unresolved ["+sectionEntry[0]+SECTION_ENTRY_SEPARATOR+sectionEntry[1]+"]");
          break;
        }
      }
      else
      {
        break;
      }
    }

    if (ret)
    {
      putStringValue(sectionName,entryName,newValue);
    }

    return ret;
  }

  private void splitSectionEntry(String toSplit, String defaultSection,
      String[] resultSectionEntry)
  {
    resultSectionEntry[0]=defaultSection;
    resultSectionEntry[1]=null;
    int indexPipe=toSplit.indexOf(SECTION_ENTRY_SEPARATOR);
    if (indexPipe!=-1)
    {
      resultSectionEntry[0]=toSplit.substring(0,indexPipe);
      resultSectionEntry[1]=toSplit.substring(indexPipe+SECTION_ENTRY_SEPARATOR.length(),toSplit.length());
    }
    else
    {
      resultSectionEntry[1]=toSplit;
    }
  }

  private int[] findSectionEntryReference(String value,
      String defaultSectionName, String[] resultSectionEntry)
  {
    int found=value.indexOf(BEGIN_VARIABLE_NAME);
    int endIndex=-1;
    if (found>=0)
    {
      // Found something
      endIndex=value.indexOf(END_VARIABLE_NAME,found
          +BEGIN_VARIABLE_NAME.length());
      if (endIndex==-1)
      {
        endIndex=value.length();
      }
      String key=value.substring(found+BEGIN_VARIABLE_NAME.length(),endIndex);
      // Get section/entry
      splitSectionEntry(key,defaultSectionName,resultSectionEntry);
    }
    return new int[] {found,endIndex};
  }

  private String handleIfExists(String ifExistsClause)
  {
    String ret=null;
    StringTokenizer st=new StringTokenizer(ifExistsClause,",");
    if (st.countTokens()==4)
    {
      String sectionToTest=st.nextToken();
      String entryToTest=st.nextToken();
      String value1=st.nextToken();
      String value2=st.nextToken();
      ret=value2;
      String testValue=getStringValue(sectionToTest,entryToTest,null);
      if (testValue!=null)
      {
        ret=value1;
      }
    }
    return ret;
  }

  /**
   * Prints the contents of this configuration to the specified stream.
   * @param ps Stream to print to.
   */
  public void dump(PrintStream ps)
  {
    ps.println("Number of sections : "+_infos.size());
    String sectionName;
    String variableName;
    String[] sectionNames=getSectionNames();
    String[] variableNames;
    for(int i=0;i<sectionNames.length;i++)
    {
      sectionName=sectionNames[i];
      ps.println("["+sectionName+"]");
      variableNames=getVariableNames(sectionName);
      for(int j=0;j<variableNames.length;j++)
      {
        variableName=variableNames[j];
        ps.println("\t"+variableName+"="+getStringValue(sectionName,variableName,""));
      }
    }
  }
}
