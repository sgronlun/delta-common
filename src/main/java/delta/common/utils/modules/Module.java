package delta.common.utils.modules;

import java.io.File;

import delta.common.utils.configuration.Configuration;
import delta.common.utils.configuration.ConfigurationFileIO;
import delta.common.utils.environment.FileSystem;
import delta.common.utils.environment.JavaProcess;

/**
 * @author DAM
 */
public class Module
{
  private static final String CONFIG_FILENAME="config.ini";
  private String _name;
  private File _moduleRootDir;
  private File _configFile;
  private Configuration _cfg;

  Module(String name)
  {
    _name=name;
  }

  /**
   * Get the configuration associated with this module.
   * @return the configuration associated with this module.
   */
  public Configuration getConfiguration()
  {
    if (_cfg==null)
    {
      _cfg=buildConfiguration();
    }
    return _cfg;
  }

  private File getModuleRootDir()
  {
    if (_moduleRootDir==null)
    {
      JavaProcess localJavaProcess=JavaProcess.getLocalJavaProcess();
      String name=localJavaProcess.getName();
      _moduleRootDir=FileSystem.getModuleDir(name,_name);
    }
    return _moduleRootDir;
  }

  private File getConfigurationFile()
  {
    if (_configFile==null)
    {
      File moduleRootDir=getModuleRootDir();
      _configFile=new File(moduleRootDir,CONFIG_FILENAME);
    }
    return _configFile;
  }

  private Configuration buildConfiguration()
  {
    File configFile=getConfigurationFile();
    Configuration cfg=new Configuration();
    ConfigurationFileIO.loadFile(configFile,cfg);
    return cfg;
  }

  /**
   * Synchronize configuration to file.
   */
  public void syncConfiguration()
  {
    Configuration cfg=getConfiguration();
    File cfgFile=getConfigurationFile();
    ConfigurationFileIO.writeToFile(cfgFile,cfg);
  }
}
