package delta.common.utils.corba;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NamingContextExt;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

import delta.common.utils.corba.jacorb.ORBJacORBImpl;
import delta.common.utils.corba.jdk.ORBJDKImpl;
import delta.common.utils.environment.HostAndApplication;
import delta.common.utils.files.TextFileReader;
import delta.common.utils.files.TextFileWriter;
import delta.common.utils.traces.UtilsLoggers;

/**
 * Base class for ORB-specific implementations.
 * @author DAM
 */
public abstract class ORBImpl
{
  private static final Logger _logger=UtilsLoggers.getCorbaLogger();

  protected List<ServantInfo> _servantInfos;

  private boolean _objectsPublished;

  /**
   * Server name.
   */
  private String _orbName;
  /**
   * The managed ORB.
   */
  private ORB _orb;
  /**
   * The managed POA.
   */
  private POA _poa;
  /**
   * The managed naming service.
   */
  private NamingContextExt _namingService;

  /**
   * Build a new ORB implementation.
   * @param name ORB name.
   * @param orbImplName Name of ORB implementation to use.
   * @return The newly built ORB implementation.
   */
  public static ORBImpl buildNewInstance(String name, String orbImplName)
  {
    ORBImpl ret=null;
    if (CORBAConstants.JACORB_IMPL.equals(orbImplName))
    {
      ret=new ORBJacORBImpl();
    }
    else if (CORBAConstants.JDK_IMPL.equals(orbImplName))
    {
      ret=new ORBJDKImpl();
    }
    else
    {
      _logger.warn("Unknown implementation ["+orbImplName+"]. Using default JDK impl.");
      ret=new ORBJDKImpl();
    }
    ret._orbName=name;
    return ret;
  }

  protected ORBImpl()
  {
    _servantInfos=new ArrayList<ServantInfo>();
    _objectsPublished=false;
  }

  /**
   * Get the ORB name.
   * @return the ORB name.
   */
  public String getORBName()
  {
    return _orbName;
  }

  /**
   * Get the ORB (build it if necessary).
   * @return the ORB.
   */
  public ORB getORB()
  {
    if (_orb==null)
    {
      _orb=createORB();
    }
    return _orb;
  }

  /**
   * Get the POA (build it if necessary).
   * @return the POA.
   */
  public POA getPOA()
  {
    if (_poa==null)
    {
      _poa=createPOA();
    }
    return _poa;
  }

  /**
   * Get the naming service (build it if necessary).
   * @return the naming service.
   */
  protected NamingContextExt getNamingService()
  {
    if (_namingService==null)
    {
      _namingService=createNamingService();
    }
    return _namingService;
  }

  /**
   * Register a servant and its publication method (identifier/mode), so
   * that it will be automatically published when the ORB starts.
   * @param servant Servant.
   * @param serviceName Service name.
   * @param where Service location (or <code>null</code>).
   * while <code>false</code> indicates a shared publication (naming service or CORBA-loc).
   */
  void registerServant(Servant servant, String serviceName, HostAndApplication where)
  {
    CORBAServicesRegistry registry=CORBAServicesRegistry.getInstance();
    CORBAServiceInfo serviceInfo=registry.getInfoForService(serviceName);
    if (serviceInfo!=null)
    {
      ServantInfo info=new ServantInfo(servant,serviceInfo,where);
      registerServant(info);
    }
    else
    {
      _logger.error("Cannot find service info for service : "+serviceName);
    }
  }

  /**
   * Register a servant through an IOR file.
   * @param servant Servant.
   * @param iorFile IOR file path.
   * @param name Name of object.
   */
  void registerServant(Servant servant, File iorFile, String name)
  {
    ServantInfo info=new ServantInfo(servant,iorFile,name);
    registerServant(info);
  }

  /**
   * Register a servant.
   * @param info Servant info.
   */
  private void registerServant(ServantInfo info)
  {
    _servantInfos.add(info);
    if (_objectsPublished)
    {
      _logger.warn("Registering object ["+info+"] after objects publication !");
      activateServant(info);
      publishObject(info);
    }
  }

  /**
   * ORB creation method.
   * The ORB creation is delegated to sub-classes.
   * @return An ORB.
   */
  protected abstract ORB createORB();

  /**
   * POA creation method.
   * The POA creation is delegated to sub-classes.
   * @return A POA.
   */
  protected abstract POA createPOA();

  /**
   * Create a reference to the naming service.
   * The implementation of this method is delegated to sub-classes.
   * @return A namin service reference.
   */
  protected abstract NamingContextExt createNamingService();

  /**
   * Activate all registered servants.
   */
  public void activateServants()
  {
    List<ServantInfo> infos=_servantInfos;
    if ((infos!=null) && (infos.size()>0))
    {
      ServantInfo info;
      for(Iterator<ServantInfo> it=infos.iterator();it.hasNext();)
      {
        info=it.next();
        activateServant(info);
      }
    }
  }

  /**
   * Simple servant activation.
   * @param servant Servant.
   */
  public void activateServant(Servant servant)
  {
    ServantInfo info=new ServantInfo(servant);
    activateServant(info);
  }

  /**
   * Activate a servant.
   * @param info Servant info.
   */
  public abstract void activateServant(ServantInfo info);

  /**
   * Activate the POA that manages registered servants.
   */
  public abstract void activatePOA();

