package delta.common.utils.values;

/**
 * Integer value provider that serves a constant integer value.
 * @author DAM
 */
public class ConstantIntegerProvider implements IntegerValueProvider
{
  private int _value;

  /**
   * Constructor.
   * @param value Managed value.
   */
  public ConstantIntegerProvider(int value)
  {
    _value=value;
  }

  @Override
  public int get()
  {
    return _value;
  }

  @Override
  public String toString()
  {
    return String.valueOf(_value);
  }
}
