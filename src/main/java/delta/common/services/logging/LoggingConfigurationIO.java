package delta.common.services.logging;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Level;

import delta.common.utils.environment.FileSystem;
import delta.common.utils.files.TextFileReader;
import delta.common.utils.files.TextFileWriter;
import delta.common.utils.traces.LoggingConstants;

/**
 * Logging configuration file I/O.
 * @author DAM
 */
public abstract class LoggingConfigurationIO
{
  /**
   * Loads a logging configuration file into a <code>LoggingConfiguration</code> instance.
   * @param appName Targeted application.
   * @return A <code>LoggingConfiguration</code> instance or <code>null</code>.
   */
  public static LoggingConfiguration loadLoggingConfiguration(String appName)
  {
    LoggingConfiguration ret=null;
    File cfgFile=getLoggingConfigurationFile(appName);
    if ((cfgFile.exists()) && (cfgFile.canRead()))
    {
      ret=new LoggingConfiguration();
      TextFileReader reader=new TextFileReader(cfgFile);
      if (reader.start())
      {
        String line=null;
        String loggerName;
        String levelName;
        int index;
        while (true)
        {
          line=reader.getNextLine();
          if (line==null) break;
          index=line.indexOf('=');
          loggerName=line.substring(0,index);
          levelName=line.substring(index+1);
          ret.setLevel(loggerName,levelName);
        }
        reader.terminate();
      }
    }
    return ret;
  }

  /**
   * Save a logging configuration to a file.
   * @param appName Targeted application.
   * @param cfg Logging configuration to save.
   * @return <code>true</code> if it was successfully saved, <code>false</code> otherwise.
   */
  public static boolean saveLoggingConfiguration(String appName, LoggingConfiguration cfg)
  {
    boolean ret=false;
    File cfgFile=getLoggingConfigurationFile(appName);
    cfgFile.getParentFile().mkdirs();
    TextFileWriter writer=new TextFileWriter(cfgFile);
    if (writer.start())
    {
      List<String> setLoggers=cfg.getSetLoggers();
      String loggerName;
      Level level;
      String levelName;
      String line;
      for(Iterator<String> it=setLoggers.iterator();it.hasNext();)
      {
        loggerName=it.next();
        level=cfg.getLevel(loggerName);
        if (level!=null)
        {
          levelName=level.toString();
          line=loggerName+"="+levelName;
          writer.writeNextLine(line);
        }
      }
      writer.terminate();
    }
    return ret;
  }

  /**
   * Get the file used to save the logging configuration file of an application.
   * @param appName Targeted application.
   * @return A file.
   */
  public static File getLoggingConfigurationFile(String appName)
  {
    String moduleName=LoggingConstants.MODULE_NAME;
    File dir=FileSystem.getModuleDir(appName,moduleName);
    File loggingCfgFile=new File(dir,"config.ini");
    return loggingCfgFile;
  }
}
