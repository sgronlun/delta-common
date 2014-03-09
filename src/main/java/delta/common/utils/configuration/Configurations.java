package delta.common.utils.configuration;

import java.io.File;

import delta.common.utils.environment.User;

/**
 * Access to configurations.
 * @author DAM
 */
public class Configurations
{
  private static Configuration _userConfiguration;

  /**
   * Get the default configuration.
   * @return the default configuration.
   */
  public static Configuration getConfiguration()
  {
    return getUserConfiguration();
  }

  /**
   * Get the local user configuration.
   * @return the local user configuration.
   */
  public static Configuration getUserConfiguration()
  {
    if(_userConfiguration==null)
    {
      User user=User.getLocalUser();
      File userHome=user.getHomeDir();
      File cfgFile=new File(userHome,"delta.cfg");
      if (cfgFile.canRead())
      {
        _userConfiguration=buildConfigFile(cfgFile);
      }
      else
      {
        _userConfiguration=new Configuration();
      }
    }
    return _userConfiguration;
  }

  /**
   * Get a configuration from a file.
   * @param pathName File to read.
   * @return A configuration or <code>null</code>.
   */
  public static Configuration buildConfigFile(File pathName)
  {
    Configuration ret=ConfigurationFileIO.loadFile(pathName);
    if (ret!=null)
    {
      ret.resolveValues();
    }
    return ret;
  }
}
