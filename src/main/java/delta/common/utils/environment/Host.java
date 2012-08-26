package delta.common.utils.environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Represents an host.
 * @author DAM
 */
public class Host
{
  /**
   * Alias for the local host.
   */
  public static final String LOCALHOST_ALIAS="localhost";
  private static Host _localHost=null;

  private String _name;

  /**
   * Constructor.
   * @param name Name of this host.
   */
  public Host(String name)
  {
    _name=name;
  }

  /**
   * Private constructor.
   * Builds an instance that represents the local host (the host on which this
   * process runs).
   */
  private Host()
  {
    _name=LOCALHOST_ALIAS;
    try
    {
      String name=InetAddress.getLocalHost().getHostName();
      int firstPoint=name.indexOf('.');
      if (firstPoint>=0)
      {
        name=name.substring(0,firstPoint);
      }
      _name=name;
    }
    catch(UnknownHostException uhe_l)
    {
      uhe_l.printStackTrace(System.err);
    }
  }

  /**
   * Get the name of this host.
   * @return the name of this host.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the host that runs this process.
   * @return the host that runs this process.
   */
  public static Host getLocalHost()
  {
    if (_localHost==null)
    {
      _localHost=new Host();
    }
    return _localHost;
  }
}
