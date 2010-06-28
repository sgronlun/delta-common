package delta.common.utils.environment;

/**
 * Represents an Operating System.
 * @author DAM
 */
public final class OS
{
  private static final String OS_VERSION="os.version";
  private static final String OS_NAME="os.name";

  private static OS _localOS=null;

  private String _version;
  private OSType _osType;

  /**
   * Private constructor.
   * Builds an instance that represents the local OS (the OS on which this
   * process runs).
   */
  private OS()
  {
    _version=System.getProperty(OS_VERSION);
    String osName=System.getProperty(OS_NAME);
    _osType=OSType.getOSTypeByName(osName);
  }

  /**
   * Get the OS type for this OS.
   * @return the OS type for this OS.
   */
  public OSType getOSType()
  {
    return _osType;
  }

  /**
   * Get the version of the underlying OS.
   * @return the version of the underlying OS.
   */
  public String getOSVersion()
  {
    return _version;
  }

  /**
   * Get the OS that runs this process.
   * @return the OS that runs this process.
   */
  public static OS getLocalOS()
  {
    if (_localOS==null)
    {
      _localOS=new OS();
    }
    return _localOS;
  }
}
