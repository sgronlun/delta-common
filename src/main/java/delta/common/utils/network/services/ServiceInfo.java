package delta.common.utils.network.services;

import delta.common.utils.environment.Host;

/**
 * Provides information about a single service.
 * @author DAM
 */
public class ServiceInfo
{
  /**
   * Default hostname for a service.
   */
  private static final String DEFAULT_HOST_NAME="localhost";
  /**
   * Name of the described service.
   */
  private String _serviceName;
  /**
   * Name of the host that runs the service.
   */
  private String _hostName;
  /**
   * Port of the service.
   */
  private short _port;

  /**
   * Basic constructor (localhost is assumed).
   * @param serviceName Name of the service.
   * @param port Port of the service.
   */
  public ServiceInfo(String serviceName, short port)
  {
    _serviceName=serviceName;
    _hostName=DEFAULT_HOST_NAME;
    _port=port;
  }

  /**
   * Constrcuctor.
   * @param serviceName Name of the service.
   * @param hostName Name of the host that runs the service.
   * @param port Port of the service.
   */
  public ServiceInfo(String serviceName, String hostName, short port)
  {
    _serviceName=serviceName;
    if ((hostName==null) || (hostName.equalsIgnoreCase(ServiceInfo.DEFAULT_HOST_NAME)))
    {
      Host localhost=Host.getLocalHost();
      hostName=localhost.getName();
    }
    _hostName=hostName;
    _port=port;
  }

  /**
   * Get the name of the service.
   * @return the name of the service.
   */
  public String getServiceName()
  {
    return _serviceName;
  }

  /**
   * Get the name of the host that runs the service.
   * @return the name of the host that runs the service.
   */
  public String getHostName()
  {
    return _hostName;
  }

  /**
   * Get the port of the service.
   * @return the port of the service.
   */
  public short getPort()
  {
    return _port;
  }

  /**
   * Returns a string of information on the service.
   * @return the string of information on the service.
   */
  @Override
  public String toString()
  {
    return "Service ["+getServiceName()+"] Host [" + getHostName() + "] Port " + getPort();
  }
}
