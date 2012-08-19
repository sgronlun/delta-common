package delta.common.framework.jobs;

import java.util.Random;

import delta.common.utils.misc.SleepManager;

/**
 * A simple job implementation that just sleeps for a while.
 * @author DAM
 */
public class SleepJob implements JobImpl
{
  private int _jobDuration;
  private boolean _doCrash;

  /**
   * Constructor.
   */
  public SleepJob()
  {
    this(0,10000,false);
  }

  /**
   * Constructor.
   * @param minDuration Minimum sleep duration (ms).
   * @param maxDuration Maximum sleep duration (ms).
   * @param doCrash Indicates if the job should terminate gracefully or not.
   */
  public SleepJob(int minDuration, int maxDuration, boolean doCrash)
  {
    if (minDuration>maxDuration)
    {
      throw new IllegalArgumentException("minDuration="+minDuration+">maxDuration="+maxDuration);
    }
    if (minDuration<0)
    {
      throw new IllegalArgumentException("minDuration="+minDuration);
    }
    if (maxDuration<0)
    {
      throw new IllegalArgumentException("maxDuration="+maxDuration);
    }
    int delta=maxDuration-minDuration;
    if (delta>0)
    {
      Random r=new Random(System.currentTimeMillis());
      _jobDuration=minDuration+r.nextInt(maxDuration-minDuration+1);
    }
    else
    {
      _jobDuration=minDuration;
    }
    _doCrash=doCrash;
  }

  public String getLabel()
  {
    return "Waiting for "+_jobDuration+"ms";
  }

  public void doIt(JobSupport support)
  {
    SleepManager.sleep(_jobDuration);
    if (_doCrash)
    {
      throw new NullPointerException();
    }
  }

  public boolean interrupt()
  {
    return false;
  }
}
