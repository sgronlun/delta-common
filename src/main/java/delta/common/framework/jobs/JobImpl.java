package delta.common.framework.jobs;

/**
 * Interface of a job implementation.
 * @author DAM
 */
public interface JobImpl
{
  /**
   * Get a label for this job.
   * @return a readable label.
   */
  String getLabel();
  
  /**
   * Perform the job.
   */
  void doIt();
}
