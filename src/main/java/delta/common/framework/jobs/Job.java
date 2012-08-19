package delta.common.framework.jobs;

/**
 * Represents a job, i.e a piece of work that can
 * be done be a piece of software.
 * A job includes :
 * <ul>
 * <li>a state (see class JobState),
 * <li>a label (free text),
 * </ul>
 * @author DAM
 */
public final class Job
{
  private JobImpl _jobImpl;
  private JobState _state;

  /**
   * Full constructor.
   * @param jobImpl Job implementation.
   */
  public Job(JobImpl jobImpl)
  {
    _state=JobState.TO_DO;
    _jobImpl=jobImpl;
  }

  /**
   * Get the label for this job.
   * @return the label for this job.
   */
  public String getLabel()
  {
    String label=_jobImpl.getLabel();
    return label;
  }

  /**
   * Move this job to the specified state.
   * @param state State to set.
   */
  public void setState(JobState state)
  {
    _state=state;
  }

  /**
   * Get the state of this job.
   * @return the state of this job.
   */
  public JobState getState()
  {
    return _state;
  }

  /**
   * Famous toString() method.
   * @return A stringified representation of this object.
   */
  @Override
  public String toString()
  {
    return getLabel();
  }

  /**
   * Job implementation.
   */
  public void doIt()
  {
    _jobImpl.doIt(null);
  }
}
