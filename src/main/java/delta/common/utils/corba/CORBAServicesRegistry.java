package delta.common.utils.corba;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.NumericTools;
import delta.common.utils.configuration.Configuration;
import delta.common.utils.configuration.Configurations;
import delta.common.utils.environment.Host;
import delta.common.utils.text.StringSplitter;
import delta.common.utils.traces.UtilsLoggers;

/**
 * Registry for CORBA services.
 * It maps CORBA services (identified by a name) with hostname, server name (application name) and port.
 * @author DAM
 */
public class CORBAServicesRegistry
{
  private static final Logger _logger=UtilsLoggers.getCorbaLogger();

  private static final String CORBA_SERVERS_SECTION="CORBA_SERVERS";
  private static final String CORBA_SERVICES_SECTION="CORBA_SERVICES";

  /**
   * Reference to the sole instance of this class
   */
  private static CORBAServicesRegistry _instance;

  /**
   * Maps service names to service info.
   */
  private HashMap<String,CORBAServiceInfo> _mapServices;
  /**
   * Maps server names to server info.
   */
  private HashMap<String,CORBAServerInfo> _mapServers;

  /**
   * Get the sole instance of this class.
   * @return The sole instance of this class.
   */
  public static CORBAServicesRegistry getInstance()
  {
    if (_instance==null)
    {
      _instance=new CORBAServicesRegistry();
    }
    return _instance;
  }

  /**
   * Private constructor.
   *
   */
  private CORBAServicesRegistry()
  {
    _mapServices=new HashMap<String,CORBAServiceInfo>();
    _mapServers=new HashMap<String,CORBAServerInfo>();
    parseServers();
    parseServices();
  }

  /**
   * Get all information about a service.
   * @param name Service name.
   * @return Found information or <code>null</code>.
   */
  public CORBAServiceInfo getInfoForService(String name)
  {
    if (name==null) return null;
    CORBAServiceInfo ret=_mapServices.get(name);
    return ret;
  }

  /**
   * Get all information about a server.
   * @param name Server name.
   * @return Found information or <code>null</code>.
   */
  public CORBAServerInfo getInfoForServer(String name)
  {
    if (name==null) return null;
    CORBAServerInfo ret=_mapServers.get(name);
    return ret;
  }

  /**
   * Get the port used by a server (application).
   * @param serverName Server name.
   * @return A port number or 0 if no such server is registered.
   */
  public int getPortForServer(String serverName)
  {
    CORBAServerInfo serverInfo=getInfoForServer(serverName);
    int ret=0;
    if (serverInfo!=null)
    {
      ret=serverInfo.getPort();
    }
    return ret;
  }

  /**
   * Register a service info.
   * @param serviceInfo Service info.
   */
  private void registerServiceInfo(CORBAServiceInfo serviceInfo)
  {
    String serviceName=serviceInfo.getServiceName();
    CORBAServiceInfo old=_mapServices.put(serviceName,serviceInfo);
    if (old!=null)
    {
      _logger.warn("Service ["+serviceName+"] already registered.");
    }
  }

  /**
   * Register a server info.
   * @param serverInfo Server info.
   */
  private void registerServerInfo(CORBAServerInfo serverInfo)
  {
    String serverName=serverInfo.getServerName();
    CORBAServerInfo old=_mapServers.put(serverName,serverInfo);
    if (old!=null)
    {
      _logger.warn("Server ["+serverName+"] already registered.");
    }
  }

  private void parseServices()
  {
    Configuration cfg=Configurations.getConfiguration();
    String[] keys=cfg.getVariableNames(CORBA_SERVICES_SECTION);
    String key,value;
    for(int i=0;i<keys.length;i++)
    {
      key=keys[i];
      value=cfg.getStringValue(CORBA_SERVICES_SECTION,key,null);
      parseService(key,value);
    }
  }

  private void parseService(String key, String value)
  {
    _logger.debug("Parsing CORBA service description ["+key+"]=["+value+"]");
    try
    {
      HashMap<String,String> map=parseFieldValues(value);
      String corbaImpl=map.get("corbaImpl");
      if (corbaImpl==null)
      {
        corbaImpl=CORBAConstants.getDefaultORBImpl();
      }
      String publication=map.get("publication");
      CorbaPublicationMode mode=CorbaPublicationMode.getByName(publication);
      if (mode!=null)
      {
        CORBAServiceInfo info=new CORBAServiceInfo(key,corbaImpl,mode);
        if (mode==CorbaPublicationMode.CORBA_LOC)
        {
          String server=map.get("server");
          if (server==null)
          {
            _logger.warn("server is needed in CORBA_LOC mode. Service="+key+", using DEFAULT_SERVER as a default value.");
            server="DEFAULT_SERVER";
          }
          info.setServerName(server);
        }
        else if (mode==CorbaPublicationMode.IOR_FILE)
        {
          String scope=map.get("scope");
          if (scope==null)
          {
            _logger.warn("scope is needed in IOR_FILE mode. Service="+key+", using local as a default value.");
            scope=CORBAConstants.LOCAL;
          }
          info.setScope(scope);
        }
        registerServiceInfo(info);
      }
      else
      {
        _logger.error("Missing infos in service description ["+value+"]");
      }
    }
    catch(Exception e)
    {
      _logger.error("Cannot parse service description ["+value+"]");
    }
  }

  private void parseServers()
  {
    Configuration cfg=Configurations.getConfiguration();
    String[] keys=cfg.getVariableNames(CORBA_SERVERS_SECTION);
    String key,value;
    for(int i=0;i<keys.length;i++)
    {
      key=keys[i];
      value=cfg.getStringValue(CORBA_SERVERS_SECTION,key,null);
      parseServer(key,value);
    }
  }

  private void parseServer(String key, String value)
  {
    _logger.debug("Parsing CORBA server description ["+key+"]=["+value+"]");
    try
    {
      HashMap<String,String> map=parseFieldValues(value);
      String host=map.get("host");
      String portStr=map.get("port");
      Integer port=NumericTools.parseInteger(portStr);
      if ((host!=null) && (port!=null))
      {
        if (Host.LOCALHOST_ALIAS.equals(host))
        {
          host=Host.getLocalHost().getName();
        }
        CORBAServerInfo info=new CORBAServerInfo(key,host,port.intValue());
        registerServerInfo(info);
      }
      else
      {
        _logger.error("Missing infos in server description ["+value+"]");
      }
    }
    catch(Exception e)
    {
      _logger.error("Cannot parse server description ["+value+"]");
    }
  }

  private HashMap<String,String> parseFieldValues(String value)
  {
    HashMap<String,String> map=new HashMap<String,String>();
    List<String> items=StringSplitter.splitAsList(value,'/');
    if ((items!=null) && (items.size()>0))
    {
      String item;
      String[] field;
      for(Iterator<String> it=items.iterator();it.hasNext();)
      {
        item=it.next();
        field=parseField(item);
        if (field!=null)
        {
          map.put(field[0],field[1]);
        }
      }
    }
    return map;
  }

  private String[] parseField(String field)
  {
    if (field==null) return null;
    String[] ret=null;
    int index=field.indexOf('=');
    if (index!=-1)
    {
      ret=new String[2];
      ret[0]=field.substring(0,index);
      ret[1]=field.substring(index+1);
    }
    else
    {
      _logger.error("Invalid field ["+field+"]");
    }
    return ret;
  }
}
