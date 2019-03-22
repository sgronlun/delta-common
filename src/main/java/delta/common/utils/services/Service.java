package delta.common.utils.services;

import java.io.PrintStream;

import org.apache.log4j.Logger;

/**
 * Base class for services.
 * @author DAM
 */
public class Service
{
  private static final Logger LOGGER=Logger.getLogger(Service.class);

  private String _name;
  private ServiceState _state;

  /**
   * Constructor.
   * @param name Service name.
   */
  public Service(String name)
  {
    _name=name;
    _state=ServiceState.NOT_RUNNING;
  }

  /**
   * Get the name of this service.
   * @return the name of this service.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the state of this service.
   * @return the state of this service.
   */
  public ServiceState getState()
  {
    return _state;
  }

  protected void setState(ServiceState state)
  {
    _state=state;
  }

  /**
   * Start service.
   */
  public void start()
  {
    LOGGER.error("This method is not implemented for service ["+_name+"]");
  }

  /**
   * Start this service if it is not yes started.
   */
  public void startIfNecessary()
  {
    if (_state==ServiceState.NOT_RUNNING)
    {
      start();
    }
    else if (_state==ServiceState.OUT_OF_ORDER)
    {
      LOGGER.warn("Service ["+_name+"] is out of order. Cannot start it.");
    }
  }

  /**
   * Stop service.
   *
   */
  public void stop()
  {
    LOGGER.error("This method is not implemented for service ["+_name+"]");
  }

  /**
   * Dump infos about services.
   * @param out Output stream.
   */
  public void dump(PrintStream out)
  {
    // Nothing to do here !!
  }
}
