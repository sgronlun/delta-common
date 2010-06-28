package delta.common.utils.configuration;

import java.io.File;

import org.apache.log4j.Logger;

import delta.common.utils.files.TextFileReader;
import delta.common.utils.files.TextFileWriter;
import delta.common.utils.traces.UtilsLoggers;

public class ConfigurationFileIO
{
  private static final Logger _logger=UtilsLoggers.getCfgLogger();

  public static boolean loadSection(File f, Configuration config, String sectionName)
  {
    config.removeSection(sectionName);
    return readFromFile(f,config,sectionName);
  }

  public static boolean loadFile(File f, Configuration config)
  {
    config.clear();
    return readFromFile(f,config,null);
  }

  private static boolean readFromFile(File f, Configuration config, String sectionToRead)
  {
    TextFileReader reader=new TextFileReader(f);
    if (!reader.start())
    {
      _logger.error("Configuration file not found or unreadable ["+f+"]");
      return false;
    }

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
          _logger.error("Invalid line "+lineNumber);
        }
      }
      else
      {
        int index=line.indexOf('=');
        if(index!=-1)
        {
          if((sectionToRead==null)||(sectionToRead.equals(section)))
          {
            config.putStringValue(section, line.substring(0, index), line.substring(index+1));
          }
        }
        else
        {
          _logger.error("Invalid line "+lineNumber);
        }
      }
    }
    reader.terminate();
    return true;
  }

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
