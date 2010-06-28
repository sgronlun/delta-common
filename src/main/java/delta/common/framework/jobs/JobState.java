package delta.common.framework.jobs;

/**
 * Provides fly-weight instances for the possible states of a job.
 * @author DAM
 */
public final class JobState
{
  /**
   * "to do" job state.
   */
  public static final JobState TO_DO=new JobState("TO_DO");

  /**
   * "running" job state.
   */
  public static final JobState RUNNING=new JobState("RUNNING");

  /**
   * "finished" job state.
   */
  public static final JobState FINISHED=new JobState("FINISHED");

  private String _name;

  /**
   * Private constructor.
   * @param name Job state name.
   */
  private JobState(String name)
  {
    _name=name;
  }

  /**
   * Get the name of this state.
   * @return the name of this state.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Famous toString() method.
   * @return A stringified representation of this object.
   */
  @Override
  public String toString()
  {
    return _name;
  }
}
