package delta.common.utils.corba;

import java.io.File;

import delta.common.utils.configuration.Configuration;
import delta.common.utils.configuration.Configurations;
import delta.common.utils.environment.FileSystem;
import delta.common.utils.environment.HostAndApplication;

/**
 * Constants related to CORBA.
 * @author DAM
 */
public class CORBAConstants
{
  /**
   * Name of the JacORB ORB implementation.
   */
  public static final String JACORB_IMPL="JACORB";
  /**
   * Name of the JDK ORB implementation.
   */
  public static final String JDK_IMPL="JDKORB";

  /**
   * Shared mode for IOR files.
   */
  public static final String SHARED="shared";
  /**
   * Local mode for IOR files.
   */
  public static final String LOCAL="local";

  /**
   * Get the name of the default ORB implementation.
   * @return the name of the default ORB implementation.
   */
  public static String getDefaultORBImpl()
  {
    Configuration cfg=Configurations.getConfiguration();
    String orb=cfg.getStringValue("CORBA","ORB_IMPLEMENTATION",JDK_IMPL);
    return orb;
  }

  /**
   * Compute the name of a CORBA object.
   * @param serviceName Name of the CORBA service.
   * @param where Location of the CORBA service (or <code>null</code>).
   * @return A CORBA object name.
   */
  public static String getCORBAObjectName(String serviceName, HostAndApplication where)
  {
    if (where==null)
    {
      return serviceName;
    }
    String appID=where.getApplicationID();
    String hostName=where.getHostName();
    String name=serviceName+"#"+appID+"#"+hostName;
    return name;
  }

  /**
   * Get the filename for an IOR file.
   * @param serviceId Name of the CORBA object (service name and location).
   * @param local Compute a local IOR (<code>true</code>) or a global IOR (<code>false</code>).
   * @return the filename for a local IOR file.
   */
  private static File computeIORFile(String serviceId, boolean local)
  {
    //Target target=Target.getInstance();
    File defaultIORDir;
    File iorFile;
    if (local)
    {
      // todo defaultIORDir=target.getLocalDir();
      defaultIORDir=FileSystem.getTmpDir();
      defaultIORDir=new File(defaultIORDir,"ior");
      Configuration cfg=Configurations.getConfiguration();
      File iorDir=cfg.getFileValue("CORBA","LOCAL_IORS_PATH",defaultIORDir);
      iorFile=new File(iorDir,serviceId+".ior");
    }
    else
    {
      // todo defaultIORDir=target.getSharedDir();
      defaultIORDir=FileSystem.getTmpDir();
      defaultIORDir=new File(defaultIORDir,"ior");
      Configuration cfg=Configurations.getConfiguration();
      File iorDir=cfg.getFileValue("CORBA","GLOBAL_IORS_PATH",defaultIORDir);
      iorFile=new File(iorDir,serviceId+".ior");
    }
    return iorFile;
  }

  /**
   * Get the IOR file used for a CORBA object.
   * @param serviceName Service name.
   * @param where Service location.
   * @param local Local or shared ior file.
   * @return An IOR file.
   */
  public static File getIORFile(String serviceName, HostAndApplication where, boolean local)
  {
    String serviceId=CORBAConstants.getCORBAObjectName(serviceName,where);
    File iorFile=CORBAConstants.computeIORFile(serviceId,local);
    return iorFile;
  }
}
