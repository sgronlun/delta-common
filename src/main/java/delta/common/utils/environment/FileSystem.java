package delta.common.utils.environment;

import java.io.File;

/**
 * @author DAM
 */
public abstract class FileSystem
{
  private static final String DELTA_DIR=".delta";
  private static File _deltaHomeDir;
  private static File _dataDir;
  private static File _testDataDir;
  private static File _tmpDir;

  /**
   * Get the root directory for delta.
   * @return the root directory for delta.
   */
  public static File getDeltaHomeDir()
  {
    if (_deltaHomeDir==null)
    {
      String dataDir=System.getenv("DELTA_HOME");
      if (dataDir!=null)
      {
        _deltaHomeDir=new File(dataDir);
      }
      else
      {
        System.err.println("DELTA_HOME is not defined !");
      }
    }
    return _deltaHomeDir;
  }

  /**
   * Get the root directory for data.
   * @return the root directory for data.
   */
  public static File getDataDir()
  {
    if (_dataDir==null)
    {
      String dataDir=System.getenv("DELTA_DATA_DIR");
      if (dataDir==null)
      {
        File dir=getDeltaHomeDir();
        _dataDir=new File(dir,"data");
      }
      else
      {
        _dataDir=new File(dataDir);
      }
      _dataDir.mkdirs();
    }
    return _dataDir;
  }

  /**
   * Get the root directory for test data.
   * @return the root directory for test data.
   */
  public static File getTestDataDir()
  {
    if (_testDataDir==null)
    {
      File dataDir=getDataDir();
      _testDataDir=new File(dataDir,"tests");
      _testDataDir.mkdirs();
    }
    return _testDataDir;
  }

  /**
   * Get the tmp directory.
   * @return the tmp directory.
   */
  public static File getTmpDir()
  {
    if (_tmpDir==null)
    {
      String tmpDir=System.getProperty("java.io.tmpdir");
      _tmpDir=new File(tmpDir);
    }
    return _tmpDir;
  }

  public static File getModuleDir(String appName,String moduleName)
  {
    User localUser=User.getLocalUser();
    File userDir=localUser.getHomeDir();
    File deltaDir=new File(userDir,DELTA_DIR);
    File appDir=new File(deltaDir,appName);
    File moduleDir=new File(appDir,moduleName);
    return moduleDir;
  }
}
