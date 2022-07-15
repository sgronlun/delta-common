package delta.common.utils.expressions.logical;

/**
 * Simple element in a logical expression. Stores a data value. 
 * @param <T> Type of managed data.
 * @author DAM
 */
public class SimpleLogicalExpression<T> extends AbstractLogicalExpression<T>
{
  private T _value;

  /**
   * Constructor.
   * @param value Value to compare to.
   */
  public SimpleLogicalExpression(T value)
  {
    super();
    _value=value;
  }

  /**
   * Get the value to use.
   * @return A value or <code>null</code>.
   */
  public T getValue()
  {
    return _value;
  }

  @Override
  public String toString()
  {
    return _value!=null?_value.toString():"";
  }
}
