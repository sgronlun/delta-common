package delta.common.framework.jobs;

import java.util.Random;

import delta.common.utils.misc.SleepManager;

public class SleepJob implements JobImpl
{
  private static int _jobCounter=0;
  private int _jobDuration;

  public SleepJob()
  {
    Random r=new Random(System.currentTimeMillis());
    _jobDuration=r.nextInt(10000);
    _jobCounter++;
  }

  public String getLabel()
  {
    return "Waiting for "+_jobDuration+"ms";
  }

  public void doIt()
  {
    SleepManager.sleep(_jobDuration);
  }
}
