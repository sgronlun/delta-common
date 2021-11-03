package delta.common.utils.math;

/**
 * A range with optional limits.
 * @author DAM
 */
public class Range
{
  private Integer _min;
  private Integer _max;

  /**
   * Constructor.
   */
  public Range()
  {
    _min=null;
    _max=null;
  }

  /**
   * Constructor.
   * @param min Minimum.
   * @param max Maximum.
   */
  public Range(Integer min, Integer max)
  {
    _min=min;
    _max=max;
  }

  /**
   * Constructor with both limits.
   * @param min Minimum value (inclusive).
   * @param max Maximum value (inclusive).
   */
  public Range(int min, int max)
  {
    _min=Integer.valueOf(min);
    _max=Integer.valueOf(max);
  }

  /**
   * Get the limit: minimum value.
   * @return A value or <code>null</code> if none.
   */
  public Integer getMin()
  {
    return _min;
  }

  /**
   * Get the limit: maximum value.
   * @return A value or <code>null</code> if none.
   */
  public Integer getMax()
  {
    return _max;
  }

  /**
   * Indicates if this range contains the given value.
   * @param value A value.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean contains(int value)
  {
    if ((_min!=null) && (value<_min.intValue()))
    {
      return false;
    }
    if ((_max!=null) && (value>_max.intValue()))
    {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode()
  {
    return ((_min!=null)?_min.intValue():0)*17+((_max!=null)?_max.intValue():0);
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof Range)
    {
      Range other=(Range)obj;
      if ((_min==null) && (other._min!=null))
      {
        return false;
      }
      if ((_min!=null) && (other._min==null))
      {
        return false;
      }
      if ((_min!=null) && (_min.intValue()!=other._min.intValue()))
      {
        return false;
      }
      if ((_max==null) && (other._max!=null))
      {
        return false;
      }
      if ((_max!=null) && (other._max==null))
      {
        return false;
      }
      if ((_max!=null) && (_max.intValue()!=other._max.intValue()))
      {
        return false;
      }
      return true;
    }
    return false;
  }

  @Override
  public String toString()
  {
    if ((_min!=null) || (_max!=null))
    {
      StringBuilder sb=new StringBuilder();
      if (_min!=null)
      {
        sb.append(_min);
      }
      sb.append(':');
      if (_max!=null)
      {
        sb.append(_max);
      }
      return sb.toString();
    }
    return ":";
  }
}
