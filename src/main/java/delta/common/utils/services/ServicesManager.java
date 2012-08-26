package delta.common.utils.services;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * Manager for all services.
 * @author DAM
 */
public class ServicesManager
{
  private static final Logger _logger=UtilsLoggers.getServicesLogger();

  /**
   * Reference to the sole instance of this class
   */
  private static ServicesManager _instance;

  private List<Service> _knownServices;

  /**
   * Get the sole instance of this class.
   * @return The sole instance of this class.
   */
  public static ServicesManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new ServicesManager();
    }
    return _instance;
  }

  /**
   * Private constructor.
   */
  private ServicesManager()
  {
    _knownServices=new ArrayList<Service>();
  }

  /**
   * Register a service.
   * @param service Service to register.
   */
  public void registerService(Service service)
  {
    _knownServices.add(service);
  }

  /**
   * Unregister a service.
   * @param service Service to unregister.
   */
  public void unregisterService(Service service)
  {
    if (service!=null)
    {
      if (service.getState()==ServiceState.RUNNING)
      {
        service.stop();
      }
      _knownServices.remove(service);
    }
  }

  /**
   * Stop all started services.
   */
  public void stopServices()
  {
    // Services are stopped in the opposite order of their registration
    ArrayList<Service> services=new ArrayList<Service>(_knownServices);
    Service service;
    int nbServices=services.size();
    for(int i=nbServices-1;i>=0;i--)
    {
      service=services.get(i);
      _logger.info("Stopping service ["+service.getName()+"]");
      stopService(service);
    }
  }

  /**
   * Stop a service.
   * @param service Service to stop.
   */
  public void stopService(Service service)
  {
    try
    {
      if (service!=null)
      {
        ServiceState state=service.getState();
        if (state==ServiceState.RUNNING)
        {
          service.stop();
        }
      }
    }
    catch(Throwable t)
    {
      _logger.error("",t);
    }
  }

  /**
   * Dump infos about services.
   * @param out Output stream.
   */
  public void dump(PrintStream out)
  {
    Service service;
    ServiceState state;
    for(Iterator<Service> it=_knownServices.iterator();it.hasNext();)
    {
      service=it.next();
      state=service.getState();
      out.println("["+service.getName()+"] : "+state.getState());
      service.dump(out);
    }
  }
}
