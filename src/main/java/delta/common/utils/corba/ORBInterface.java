package delta.common.utils.corba;

import java.io.File;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;

import delta.common.utils.environment.HostAndApplication;

/**
 * Simple ORB interface.
 * @author DAM
 */
public class ORBInterface
{
  /**
   * Reference to the sole instance of this class
   */
  private static ORBInterface _instance;

  private ORBImpl _orbClient;

  /**
   * Get the sole instance of this class.
   * @return The sole instance of this class.
   */
  public static ORBInterface getInstance()
  {
    if (_instance==null)
    {
      _instance=new ORBInterface();
    }
    return _instance;
  }

  /**
   * Private constructor.
   */
  private ORBInterface()
  {
    // Nothing to do !
  }

  private ORBImpl getORBClient()
  {
    if (_orbClient==null)
    {
      String client="client";
      String orbImplName=CORBAConstants.getDefaultORBImpl();
      _orbClient=ORBImpl.buildNewInstance(client,orbImplName);
    }
    return _orbClient;
  }

  /**
   * Get the ORB.
   * @return the ORB.
   */
  public ORB getORB()
  {
    ORBImpl impl=getORBClient();
    ORB orb=impl.getORB();
    return orb;
  }

  /**
   * Get the naming service.
   * @return the naming service.
   */
  public NamingContextExt getNamingService()
  {
    ORBImpl impl=getORBClient();
    NamingContextExt namingService=impl.getNamingService();
    return namingService;
  }

  /**
   * Load a CORBA object from its reference.
   * @param  ior An object IOR.
   * @return The corba object corresponding to the given IOR.
   */
  public Object loadFromIOR(String ior)
  {
    return getORB().string_to_object(ior);
  }

  /**
   * Find a reference to a named CORBA object.
   * @param serviceName name of object to find.
   * @return a CORBA object reference or <code>null</code> if not found.
   */
  public Object findObject(String serviceName)
  {
    Object ret=findObject(serviceName,null);
    return ret;
  }

  /**
   * Find a reference to a named CORBA object.
   * @param serviceName name of object to find.
   * @param where Service location (or <code>null</code>).
   * @return a CORBA object reference or <code>null</code> if not found.
   */
  public Object findObject(String serviceName, HostAndApplication where)
  {
    ORBImpl impl=getORBClient();
    Object ret=impl.findObject(serviceName,where);
    return ret;
  }

  /**
   * Find a reference to a named CORBA object.
   * @param iorFile Path of IOR file.
   * @return a CORBA object reference or <code>null</code> if not found.
   */
  public Object findObjectWithIOR(File iorFile)
  {
    ORBImpl impl=getORBClient();
    Object ret=impl.findWithIOR(iorFile);
    return ret;
  }
}
