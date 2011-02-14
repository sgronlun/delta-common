package delta.common.framework.jobs;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * Controller used to execute jobs on a pool of threads.
 * @author DAM
 */
public class MultiThreadedJobExecutor
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  private JobPool _pool;
  private int _nbThreads;
  private Thread[] _threads;
  private Worker[] _workers;

  /**
   * Constructor.
   * @param pool Pool of jobs to execute.
   * @param nbThreads Number of threads to use.
   */
  public MultiThreadedJobExecutor(JobPool pool, int nbThreads)
  {
    if (nbThreads<=0)
    {
      _logger.warn("Corrected specified number of thread ("+nbThreads+"->1");
      nbThreads=1;
    }
    if (nbThreads>10)
    {
      _logger.warn("Corrected specified number of thread ("+nbThreads+"->10");
      nbThreads=10;
    }
    _nbThreads=nbThreads;
    _threads=new Thread[nbThreads];
    _workers=new Worker[nbThreads];
    _pool=pool;
  }

  private void buildThreads()
  {
    for(int i=0;i<_nbThreads;i++)
    {
      _workers[i]=new Worker(_pool);
      _threads[i]=new Thread(_workers[i],"Worker "+i);
    }
  }

  private void startThreads()
  {
    for(int i=0;i<_nbThreads;i++)
    {
      _threads[i].start();
    }
  }

  /**
   * Start jobs.
   */
  public void start()
  {
    buildThreads();
    startThreads();
  }

  /**
   * Indicates if all jobs are finished or not.
   * @return <code>true</code> if they are, <code>false</code> otherwise.
   */
  public boolean isFinished()
  {
    int nbRunning=0;
    for(int i=0;i<_nbThreads;i++)
    {
      if ((_threads[i]!=null) && (_threads[i].getState()==Thread.State.TERMINATED))
      {
        _threads[i]=null;
        _workers[i]=null;
      }
      else
      {
        nbRunning++;
      }
    }
    return (nbRunning==0);
  }

  /**
   * Wait for the completion of all jobs.
   */
  public void waitForCompletion()
  {
    for(int i=0;i<_nbThreads;i++)
    {
      if (_threads[i]!=null)
      {
        try
        {
          _threads[i].join();
          _threads[i]=null;
          _workers[i]=null;
        }
        catch(InterruptedException iE)
        {
          _logger.error("",iE);
        }
      }
    }
  }

  /**
   * Get the associated job pool.
   * @return the associated job pool.
   */
  public JobPool getPool()
  {
     return _pool;
  }

  /**
   * Get the number of threads managed by this controller.
   * @return a number of threads.
   */
  public int getNbThreads()
  {
     return _nbThreads;
  }

  /**
   * Get the worker associated with the given thread index.
   * @param index A thread index (starting at 0, but less than the number of threads).
   * @return A worker or <code>null</code> if the targeted thread has finished.
   */
  public Worker getWorker(int index)
  {
    return _workers[index];
  }
}
