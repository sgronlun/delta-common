package delta.common.framework.jobs;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

public class MultiThreadedJobExecutor
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  private JobPool _pool;
  private int _nbThreads;
  private Thread[] _threads;
  private Worker[] _workers;

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
      _threads[i]=new Thread(_workers[i]);
    }
  }

  private void startThreads()
  {
    for(int i=0;i<_nbThreads;i++)
    {
      _threads[i].start();
    }
  }

  public void start()
  {
    buildThreads();
    startThreads();
  }

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

  public JobPool getPool()
  {
     return _pool;
  }

  public int getNbThreads()
  {
     return _nbThreads;
  }

  public int getWorkerIndex(Worker w)
  {
    for(int i=0;i<_nbThreads;i++)
    {
      if (_workers[i]==w) return i;
    }
    return -1;
  }

  public Worker getWorker(int index)
  {
    return _workers[index];
  }
}
