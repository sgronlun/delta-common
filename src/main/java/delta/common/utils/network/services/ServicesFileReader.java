package delta.common.utils.network.services;

import java.io.File;
import java.util.StringTokenizer;

import delta.common.utils.files.TextFileReader;

/**
 * Utility class to read network services information from a file.<br>
 * It reads files constituted of lines which contain white separated fields :
 * service_name, ip_port and optional hostname.<br>
 * Comments begin with "#".<br>
 * For instance :<br>
 * <br>
 * <i>#service_name     ip_port     hostname<br>
 * my_service         1000       tequila<br>
 * another_service    1001<br></i>
 * <br>
 */
public class ServicesFileReader
{
  private static final String COMMENT_PREFIX = "#";
  private static final String SEPARATORS = " \t";

  /**
   * Builds a service register from a file.
   * @param filename file to be read.
   * @return Newly built ServiceRegister instance or exception.
   */
  public NetworkServicesRegistry parseServices(File filename)
  {
    TextFileReader parser=new TextFileReader(filename);
    if (!parser.start()) return null;

    NetworkServicesRegistry return_l=new NetworkServicesRegistry();
    ServiceInfo location_l=null;
    String line_l;
    String serviceName_l;
    String hostName_l;
    String portStr_l;
    short portNumber_l;
    while(true)
    {
      line_l=parser.getNextLine();
      if (line_l==null) break;
      if (line_l.startsWith(COMMENT_PREFIX)) continue;
      serviceName_l="";
      hostName_l=null;
      portNumber_l=-1;
      StringTokenizer st=new StringTokenizer(line_l,SEPARATORS);
      if (st.hasMoreTokens()) serviceName_l=st.nextToken(); else continue;
      if (st.hasMoreTokens()) portStr_l=st.nextToken(); else continue;
      try { portNumber_l=Short.parseShort(portStr_l); }
      catch(NumberFormatException nfe) { continue; }
      if (st.hasMoreTokens()) hostName_l=st.nextToken();
      location_l=new ServiceInfo(serviceName_l,hostName_l,portNumber_l);
      return_l.addServiceInfo(location_l);
    }
    parser.terminate();
    return return_l;
  }
}
