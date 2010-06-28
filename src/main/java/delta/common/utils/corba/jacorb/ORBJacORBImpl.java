package delta.common.utils.corba.jacorb;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.CosNaming.NamingContextExt;
import org.omg.PortableServer.IdAssignmentPolicy;
import org.omg.PortableServer.IdAssignmentPolicyValue;
import org.omg.PortableServer.LifespanPolicy;
import org.omg.PortableServer.LifespanPolicyValue;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.Servant;

import delta.common.utils.corba.CORBAServiceInfo;
import delta.common.utils.corba.CORBAServicesRegistry;
import delta.common.utils.corba.ORBImpl;
import delta.common.utils.corba.ServantInfo;
import delta.common.utils.environment.Host;
import delta.common.utils.environment.HostAndApplication;
import delta.common.utils.network.NetworkUtilities;
import delta.common.utils.traces.UtilsLoggers;

/**
 * Implementation of JacORB-specific ORB services.
 * @author DAM
 */
public class ORBJacORBImpl extends ORBImpl
{
  private static final Logger _logger=UtilsLoggers.getCorbaLogger();

  /**
   * The ORB class specific property name.
   */
  private static final String ORB_CLASS_PROP_NAME="org.omg.CORBA.ORBClass";

  /**
   * The ORB singleton class specific property name.
   */
  private static final String ORB_SINGLETON_CLASS_PROP_NAME="org.omg.CORBA.ORBSingletonClass";

  /**
   * POA name.
   */
  private static final String POA_NAME="SERVER-POA";

  private POA _rootPOA;

  /**
   * Build the alias properties (used to bind each servant corbaloc alias to its POA identifier).
   * @param infos All the server hosted servant infos.
   * @return properties Computed properties.
   */
  private Properties buildAliasProperties(List<ServantInfo> infos)
  {
    /*
     * Add the JACORB implname property value.
     */
    Properties properties=new Properties();
    String orbName=getORBName();
    String implKey="jacorb.implname";
    properties.setProperty(implKey,orbName);
    if (_logger.isDebugEnabled())
    {
      _logger.debug("Added property ["+implKey+"]=["+orbName+"].");
    }

    /*
     * Add servant aliases into the ObjectKeyMap.
     */
    if ((infos!=null) && (infos.size()>0))
    {
      String keyStart="jacorb.orb.objectKeyMap.";

      ServantInfo info;
      String valueStart=orbName+"/"+POA_NAME+"/";
      String key;
      String value;
      String id;
      java.lang.Object oldValue;
      for(Iterator<ServantInfo> it=infos.iterator();it.hasNext();)
      {
        info=it.next();
        //if (info.getMode()==CorbaPublicationMode.CORBA_LOC)
        {
          id=info.getServerName();
          key=keyStart+id;
          oldValue=properties.getProperty(key);
          if (oldValue==null)
          {
            value=valueStart+id;
            properties.setProperty(key,value);
            if (_logger.isDebugEnabled())
            {
              _logger.debug("Added property ["+key+"]=["+value+"].");
            }
          }
          else
          {
            /*
             * The property has already be inserted
             * => more than one servants have the same identifier !!!
             */
            _logger.warn("Duplicate servant identifier : "+id);
          }
        }
      }
    }
    return properties;
  }

  /**
   * Get the IP to use with for this server ORB.
   * @return the IP to use with for this server ORB.
   */
  private String getIP()
  {
    String hostName=Host.getLocalHost().getName();
    String ip=NetworkUtilities.getIPForName(hostName);
    return ip;
  }

  /**
   * Get the port to use with for this server ORB.
   * @return the port to use with for this server ORB.
   */
  private int getPort()
  {
    CORBAServicesRegistry registry=CORBAServicesRegistry.getInstance();
    String orbName=getORBName();
    int port=registry.getPortForServer(orbName);
    return port;
  }

  /**
   * Activate a servant.
   * @param info Servant info.
   */
  @Override
  public void activateServant(ServantInfo info)
  {
    String identifier=info.getServerName();
    try
    {
      POA poa=getPOA();
      String poaName=POA_NAME;
      byte[] id=identifier.getBytes();
      Servant servant=info.getServant();
      poa.activate_object_with_id(id, servant);
      if (_logger.isDebugEnabled())
      {
        Object ior=poa.servant_to_reference(servant);
        String msg="Servant for ["+identifier+"] activated in the POA ["+poaName+"]. IOR is \""+ior+"\".";
        _logger.debug(msg);
      }
    }
    catch(Throwable t)
    {
      _logger.error("Cannot activate servant ["+identifier+"]",t);
    }
  }

