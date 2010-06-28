package delta.common.utils.corba;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * CORBA service information.
 * @author DAM
 */
public class CORBAServiceInfo
{
  private static final Logger _logger=UtilsLoggers.getCorbaLogger();

  private String _serviceName;
  private String _orbImplName;
  private CorbaPublicationMode _publicationMode;
  // For the corbaloc publication mode
  private String _serverName;
  // For the ior publication mode
  private String _scope;

  /**
   * Constructor.
   * @param serviceName Service name.
   * @param orbImpl ORB implementation.
   * @param mode CORBA publication mode.
   */
  public CORBAServiceInfo(String serviceName, String orbImpl, CorbaPublicationMode mode)
  {
    _serviceName=serviceName;
    _orbImplName=orbImpl;
    _publicationMode=mode;
    _scope="local";
  }

  /**
   * Get the name for this service.
   * @return the name.
   */
  public String getServiceName()
  {
    return _serviceName;
  }

  /**
   * Get the ORB implementation name for this service.
   * @return the ORB implementation name for this service.
   */
  public String getORBImplName()
  {
    return _orbImplName;
  }

  /**
   * Get the CORBA publication mode for this service.
   * @return the CORBA publication mode for this service.
   */
  public CorbaPublicationMode getPublicationMode()
  {
    return _publicationMode;
  }

  /**
   * Get the server name for this service.
   * @return the server name.
   */
  public String getServerName()
  {
    return _serverName;
  }

  /**
   * Set the server name for this service.
   * (only in CORBA_LOC mode)
   * @param serverName Name of the server.
   */
  public void setServerName(String serverName)
  {
    if (_publicationMode==CorbaPublicationMode.CORBA_LOC)
    {
      _serverName=serverName;
    }
    else
    {
      _logger.error("Server name is ignored when not in CORBA_LOC mode for service ("+_serviceName+").");
    }
  }

  /**
   * Get the scope for this service.
   * @return the scope for this service.
   */
  public String getScope()
  {
    return _scope;
  }

  /**
   * Indicates if the service is exported locally or not.
   * @return <code>true</code> if it is local, <code>false</code> otherwise.
   */
  public boolean isLocal()
  {
    boolean local=false;
    if (CORBAConstants.LOCAL.equals(_scope))
    {
      local=true;
    }
    return local;
  }

  /**
   * Set the scope for this service.
   * (only in IOR_FILE mode)
   * @param scope Scope.
   */
  public void setScope(String scope)
  {
    if (_publicationMode==CorbaPublicationMode.IOR_FILE)
    {
      _scope=scope;
    }
    else
    {
      _logger.error("Scope is ignored when not in IOR_FILE mode for service ("+_serviceName+").");
    }
  }
}
