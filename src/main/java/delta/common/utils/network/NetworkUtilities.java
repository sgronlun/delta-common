package delta.common.utils.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Set of misc. network-related utilities.
 * These utilities include :
 * <ul>
 * <li>getHostName : returns the current host's name.
 * <li>getIPForName : return the IP address of an host.
 * </ul>
 * @author DAM
 */
public class NetworkUtilities
{
  /**
   * Get the IP address of an host.
   * @param hostName Host's name.
   * @return the IP address of an host.
   */
  public static String getIPForName(String hostName)
  {
    String ip=null;
    try
    {
      InetAddress address=InetAddress.getByName(hostName);
      if (address!=null)
      {
        ip=address.getHostAddress();
      }
    }
    catch (UnknownHostException ex)
    {
      return "127.0.0.1";
    }
    return ip;
  }
}
