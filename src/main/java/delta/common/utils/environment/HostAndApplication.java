package delta.common.utils.environment;

/**
 * Address of a system entity (host+application).
 * @author DAM
 */
public class HostAndApplication
{
  private String _key;
  private String _appId;
  private String _osHostName;

  private static HostAndApplication _me=null;

  /**
   * Get a <tt>HostAndApplication</tt> instance that reflects the current application
   * and host.
   * @return A <tt>HostAndApplication</tt>.
   */
  public static HostAndApplication me()
  {
    if (_me==null)
    {
      _me=new HostAndApplication();
    }
    return _me;
  }

  private HostAndApplication()
  {
    JavaProcess me=JavaProcess.getLocalJavaProcess();
    _appId=me.getName();
    Host localHost=Host.getLocalHost();
    _osHostName=localHost.getName();
    _key=_appId+"#"+_osHostName;
  }

  /**
   * Full constructor.
   * @param appID application ID.
   * @param osHostName OS-level hostname.
   */
  public HostAndApplication(String appID, String osHostName)
  {
    _appId=appID;
    _osHostName=osHostName;
    _key=_appId+"#"+_osHostName;
  }

  /**
   * Get the application ID.
   * @return the application ID.
   */
  public String getApplicationID()
  {
    return _appId;
  }

  /**
   * Get the hostname.
   * @return the hostname.
   */
  public String getHostName()
  {
    return _osHostName;
  }

  /**
   * Standard toString() method.
   * @return Stringified representation of this object.
   */
  @Override
  public String toString()
  {
    return "[Host="+_osHostName+", App="+_appId+"]";
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof HostAndApplication)
    {
      HostAndApplication h=(HostAndApplication)obj;
      return ((h.getApplicationID().equals(_appId))&&(h.getHostName().equals(_osHostName)));
    }
    return false;
  }

  /**
   * Standard hashCode method.
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    return _key.hashCode();
  }
}
