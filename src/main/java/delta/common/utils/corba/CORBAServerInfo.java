package delta.common.utils.corba;

/**
 * CORBA server information.
 * @author DAM
 */
public class CORBAServerInfo
{
  private String _serverName; // = application name
  private String _hostName;
  private int _port;

  /**
   * Constructor.
   * @param serverName Server name.
   * @param host Name of host that runs the server.
   * @param port Port of the server.
   */
  public CORBAServerInfo(String serverName, String host, int port)
  {
    _serverName=serverName;
    _hostName=host;
    _port=port;
  }

  /**
   * Get the server name.
   * @return the server name.
   */
  public String getServerName()
  {
    return _serverName;
  }

  /**
   * Get the host name for this server.
   * @return the host name for this server.
   */
  public String getHostName()
  {
    return _hostName;
  }

  /**
   * Get the port for this server.
   * @return the port for this server.
   */
  public int getPort()
  {
    return _port;
  }
}