  /**
   * Find a reference to a named CORBA object.
   * @param serviceName name of object to find.
   * @param where Service location (or <code>null</code>).
   * @return a CORBA object reference or <code>null</code> if not found.
   */
  public Object findObject(String serviceName, HostAndApplication where)
  {
    Object ret=null;
    CORBAServicesRegistry registry=CORBAServicesRegistry.getInstance();
    CORBAServiceInfo info=registry.getInfoForService(serviceName);
    CorbaPublicationMode mode=info.getPublicationMode();
    if (mode==CorbaPublicationMode.NAMING_SERVICE)
    {
      ret=findInNamingService(info,where);
    }
    else if (mode==CorbaPublicationMode.CORBA_LOC)
    {
      ret=findWithCorbaLoc(info,where);
    }
    else if (mode==CorbaPublicationMode.IOR_FILE)
    {
      ret=findWithIOR(info,where);
    }
    else
    {
      _logger.error("Unmanaged publication mode : "+mode);
    }
    return ret;
  }

  /**
   * Find a reference to a CORBA object using the naming service.
   * @param serviceInfo Service info.
   * @param where Service location (or <code>null</code>).
   * @return A reference to a CORBA object or <code>null</code> if not found.
   */
  protected abstract Object findInNamingService(CORBAServiceInfo serviceInfo, HostAndApplication where);

  /**
   * Find a reference to a CORBA object using a CORBA-loc.
   * @param serviceInfo Service info.
   * @param where Service location (or <code>null</code>).
   * @return A reference to a CORBA object or <code>null</code> if not found.
   */
  protected Object findWithCorbaLoc(CORBAServiceInfo serviceInfo, HostAndApplication where)
  {
    String serviceName=serviceInfo.getServiceName();
    String corbaLocation=CorbaLocTools.computeCorbaloc(serviceName,where);
    ORB orb=getORB();
    Object obj=orb.string_to_object(corbaLocation);
    return obj;
  }

  /**
   * Find a reference to a CORBA object using an IOR file.
   * @param serviceInfo Service info.
   * @param where Service location (or <code>null</code>).
   * @return A reference to a CORBA object or <code>null</code> if not found.
   */
  public Object findWithIOR(CORBAServiceInfo serviceInfo, HostAndApplication where)
  {
    String serviceName=serviceInfo.getServiceName();
    boolean local=serviceInfo.isLocal();
    File iorFile=CORBAConstants.getIORFile(serviceName,where,local);
    Object corbaObject=findWithIOR(iorFile);
    return corbaObject;
  }

  /**
   * Find a reference to a CORBA object using an IOR.
   * @param iorFile Path of IOR file.
   * @return A reference to a CORBA object or <code>null</code> if not found.
   */
  public Object findWithIOR(File iorFile)
  {
    Object corbaObject=null;
    String ior=null;
    TextFileReader fr=new TextFileReader(iorFile);
    if (fr.start())
    {
      ior=fr.getNextLine();
      fr.terminate();
    }
    if (ior!=null)
    {
      ORB orb=getORB();
      corbaObject=orb.string_to_object(ior);
    }
    return corbaObject;
  }

  /**
   * Publish all servant objects.
   */
  public void publishObjects()
  {
    List<ServantInfo> infos=_servantInfos;
    if ((infos!=null) && (infos.size()>0))
    {
      ServantInfo info;
      for(Iterator<ServantInfo> it=infos.iterator();it.hasNext();)
      {
        info=it.next();
        publishObject(info);
      }
    }
    _objectsPublished=true;
  }

  private void publishObject(ServantInfo info)
  {
    CorbaPublicationMode mode=info.getMode();
    if (mode==CorbaPublicationMode.IOR_FILE)
    {
      publishWithIOR(info);
    }
    else if (mode==CorbaPublicationMode.NAMING_SERVICE)
    {
      publishInNamingService(info);
    }
    else if (mode==CorbaPublicationMode.CORBA_LOC)
    {
      publishWithCorbaLoc(info);
    }
  }

  /**
   * Publish a CORBA servant in the naming service.
   * @param servantInfo Infos on servant to publish.
   * @return <code>true</code> if successfull, <code>false</code> otherwise.
   */
  protected abstract boolean publishInNamingService(ServantInfo servantInfo);

  /**
   * Publish a CORBA servant with a CORBA-loc.
   * @param servantInfo Infos on servant to publish.
   * @return <code>true</code> if successfull, <code>false</code> otherwise.
   */
  protected abstract boolean publishWithCorbaLoc(ServantInfo servantInfo);

  /**
   * Publish a CORBA servant using an IOR.
   * @param servantInfo Infos on servant to publish.
   * @return <code>true</code> if successfull, <code>false</code> otherwise.
   */
  protected boolean publishWithIOR(ServantInfo servantInfo)
  {
    boolean ret=false;
    try
    {
      ORB orb=getORB();
      POA poa=getPOA();
      Servant servant=servantInfo.getServant();
      Object objRef=poa.servant_to_reference(servant);
      String ior=orb.object_to_string(objRef);
      File iorFile=servantInfo.getIORFile();
      File parent=iorFile.getParentFile();
      parent.mkdirs();
      TextFileWriter fw=new TextFileWriter(iorFile);
      if (fw.start())
      {
        fw.writeNextLine(ior);
        fw.terminate();
        ret=true;
      }
      else
      {
        _logger.error("Cannot write to file : "+iorFile);
        // DAM - 20/06/2007 - Test parent existence
        boolean parentExists=parent.exists();
        if (!parentExists)
        {
          _logger.warn("Parent ["+parent+"] does not exist !");
        }
        else
        {
          boolean canWriteToParent=parent.canWrite();
          if (!canWriteToParent)
          {
            _logger.warn("Parent ["+parent+"] is not writeable !");
          }
        }
      }
    }
    catch(Exception e)
    {
      _logger.error("Cannot register object (IOR file) : "+servantInfo,e);
      ret=false;
    }
    return ret;
  }
}
