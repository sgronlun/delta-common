package delta.common.utils.jdbc;

import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.util.Arrays;
import java.util.Properties;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

public class DriverInspector
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  public static void dumpDriverInfos(Driver d, String url)
  {
    System.out.println("Driver class : "+d.getClass().getName());
    System.out.println("Version "+d.getMajorVersion()+"."+d.getMinorVersion());
    if(d.jdbcCompliant())
    {
      System.out.println("JDBC compliant");
    }
    else
    {
      System.err.println("Not JDBC compliant");
    }
    Properties p=new Properties();
    try
    {
      DriverPropertyInfo[] infos_l=d.getPropertyInfo(url, p);
      for(int i=0;i<infos_l.length;i++)
      {
        DriverPropertyInfo info=infos_l[i];
        System.out.println(info.name+"="+info.value+" required=("+info.required+") description=("+info.description+") choices=("+Arrays.toString(info.choices)+")");
      }
    }
    catch(Exception e)
    {
      _logger.error("Could not get properties for driver", e);
    }
  }

  public static void dumpMetaData(DatabaseMetaData metaData)
  {
    try
    {
      System.out.println("Version JDBC "+metaData.getJDBCMajorVersion()+"."+metaData.getJDBCMinorVersion());
      System.out.println("Supports generated keys : "+metaData.supportsGetGeneratedKeys());
    }
    catch(Exception e)
    {
      _logger.error("Could not get properties for driver", e);
    }
  }
}
