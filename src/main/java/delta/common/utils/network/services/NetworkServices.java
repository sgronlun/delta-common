package delta.common.utils.network.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.configuration.Configuration;
import delta.common.utils.configuration.Configurations;
import delta.common.utils.traces.UtilsLoggers;

public final class NetworkServices
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  private static NetworkServices _instance;

  private List<NetworkServicesRegistry> _serviceRegistries;

  public static NetworkServices getInstance()
  {
    synchronized (NetworkServices.class)
    {
      if (_instance==null)
      {
        _instance=new NetworkServices();
      }
      return _instance;
    }
  }

  private NetworkServices()
  {
    _serviceRegistries=new ArrayList<NetworkServicesRegistry>();
    parserServiceFiles();
  }

  public ServiceInfo getServiceByName(String serviceName)
  {
    ServiceInfo service_l=null;
    int nb=_serviceRegistries.size();
    NetworkServicesRegistry register;
    for(int i=0;i<nb;i++)
    {
      register=_serviceRegistries.get(i);
      service_l=register.getService(serviceName);
      if (service_l!=null)
      {
        break;
      }
    }
    return service_l;
  }

  private void parserServiceFiles()
  {
    ServicesFileReader reader_l=new ServicesFileReader();
    NetworkServicesRegistry register_l=null;
    Configuration cfg=Configurations.getConfiguration();
    File path=cfg.getFileValue("NETWORK","SERVICES",null);
    if (path!=null)
    {
      register_l=reader_l.parseServices(path);
    }
    if (register_l!=null)
    {
      _serviceRegistries.add(register_l);
    }
    else
    {
      _logger.error("Could not parse service file !");
    }
  }
}
