package delta.common.utils.configuration;

import java.io.File;

import delta.common.utils.environment.User;

public class Configurations
{
  public static final String USER_CONFIGURATION="USER";

  private static Configuration _userConfiguration;

  public static Configuration getConfiguration()
  {
    return getUserConfiguration();
  }

  public static Configuration getUserConfiguration()
  {
    if(_userConfiguration==null)
    {
      User user=User.getLocalUser();
      File userHome=user.getHomeDir();
      File cfgFile=new File(userHome,"delta.cfg");
      _userConfiguration=buildConfigFile(cfgFile);
    }
    return _userConfiguration;
  }

  public static Configuration buildConfigFile(File pathName)
  {
    Configuration ret=new Configuration();
    ConfigurationFileIO.loadFile(pathName,ret);
    ret.resolveValues();
    return ret;
  }
}
