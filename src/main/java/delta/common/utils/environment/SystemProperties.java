package delta.common.utils.environment;

/**
 * Provides simplified access to system properties.
 * @author DAM
 */
public abstract class SystemProperties
{
  /**
   * Get the name of the underlying architecture.
   * @return the name of the underlying architecture.
   */
  public static String getArchitecture()
  {
    String ret=System.getProperty("os.arch");
    return ret;
  }

/*
  java.io.tmpdir  Default temp file path
  file.separator  File separator ("/" on UNIX)
  path.separator  Path separator (":" on UNIX)
*/
}
