package delta.common.utils.environment;

import java.io.File;

/**
 * Represents a user.
 * @author DAM
 */
public final class User
{
  private static final String USER_NAME="user.name";
  private static final String USER_HOME="user.home";
  private static final String USER_DIR="user.dir";

  private static User _localUser=null;

  private String _name;
  private File _homeDir;
  private File _currentWorkingDir;

  /**
   * Private constructor.
   * Builds an instance that represents the local user (the user who runs this process).
   */
  private User()
  {
    _name=System.getProperty(USER_NAME);
    String homeDir=System.getProperty(USER_HOME);
    _homeDir=new File(homeDir);
    String cwd=System.getProperty(USER_DIR);
    _currentWorkingDir=new File(cwd);
  }

  /**
   * Get the login of this user.
   * @return the login of this user.
   */
  public String getLogin()
  {
    return _name;
  }

  /**
   * Get the home directory of this user.
   * @return the home directory of this user.
   */
  public File getHomeDir()
  {
    return _homeDir;
  }

  /**
   * Get the current working directory of this user.
   * @return the current working of this user.
   */
  public File getCurrentWorkingDir()
  {
    return _currentWorkingDir;
  }

  /**
   * Get the user that runs this process.
   * @return the user that runs this process.
   */
  public static User getLocalUser()
  {
    if (_localUser==null)
    {
      _localUser=new User();
    }
    return _localUser;
  }
}
