package delta.common.utils.configuration;

import java.io.File;

import org.apache.log4j.Logger;

import delta.common.utils.files.TextFileReader;
import delta.common.utils.files.TextFileWriter;

/**
 * I/O methods for configuration objects.
 * @author DAM
 */
public class ConfigurationFileIO
{
  private static final Logger LOGGER=Logger.getLogger(ConfigurationFileIO.class);

  /**
   * Load the contents of a file into a configuration.
   * @param f File to read.
   * @return A configuration or <code>null</code>.
   */
  public static Configuration loadFile(File f)
  {
    TextFileReader reader=new TextFileReader(f);
    if (!reader.start())
    {
      LOGGER.error("Configuration file not found or unreadable ["+f+"]");
      return null;
    }
    Configuration config=new Configuration();
    String section="bidon";
    String line=null;
    int lineNumber=0;
    while (true)
    {
      line=reader.getNextLine();
      lineNumber++;
      if(line==null) break;
      // Handle empty lines
      if (line.equals("")) continue;
      // Handle comment lines
      if(line.charAt(0)==';') continue;
      if(line.charAt(0)=='#') continue;

      if(line.charAt(0)=='[')
      {
        // new section
        int index=line.indexOf(']');
        if(index!=-1)
        {
          section=line.substring(1, index);
        }
        else
        {
          LOGGER.error("Invalid line "+lineNumber);
        }
      }
      else
      {
        int index=line.indexOf('=');
        if(index!=-1)
        {
          config.putStringValue(section, line.substring(0, index), line.substring(index+1));
        }
        else
        {
          LOGGER.error("Invalid line "+lineNumber);
        }
      }
    }
    reader.terminate();
    return config;
  }

  /**
   * Write a configuration to a file.
   * @param f File to write to.
   * @param config Configuration to use.
   * @return <code>true</code> if it was successfull, <code>false</code> otherwise.
   */
  public static boolean writeToFile(File f, Configuration config)
  {
    if (f==null) return false;
    File parent=f.getParentFile();
    parent.mkdirs();
    TextFileWriter writer=new TextFileWriter(f);
    if (writer.start())
    {
      String sectionName;
      String variableName;
      String[] sectionNames;
      String[] variableNames;
      sectionNames=config.getSectionNames();
      for(int i=0;i<sectionNames.length;i++)
      {
        sectionName=sectionNames[i];
        writer.writeNextLine("["+sectionName+"]");
        variableNames=config.getVariableNames(sectionName);
        for(int j=0;j<variableNames.length;j++)
        {
          variableName=variableNames[j];
          writer.writeNextLine(variableName+"="+config.getStringValue(sectionName,variableName,null));
        }
        writer.writeNextLine("");
      }
      writer.terminate();
      return true;
    }
    return false;
  }
}
