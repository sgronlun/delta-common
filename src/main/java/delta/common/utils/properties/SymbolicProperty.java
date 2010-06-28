package delta.common.utils.properties;

/**
 * Symbolic property.
 * A symbolic property has a name and an associated natural integer value.
 * @author DAM
 */
public class SymbolicProperty
{
  private int _value;
  private String _name;

  /**
   * Package protected constructor.
   * Instances of this class must be created from a properties registry.
   * @param name Name of property to create.
   * @param value Internal value for this property.
   */
  SymbolicProperty(final String name, final int value)
  {
    _name=name;
    _value=value;
  }

  /**
   * Get the internal value of this property.
   * @return the internal value of this property.
   */
  int getIntValue()
  {
    return _value;
  }

  /**
   * Get the name of this property.
   * @return the name of this property.
   */
  public String getName()
  {
    return _name;
  }
}
