package delta.common.utils.variables;

import delta.common.utils.types.Type;

/**
 * Represents a variable. A variable has a name and a type.
 * @author DAM
 */
public class Variable
{
  private String _name;
  private Type _type;

  /**
   * Full constructor.
   * @param name Name of this variable.
   * @param type Variable type.
   */
  public Variable(String name, Type type)
  {
    _name=name;
    _type=type;
  }

  /**
   * Get the name of this variable.
   * @return the name of this variable.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the type of this variable.
   * @return the type of this variable.
   */
  public Type getType()
  {
    return _type;
  }
}
