package delta.common.framework.objects.sql;

import java.sql.Connection;
import java.sql.SQLException;

import delta.common.framework.objects.data.DataObject;
import delta.common.framework.objects.data.ObjectDriver;

public class ObjectSqlDriver<E extends DataObject<E>> extends ObjectDriver<E>
{
  private Connection _connection;
  private DatabaseType _dbType;

  protected ObjectSqlDriver()
  {
    // Nothing !!
  }

  protected boolean usesHSQLDB()
  {
    return (_dbType==DatabaseType.HSQLDB);
  }

  public void setConnection(Connection c, DatabaseType dbType)
  {
    if(c!=_connection)
    {
      Connection oldConnection=_connection;
      _connection=c;
      _dbType=dbType;
      connectionChanged(oldConnection, c);
    }
  }

  public Connection getConnection()
  {
    return _connection;
  }

  protected void connectionChanged(Connection oldConnection, Connection newConnection)
  {
    destroyPreparedStatements(oldConnection);
    buildPreparedStatements(newConnection);
  }

  protected void destroyPreparedStatements(Connection oldConnection)
  {
    // Nothing to do !
  }

  protected void buildPreparedStatements(Connection newConnection)
  {
    // Nothing to do !
  }

  public void commit() throws SQLException
  {
    _connection.commit();
  }
}
