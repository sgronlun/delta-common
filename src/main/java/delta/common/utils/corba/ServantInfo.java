package delta.common.utils.corba;

import java.io.File;

import org.omg.PortableServer.Servant;

import delta.common.utils.environment.HostAndApplication;

/**
 * Storage class for information about a CORBA servant.
 * @author DAM
 */
public class ServantInfo
{
  /**
   * The targeted servant.
   */
  private Servant _servant;
  /**
   * Service info.
   */
  private CORBAServiceInfo _serviceInfo;
  /**
   * IOR file.
   */
  private File _iorFile;
  /**
   * Service location.
   */
  private HostAndApplication _serviceLocation;

  /**
   * Constructor.
   * @param servant A servant reference.
   * @param serviceInfo CORBA service info.
   * @param where Service location (or <code>null</code>).
   */
  ServantInfo(Servant servant, CORBAServiceInfo serviceInfo, HostAndApplication where)
  {
    _servant=servant;
    _serviceInfo=serviceInfo;
    _serviceLocation=where;
    _iorFile=null;
    if (serviceInfo!=null)
    {
      if (serviceInfo.getPublicationMode()==CorbaPublicationMode.IOR_FILE)
      {
        String serviceName=serviceInfo.getServiceName();
        boolean local=serviceInfo.isLocal();
        _iorFile=CORBAConstants.getIORFile(serviceName,where,local);
      }
    }
  }

  /**
   * Constructor.
   * @param servant A servant reference.
   * @param iorFile IOR file to use.
   * @param name Name of object.
   */
  ServantInfo(Servant servant, File iorFile, String name)
  {
    _servant=servant;
    String orbImpl=CORBAConstants.getDefaultORBImpl();
    _serviceInfo=new CORBAServiceInfo(name,orbImpl,CorbaPublicationMode.IOR_FILE);
    _iorFile=iorFile;
    _serviceLocation=null;
  }

  ServantInfo(Servant servant)
  {
    this(servant,null,"");
  }

  /**
   * Return the servant reference.
   * @return The servant reference.
   */
  public Servant getServant()
  {
    return _servant;
  }

  /**
   * The servant identifier.
   * @return The servant identifier.
   */
  public String getServerName()
  {
    String serviceName="";
    if (_serviceInfo!=null)
    {
      serviceName=_serviceInfo.getServiceName();
    }
    return serviceName;
  }

  /**
   * Get the publication mode.
   * @return the publication mode.
   */
  public CorbaPublicationMode getMode()
  {
    CorbaPublicationMode mode=CorbaPublicationMode.NONE;
    if (_serviceInfo!=null)
    {
      mode=_serviceInfo.getPublicationMode();
    }
    return mode;
  }

  /**
   * Get the service location.
   * @return the service location.
   */
  public HostAndApplication getServiceLocation()
  {
    return _serviceLocation;
  }

  /**
   * Get the IOR file path.
   * @return the IOR file path.
   */
  public File getIORFile()
  {
    return _iorFile;
  }

  /**
   * Get a readable string for this object.
   * @return a readable string for this object.
   */
  @Override
  public String toString()
  {
    String serviceInfo=_serviceInfo.toString();
    StringBuilder sb=new StringBuilder(serviceInfo);
    if (_iorFile!=null)
    {
      sb.append('#');
      sb.append(_iorFile);
    }
    String ret=sb.toString();
    return ret;
  }
}
