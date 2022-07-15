package delta.common.utils.values;

/**
 * Simple boolean expression that compares 2 integer values.
 * @author DAM
 */
public class SimpleIntegerExpression implements BooleanValueProvider
{
  private IntegerValueProvider _value1;
  private ComparisonOperator _operator;
  private IntegerValueProvider _value2;

  /**
   * Constructor.
   * @param value1 Value 1.
   * @param operator Comparison operator.
   * @param value2 Value 2.
   */
  public SimpleIntegerExpression(IntegerValueProvider value1, ComparisonOperator operator, IntegerValueProvider value2)
  {
    _value1=value1;
    _operator=operator;
    _value2=value2;
  }

  /**
   * Get the provider for value 1.
   * @return the provider for value 1.
   */
  public IntegerValueProvider getValue1()
  {
    return _value1;
  }

  /**
   * Get the comparison operator.
   * @return the comparison operator.
   */
  public ComparisonOperator getOperator()
  {
    return _operator;
  }

  /**
   * Get the provider for value 2.
   * @return the provider for value 2.
   */
  public IntegerValueProvider getValue2()
  {
    return _value2;
  }

  /**
   * Compute the value of this expression.
   * @return the result of the computation.
   */
  public boolean compute()
  {
    int value1=_value1.get();
    int value2=_value2.get();
    switch(_operator)
    {
      case GREATER: return value1>value2;
      case GREATER_OR_EQUAL: return value1>=value2;
      case EQUAL: return value1==value2;
      case LESS: return value1<value2;
      case LESS_OR_EQUAL: return value1<=value2;
      case NOT_EQUAL: return value1!=value2;
    }
    return false;
  }

  @Override
  public boolean get()
  {
    return compute();
  }

  @Override
  public String toString()
  {
    return _value1+" "+_operator+" "+_value2;
  }
}
