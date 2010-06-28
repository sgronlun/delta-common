package delta.common.utils.misc;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

public class SleepManager
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

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
