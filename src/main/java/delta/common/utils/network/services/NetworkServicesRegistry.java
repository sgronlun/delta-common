package delta.common.utils.network.services;

import java.util.HashMap;

/**
 * Registry of service informations.
 * @author DAM
 */
public class NetworkServicesRegistry
{
  private HashMap<String, ServiceInfo> _serviceLocations;

  /**
   * Constructor.
   */
  public NetworkServicesRegistry()
  {
    _serviceLocations=new HashMap<String, ServiceInfo>();
  }

  /**
   * Get a service information by name.
   * @param name Name of the targeted service.
   * @return A service information or <code>null</code> if not found.
   */
  public ServiceInfo getService(String name)
  {
    return _serviceLocations.get(name);
  }

  /**
   * Register a new service information.
   * @param location Service information.
   */
  public void addServiceInfo(ServiceInfo location)
  {
    _serviceLocations.put(location.getServiceName(), location);
  }
}
