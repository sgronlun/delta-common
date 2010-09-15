package delta.common.utils.misc;

/**
 * Holds an integer value.
 * It can be used where a simple <tt>Integer</tt> is not adequat
 * because it is not mutable.
 * @author DAM
 */
public class IntegerHolder
{
  private int _value;

  /**
   * Constructor.
   */
  public IntegerHolder()
  {
    _value=0;
  }

  /**
   * Full constructor.
   * @param value Initial value.
   */
  public IntegerHolder(int value)
  {
    _value=value;
  }

  /**
   * Get the value of the internally managed integer.
   * @return the value of the internally managed integer.
   */
  public int getInt()
  {
    return _value;
  }

  /**
   * Set the value of the internally managed integer.
   * @param value Value to set.
   */
  public void setInt(int value)
  {
    _value=value;
  }

  /**
   * Add one to the internally managed value.
   */
  public void increment()
  {
    _value++;
  }

  /**
   * Get the hash code for this object.
   * @return the hash code for this object.
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    return _value;
  }

  /**
   * Equality test method.
   * @param obj Object to test with.
   * @return <code>true</code> if it is equals, <code>false</code> otherwise.
   */
  @Override
  public boolean equals(Object obj)
  {
    if (obj instanceof IntegerHolder)
    {
      return _value==((IntegerHolder)obj).getInt();
    }
    return false;
  }

  @Override
  public String toString()
  {
    return String.valueOf(_value);
  }
}
