package delta.common.framework.jobs;

/**
 * @author DAM
 */
public interface JobListener
{
  /**
   * Callback method invoked when a job starts.
   * @param job Involved job.
   */
  public void jobStarted(Job job);

  /**
   * Callback method invoked when a job has finished.
   * @param job Involved job.
   */
  public void jobFinished(Job job);

  public void jobStatusUpdated(Job job);
}
