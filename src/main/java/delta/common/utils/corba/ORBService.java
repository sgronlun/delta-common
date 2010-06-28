package delta.common.utils.corba;

import java.io.File;

import org.apache.log4j.Logger;
import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

import delta.common.utils.environment.HostAndApplication;
import delta.common.utils.services.Service;
import delta.common.utils.services.ServiceState;
import delta.common.utils.services.ServicesManager;
import delta.common.utils.traces.UtilsLoggers;

/**
 * ORB service.
 * The ORB service provides :
 * <ul>
 * <li>a thread that runs the ORB main loop,
 * <li>methods to register CORBA objects.
 * </ul>
 * @author DAM
 */
public class ORBService extends Service
{
  private static final Logger _logger=UtilsLoggers.getCorbaLogger();

  private static final long JOIN_TIMEOUT=5000;

  /**
   * Server name.
   */
  private String _serverName;
  /**
   * ORB implementation name.
   */
  private String _orbImplName;
  /**
   * ORB implementation.
   */
  private ORBImpl _orbServer;
  /**
   * The thread that runs the ORB (server side).
   */
  private Thread _orbThread;
  /**
   * Indicates if the current ORB service is run as a daemon thread or not.
   */
  private boolean _runAsDaemon;

  /**
   * Public constructor.
   * @param name Name.
   * @param runAsDaemon Indicates if this service used a daemon thread.
   */
  public ORBService(String name, boolean runAsDaemon)
  {
    this(name,runAsDaemon,CORBAConstants.getDefaultORBImpl());
  }

  /**
   * Public constructor.
   * @param name Name.
   * @param runAsDaemon Indicates if this service used a daemon thread.
   * @param orbImplName Name of ORB implementation to use.
   */
  public ORBService(String name, boolean runAsDaemon, String orbImplName)
  {
    super("ORB("+name+")");
    _serverName=name;
    _runAsDaemon=runAsDaemon;
    _orbImplName=orbImplName;
    ServicesManager.getInstance().registerService(this);
  }

  /**
   * Get the underlying CORBA server implementation.
   * @return the underlying CORBA server implementation.
   */
  public ORBImpl getORBServer()
  {
    if (_orbServer==null)
    {
      _orbServer=ORBImpl.buildNewInstance(_serverName,_orbImplName);
    }
    return _orbServer;
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
   * Start service.
   */
  @Override
  public synchronized void start()
  {
    // Already started !
    if (_orbThread!=null) return;

    ORBImpl orbServer=getORBServer();
    final ORB orb=orbServer.getORB();
    if (orb!=null)
    {
      orbServer.activateServants();
      orbServer.publishObjects();
      orbServer.activatePOA();
      _orbThread=new Thread("ORB thread : "+getName())
      {
        @Override
        public void run()
        {
          _logger.info("ORB start !");
          orb.run();
          _logger.info("ORB end !");
        }
      };
      _orbThread.setDaemon(_runAsDaemon);
      _orbThread.start();
      setState(ServiceState.RUNNING);
    }
  }

  /**
   * Stop the ORB
   */
  @Override
  public synchronized void stop()
  {
    // Not started !
    if (_orbThread==null) return;
    ORBImpl orbServer=getORBServer();

    ORB orb=orbServer.getORB();
    if (orb!=null)
    {
      orb.shutdown(true);
    }
    try
    {
      _orbThread.join(JOIN_TIMEOUT);
    }
    catch(InterruptedException ie)
    {
      _logger.error("",ie);
    }
    _orbThread=null;
    setState(ServiceState.NOT_RUNNING);
  }

  /**
   * Register a CORBA object with a global scope.
   * @param servant object implementation.
   * @param where Service location (or <code>null</code>).
   * @param name name to use to identify the object.
   */
  public void registerObject(Servant servant, String name, HostAndApplication where)
  {
    ORBImpl orbServer=getORBServer();
    orbServer.registerServant(servant,name,where);
  }

  /**
   * Register a CORBA object through an IOR file.
   * @param servant object implementation.
   * @param ior path of IOR file.
   */
  public void registerObjectWithIOR(Servant servant, File ior)
  {
    ORBImpl orbServer=getORBServer();
    orbServer.registerServant(servant,ior,null);
  }

  /**
   * Unregister a local CORBA object.
   * @param name Object name.
   * @return <code>true</code> if it was successfully done, <code>false</code> otherwise.
   */
  public boolean unregisterLocalObject(String name)
  {
    // todo
    return true;
  }

  /**
   * Get the POA (build it if necessary).
   * @return the POA.
   */
  public POA getPOA()
  {
    ORBImpl orbServer=getORBServer();
    POA poa=orbServer.getPOA();
    return poa;
  }
}
