package delta.common.utils.environment;

import java.io.File;

/**
 * Represents the underlying filesystem.
 * @author DAM
 */
public abstract class FileSystem
{
  private static final String USER_DIR="user.dir";
  private static final String TMP_DIR="java.io.tmpdir";

  /**
   * Get the tmp directory.
   * @return the tmp directory.
   */
  public static File getTmpDir()
  {
    String tmpDirStr=System.getProperty(TMP_DIR);
    File tmpDir=new File(tmpDirStr);
    return tmpDir;
  }

  /**
   * Get the current working directory of this user.
   * @return the current working of this user.
   */
  public File getCurrentWorkingDir()
  {
    String cwd=System.getProperty(USER_DIR);
    File currentWorkingDir=new File(cwd);
    return currentWorkingDir;
  }
}
