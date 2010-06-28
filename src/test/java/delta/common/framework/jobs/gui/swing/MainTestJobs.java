package delta.common.framework.jobs.gui.swing;

import java.util.Random;

import delta.common.framework.jobs.JobPool;
import delta.common.framework.jobs.MultiThreadedJobExecutor;
import delta.common.framework.jobs.SleepJob;
import delta.common.utils.misc.SleepManager;

public class MainTestJobs
{
  public static void main(String[] args)
  {
    Random r=new Random(System.currentTimeMillis());
    int nbJobs=r.nextInt(20);
    JobPool pool=new JobPool();
    for (int i=0; i<nbJobs; i++)
    {
      pool.addJob(new SleepJob());
      SleepManager.sleep(10);
    }

    int nbThreads=10;
    if (nbThreads>nbJobs)
    {
      nbThreads=nbJobs;
    }
    MultiThreadedJobExecutor exec=new MultiThreadedJobExecutor(pool,nbThreads);
    MultiThreadedProgressDialog dialog=new MultiThreadedProgressDialog(exec);
    dialog.start();
    exec.start();
    exec.waitForCompletion();
    System.out.println("Execution finished !");
    SleepManager.sleep(2000);
    dialog.stop();
    //System.exit(0);
  }
}
