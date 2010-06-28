package delta.common.utils.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * Tools related to JDBC.
 * @author DAM
 */
public class JDBCTools
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  public static long getPrimaryKey(Connection connection, int index)
  {
    long primaryKey=-1;
    Statement s=null;
    ResultSet rs=null;
    try
    {
      s=connection.createStatement();
      rs=s.executeQuery("CALL IDENTITY();");
      primaryKey=rs.getInt(index);
    }
    catch(SQLException sqlException)
    {
      _logger.error("",sqlException);
    }
    finally
    {
      CleanupManager.cleanup(s,rs);
    }
    return primaryKey;
  }
}
