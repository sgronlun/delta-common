package delta.common.framework.jobs;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * An object capable of running <tt>Job</tt>s from a <tt>JobPool</tt>.
 * @author DAM
 */
public class Worker implements Runnable
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  private JobPool _pool;
  private Job _currentJob;

  /**
   * Constructor.
   * @param pool Associated jobs pool.
   */
  public Worker(JobPool pool)
  {
    _pool=pool;
  }

  /**
   * Stakanov loop.
   */
  public void run()
  {
    while (true)
    {
      Job j=_pool.getNewJob();
      if (j==null) break;
      _currentJob=j;
      _pool.jobStarted(this,j);
      try
      {
        j.doIt();
      }
      catch(Throwable t)
      {
        _logger.error("Job ["+j.getLabel()+"] execution crashed with an exception",t);
      }
      _pool.jobFinished(this,j);
      _currentJob=null;
    }
  }

  /**
   * Get the <tt>Job</tt> currently associated with this instance.
   * @return A <tt>Job</tt> or <code>null</code> if none.
   */
  public Job getCurrentJob()
  {
    return _currentJob;
  }
}
