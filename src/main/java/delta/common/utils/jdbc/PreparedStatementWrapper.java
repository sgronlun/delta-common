package delta.common.utils.jdbc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.PreparedStatement;

/**
 * A wrapper for prepared statements.
 * @author DAM
 */
public class PreparedStatementWrapper implements InvocationHandler
{
  private PreparedStatement _impl;

  private PreparedStatementWrapper(PreparedStatement ps)
  {
    _impl=ps;
  }

  /**
   * Build a proxy that wraps a real prepared statement.
   * @param realPS Prepared statement to wrap.
   * @return A prepared statement proxy.
   */
  public static PreparedStatement buildProxy(PreparedStatement realPS)
  {
    InvocationHandler handler = new PreparedStatementWrapper(realPS);
    PreparedStatement psProxy=(PreparedStatement) Proxy.newProxyInstance(PreparedStatement.class.getClassLoader(),new Class[] { PreparedStatement.class },handler);
    return psProxy;
  }

  /**
   * Invoke method on wrapped object instead of on the proxy.
   * @param proxy Not used.
   * @param method Method to invoke.
   * @param args Arguments to use.
   * @return Result.
   * @throws Throwable
   */
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
  {
    Object ret=method.invoke(_impl,args);
    return ret;
  }
}
