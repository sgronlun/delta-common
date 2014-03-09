package delta.common.utils;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * Misc. tools.
 * @author DAM
 */
public abstract class Tools
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  /**
   * Sleep for a while.
   * @param milliseconds Sleep duration (milliseconds).
   */
  public static void sleep(final long milliseconds)
  {
    try
    {
      Thread.sleep(milliseconds);
    }
    catch (Exception e)
    {
      // Nothing special to do
    }
  }

  /**
   * Safe limited-time wait method.
   * @param o Object to wait on.
   * @param maxTime Maximum time to wait (0 for infinite duration).
   */
  public static void startWaiting(final Object o, final long maxTime)
  {
    try
    {
      synchronized (o)
      {
        o.wait(maxTime);
      }
    }
    catch (InterruptedException e)
    {
      // Nothing special to do
    }
  }

  /**
   * Safe infinite wait method.
   * @param o Object to wait on.
   */
  public static void startWaiting(final Object o)
  {
    try
    {
      synchronized (o)
      {
        o.wait();
      }
    }
    catch (InterruptedException e)
    {
      // Nothing special to do
    }
  }

  /**
   * Load a native library.
   * @param libraryId Library identifier.
   */
  public static void loadLibrary(final String libraryId)
  {
    try
    {
      System.loadLibrary(libraryId);
    }
    catch (Throwable ex)
    {
      try
      {
        System.out.println(System.getProperty("java.library.path"));
      }
      catch (Exception ex2)
      {
        _logger.error("",ex2);
      }
      _logger.error("",ex);
    }
  }
}
