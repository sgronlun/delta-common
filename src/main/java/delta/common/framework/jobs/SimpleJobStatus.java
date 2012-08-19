package delta.common.framework.jobs;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple job status implementation.
 * @author DAM
 */
public class SimpleJobStatus implements JobStatus
{
  private String _title;
  private JobState _state;
  private List<SimpleJobLevelStatus> _statuses;

  /**
   * Constructor.
   * @param levels Number of levels.
   */
  public SimpleJobStatus(int levels)
  {
    _title=null;
    _state=JobState.TO_DO;
    _statuses=new ArrayList<SimpleJobLevelStatus>();
    for(int i=0;i<levels;i++)
    {
      SimpleJobLevelStatus status=new SimpleJobLevelStatus();
      _statuses.add(status);
    }
  }
  
  /**
   * {@inheritDoc}
   */
  public String getJobTitle()
  {
    return _title;
  }

  /**
   * Set job title.
   * @param title Title to set.
   */
  public void setJobTitle(String title)
  {
    _title=title;
  }
  
  /**
   * Get the number of status levels.
   */
  public int getNumberOfLevels()
  {
    return _statuses.size();
  }

  /**
   * Get the status for a given level.
   * @param index Index of the targeted level.
   * @return A job level status or <code>null</code> if not found.
   */
  public SimpleJobLevelStatus getEditableLevelStatus(int index)
  {
    SimpleJobLevelStatus ret=null;
    if ((index>=0) && (index<_statuses.size()))
    {
      ret=_statuses.get(index);
    }
    return ret;
  }

  public JobLevelStatus getLevelStatus(int index)
  {
    return getEditableLevelStatus(index);
  }

  /**
   * Set job state.
   * @param state State to set.
   */
  public void setJobState(JobState state)
  {
    _state=state;
  }

  public JobState getJobState()
  {
    return _state;
  }

  /**
   * Simple job level status implementation.
   * @author DAM
   */
  public class SimpleJobLevelStatus implements JobLevelStatus
  {
    private int _min;
    private int _max;
    private int _current;
    private String _label;

    /**
     * Default constructor.
     */
    public SimpleJobLevelStatus()
    {
      this(0,100,"");
    }

    /**
     * Constructor.
     * @param min Minimum value.
     * @param max Maximum value.
     * @param label Label.
     */
    public SimpleJobLevelStatus(int min, int max, String label)
    {
      _min=min;
      _max=max;
      _current=_min;
      _label=label;
    }

    public String getLabel()
    {
      return _label;
    }

    public int getMinimumValue()
    {
      return _min;
    }

    public int getMaximumValue()
    {
      return _max;
    }

    public int getCurrentValue()
    {
      return _current;
    }

    /**
     * Set current label.
     * @param label Label to set.
     */
    public void setLabel(String label)
    {
      _label=label;
    }

    /**
     * Set range.
     * @param min Minimum value.
     * @param max Maximum value.
     */
    public void setRange(int min, int max)
    {
      _min=min;
      _max=max;
    }

    /**
     * Set current value.
     * @param value Value to set.
     */
    public void setCurrentValue(int value)
    {
      _current=value;
    }
  }
}
