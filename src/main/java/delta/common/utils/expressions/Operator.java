package delta.common.utils.expressions;

import delta.common.utils.types.Type;

/**
 * @author DAM
 */
public interface Operator
{
  public Type getResultType();
  public int getNumberOfArguments();
  public Type getArgumentType(int argIndex);
}
