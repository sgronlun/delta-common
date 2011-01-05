package delta.common.utils.misc;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * Manages sleep time.
 * @author DAM
 */
public class SleepManager
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  /**
   * Make this thread sleep for a specified amount of time. 
   * @param milliseconds Time to sleep.
   */
  public static void sleep(long milliseconds)
  {
    try
    {
      Thread.sleep(milliseconds);
    }
    catch(InterruptedException ie)
    {
      _logger.error(ie);
    }
  }
}
