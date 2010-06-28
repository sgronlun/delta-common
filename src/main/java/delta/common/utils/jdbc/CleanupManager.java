package delta.common.utils.jdbc;

import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

public class CleanupManager
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  public static void cleanup(ResultSet obj_p)
  {
    try
    {
      if(obj_p!=null)
      {
        obj_p.close();
      }
    }
    catch(Exception exception)
    {
      _logger.error("Error on ResultSet closing.", exception);
    }
  }

  public static void cleanup(Statement obj_p)
  {
    try
    {
      if(obj_p!=null)
      {
        obj_p.close();
      }
    }
    catch(Exception exception)
    {
      _logger.error("Error on Statement closing.", exception);
    }
  }

  public static void cleanup(Statement stmt_p, ResultSet rs_p)
  {
    cleanup(rs_p);
    cleanup(stmt_p);
  }
}
