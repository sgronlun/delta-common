package delta.common.utils.corba.jdk;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.Servant;

import delta.common.utils.collections.CollectionTools;
import delta.common.utils.configuration.Configuration;
import delta.common.utils.configuration.Configurations;
import delta.common.utils.corba.CORBAConstants;
import delta.common.utils.corba.CORBAServiceInfo;
import delta.common.utils.corba.ORBImpl;
import delta.common.utils.corba.ServantInfo;
import delta.common.utils.environment.HostAndApplication;
import delta.common.utils.misc.SleepManager;
import delta.common.utils.text.StringTools;
import delta.common.utils.traces.UtilsLoggers;

/**
 * Implementation of JDK-specific ORB services.
 * @author DAM
 */
public class ORBJDKImpl extends ORBImpl
{
  private static final Logger _logger=UtilsLoggers.getCorbaLogger();

  private static final String CORBA_SECTION="CORBA";
  private static final String ORB_ARG_VARIABLE_SEED="ORB_ARG";
  private static final String ORB_PROP_VARIABLE_SEED="ORB_PROP_";
  private static final String SYSTEM_PROP_VARIABLE_SEED="SYSTEM_PROP_";
  private static final String KEY="KEY";
  private static final String VALUE="VALUE";
  private static final int NB_ENTRIES_MAX=10;

  /**
   * Number of "ping" tries to make sure a server is present or not during startup phase.
   */
  private static final int MAX_NAMING_SERVICE_REGISTRATION_TRIES=5;

  /**
   * Sleep time between two such tries (see above).
   */
  private static final int SLEEP_BETWEEN_TRIES=2000;

  /**
   * Create the ORB from the ORB specific singleton and singleton class values.
   * @return The ORB.
   */
  @Override
  protected ORB createORB()
  {
    Properties systemPropertiesToSet=getProperties(SYSTEM_PROP_VARIABLE_SEED);
    if (!systemPropertiesToSet.isEmpty())
    {
      Properties systemProperties=System.getProperties();
      _logger.info("Set system property : "+systemPropertiesToSet);
      Enumeration<?> e=systemPropertiesToSet.keys();
      String key;
      String value;
      while (e.hasMoreElements())
      {
        key=(String)e.nextElement();
        value=systemPropertiesToSet.getProperty(key);
        systemProperties.setProperty(key,value);
      }
    }
    Properties props=getProperties(ORB_PROP_VARIABLE_SEED);
    String[] args=getArgs();
    _logger.info("Set ORB property : "+props);
    _logger.info("Set ORB args : "+StringTools.getDisplayableArray(args));
    ORB orb=ORB.init(args,props);
    return orb;
  }

  private String[] getArgs()
  {
    Configuration cfg=Configurations.getConfiguration();
    String variableName;
    String value;
    List<String> values=new ArrayList<String>();
    for(int i=0;i<NB_ENTRIES_MAX;i++)
    {
      variableName=ORB_ARG_VARIABLE_SEED+i;
      value=cfg.getStringValue(CORBA_SECTION,variableName,null);
      if (value!=null)
      {
        values.add(value);
      }
    }
    return CollectionTools.stringArrayFromCollection(values);
  }

  private Properties getProperties(String seed)
  {
    Configuration cfg=Configurations.getConfiguration();
    String keyVariableName;
    String valueVariableName;
    String key;
    String value;
    Properties p=new Properties();
    for(int i=0;i<NB_ENTRIES_MAX;i++)
    {
      keyVariableName=seed+KEY+i;
      key=cfg.getStringValue(CORBA_SECTION,keyVariableName,null);
      valueVariableName=seed+VALUE+i;
      value=cfg.getStringValue(CORBA_SECTION,valueVariableName,null);
      if ((key!=null) && (value!=null))
      {
        p.setProperty(key,value);
      }
    }
    return p;
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
      if (orb!=null)
      {
        POA rootPoa=POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        rootPoa.the_POAManager().activate();
        poa=rootPoa;
      }
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
    try
    {
      namingService=NamingContextExtHelper.narrow(getORB().resolve_initial_references("NameService"));
    }
    catch(Exception e)
    {
      _logger.error("Cannot get the naming service !",e);
    }
    return namingService;
  }

  /**
   * Activate a servant.
   * @param info Servant info.
   */
  @Override
  public void activateServant(ServantInfo info)
  {
    // Nothing to do ?
  }

  /**
   * Activate the POA that manages registered servants.
   */
  @Override
  public void activatePOA()
  {
    // Nothing to do !!
  }

  /**
   * Find a reference to a named CORBA object.
   * @param serviceInfo Service info.
   * @param where Service location (or <code>null</code>).
   * @return a CORBA object reference or <code>null</code> if not found.
   */
  @Override
  protected Object findInNamingService(CORBAServiceInfo serviceInfo, HostAndApplication where)
  {
    Object ret=null;
    String serviceName=serviceInfo.getServiceName();
    String serviceId=CORBAConstants.getCORBAObjectName(serviceName,where);
    NamingContextExt namingService=getNamingService();
    if (namingService!=null)
    {
      try
      {
        NameComponent[] path=namingService.to_name(serviceId);
        ret=namingService.resolve(path);
      }
      catch(Exception e)
      {
        _logger.error("Cannot find object : "+serviceId,e);
        ret=null;
      }
    }
    return ret;
  }

  /**
   * Publish a CORBA servant in the naming service.
   * @param name Object name.
   * @param servant Servant to publish.
   * @return <code>true</code> if successfull, <code>false</code> otherwise.
   */
  protected boolean unsafePublishInNamingService(String name, Servant servant)
  {
    boolean ret=false;
    try
    {
      POA poa=getPOA();
      if (poa!=null)
      {
        Object objRef=poa.servant_to_reference(servant);
        NamingContextExt namingService=getNamingService();
        if (namingService!=null)
        {
          NameComponent[] path=namingService.to_name(name);
          namingService.rebind(path,objRef);
          ret=true;
        }
        else
        {
          _logger.error("Cannot register object (naming service): "+name);
        }
      }
    }
    catch(Exception e)
    {
      _logger.error("Cannot register object (naming service): "+name,e);
    }
    return ret;
  }

  /**
   * Publish a CORBA servant in the naming service.
   * @param servantInfo Infos on servant to publish.
   * @return <code>true</code> if successful, <code>false</code> otherwise.
   */
  @Override
  protected boolean publishInNamingService(ServantInfo servantInfo)
  {
    boolean ok=false;
    String serviceName=servantInfo.getServerName();
    HostAndApplication where=servantInfo.getServiceLocation();
    Servant servant=servantInfo.getServant();
    String objectName=CORBAConstants.getCORBAObjectName(serviceName,where);
    int nbTries=0;
    while (nbTries<MAX_NAMING_SERVICE_REGISTRATION_TRIES)
    {
      ok=unsafePublishInNamingService(objectName,servant);
      if (!ok)
      {
        nbTries++;
        SleepManager.sleep(SLEEP_BETWEEN_TRIES);
      }
      else
      {
        break;
      }
    }
    if (!ok)
    {
      _logger.error("Unable to publish object ["+objectName+"] in the naming service !");
    }
    return ok;
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
