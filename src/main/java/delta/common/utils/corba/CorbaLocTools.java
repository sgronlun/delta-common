package delta.common.utils.corba;

import org.apache.log4j.Logger;

import delta.common.utils.environment.HostAndApplication;
import delta.common.utils.network.NetworkUtilities;
import delta.common.utils.traces.UtilsLoggers;

/**
 * Tools related to CORBA locations.
 * @author DAM
 */
public class CorbaLocTools
{
  private static final Logger _logger=UtilsLoggers.getCorbaLogger();

  /**
   * Computes the corba location of specified service
   * @param serviceName Service name.
   * @param where Service location (or <code>null</code>).
   * @return A CORBA location.
   */
  public static String computeCorbaloc(String serviceName, HostAndApplication where)
  {
    CORBAServicesRegistry registry=CORBAServicesRegistry.getInstance();
    CORBAServiceInfo info=registry.getInfoForService(serviceName);
    String corbaLocation="";
    if (info!=null)
    {
      CorbaPublicationMode mode=info.getPublicationMode();
      if (mode!=CorbaPublicationMode.CORBA_LOC)
      {
        _logger.warn("Computing a corbaloc for service ["+serviceName+"] whose mode is ["+mode+"]");
      }
      String serverName=info.getServerName();
      CORBAServerInfo serverInfo=registry.getInfoForServer(serverName);
      String hostname="localhost";
      int port=0;
      if (serverInfo!=null)
      {
        hostname=serverInfo.getHostName();
        port=serverInfo.getPort();

        if (where!=null)
        {
          hostname=where.getHostName();
        }
      }
      else
      {
        _logger.error("Cannot find server info for server ["+serverName+"]. Using localhost.");
      }
      String ip=NetworkUtilities.getIPForName(hostname);
      StringBuilder sb=new StringBuilder();
      sb.append("corbaloc:iiop:");
      sb.append(ip);
      sb.append(':');
      sb.append(port);
      sb.append('/');
      sb.append(serviceName);

      corbaLocation=sb.toString();
    }
    else
    {
      _logger.error("Unknown service ["+serviceName+"]");
    }
    return corbaLocation;
  }
}