  /**
   * Activate the POA that manages registered servants.
   */
  @Override
  public void activatePOA()
  {
    if (_rootPOA!=null)
    {
      POAManager poaManager=_rootPOA.the_POAManager();
      if (poaManager!=null)
      {
        try
        {
          poaManager.activate();
        }
        catch(Exception e)
        {
          _logger.error("Error while activating POA",e);
        }
      }
    }
  }

  /**
   * Create the server ORB from the ORB specific singleton and singleton class
   * values.
   * @return The server ORB.
   */
  @Override
  protected ORB createORB()
  {
    // ORB class and ORB singleton class shall be add to system properties.
    Properties systemProperties = System.getProperties();
    systemProperties.setProperty(ORB_CLASS_PROP_NAME, "org.jacorb.orb.ORB");
    systemProperties.setProperty(ORB_SINGLETON_CLASS_PROP_NAME, "org.jacorb.orb.ORBSingleton");

    // Read the CORBA server TCP/IP address. Add the IP address to the system
    // properties for JacORB initialization.
    String ip=getIP();
    final String OAIAddr="OAIAddr";
    systemProperties.setProperty(OAIAddr,ip);
    _logger.debug("Added system property ["+OAIAddr+"]=["+ip+"].");
    // Read the CORBA server TCP/IP port. Add the TCP/IP port to the system
    // properties for JacORB initialization.
    int port=getPort();
    final String OAPort="OAPort";
    systemProperties.setProperty("OAPort", String.valueOf(port));
    _logger.debug("Added system property ["+OAPort+"]=["+port+"].");

    Properties props=buildAliasProperties(_servantInfos);

    // Create, initialize and return the ORB.
    ORB orb=null;
    try
    {
      orb=ORB.init(new String[]{},props);
    }
    catch (Exception e)
    {
      _logger.error("ORB initialization failed !",e);
    }
    return orb;
  }

  /**
   * Create the POA.
   * @return The POA.
   */
  @Override
  protected POA createPOA()
  {
    POA poa=null;
    try
    {
      ORB orb=getORB();
      POA root=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
      IdAssignmentPolicy assPolicy=root.create_id_assignment_policy(IdAssignmentPolicyValue.USER_ID);
      LifespanPolicy lsPolicy=root.create_lifespan_policy(LifespanPolicyValue.PERSISTENT);
      Policy[] policies = new Policy[] { assPolicy, lsPolicy };
      POAManager poaManager=root.the_POAManager();
      poa=root.create_POA(POA_NAME,poaManager,policies);
      _rootPOA=root;
    }
    catch(Throwable t)
    {
      _logger.error("Cannot create POA !",t);
    }
    return poa;
  }

  /**
   * Create the naming service.
   * @return the naming service.
   */
  @Override
  protected NamingContextExt createNamingService()
  {
    NamingContextExt namingService=null;
    return namingService;
  }

  /**
   * Find a reference to a CORBA object using the naming service.
   * @param serviceInfo Service info.
   * @param where Service location (or <code>null</code>).
   * @return A reference to a CORBA object or <code>null</code> if not found.
   */
  @Override
  protected Object findInNamingService(CORBAServiceInfo serviceInfo, HostAndApplication where)
  {
    return null;
  }

  /**
   * Publish a CORBA servant in the naming service.
   * @param servantInfo Infos on servant to publish.
   * @return <code>true</code> if successfull, <code>false</code> otherwise.
   */
  @Override
  protected boolean publishInNamingService(ServantInfo servantInfo)
  {
    return false;
  }

  /**
   * Publish a CORBA servant with a CORBA-loc.
   * @param servantInfo Infos on servant to publish.
   * @return <code>true</code> if successfull, <code>false</code> otherwise.
   */
  @Override
  protected boolean publishWithCorbaLoc(ServantInfo servantInfo)
  {
    return false;
  }
}
