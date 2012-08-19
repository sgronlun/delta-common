package delta.common.framework.jobs;

/**
 * Interface of a job status.
 * @author DAM
 */
public interface JobStatus
{
  /**
   * Get the job title.
   * @return a displayable string.
   */
  public String getJobTitle();
  
  /**
   * Get the number of levels of the job.
   * @return a strictly positive integer.
   */
  public int getNumberOfLevels();
  
  /**
   * Get the status for the targeted level.
   * @param index Index of the targeted level (starting at 0).
   * @return A job level status or <code>null</code> if not found.
   */
  public JobLevelStatus getLevelStatus(int index); 

  /**
   * Job level status.
   * @author DAM
   */
  public interface JobLevelStatus
  {
    /**
     * Get the label for this job level.
     * @return a displayable string.
     */
    public String getLabel();
    /**
     * Get the minimum value for this job level.
     * @return An integer value.
     */
    public int getMinimumValue();
    /**
     * Get the maximum value for this job level.
     * @return An integer value.
     */
    public int getMaximumValue();
    /**
     * Get the current value for this job level.
     * @return An integer value.
     */
    public int getCurrentValue();
  }
  
  /**
   * Get the state of the managed job.
   * @return the state of the managed job.
   */
  public JobState getJobState();
}
