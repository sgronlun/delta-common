package delta.common.utils.types;

/**
 * Describes a type parameter.
 * @author DAM
 */
public class TypeClassParameter
{
  private String _name;
  private String _defaultValue;
  private boolean _mandatory;
  private String _type;

  /**
   * Full constructor.
   * @param name Parameter name.
   * @param type Type of parameter.
   * @param mandatory Mandatory parameter.
   * @param defaultValue Default value for parameter.
   */
  public TypeClassParameter(String name, String type, boolean mandatory, String defaultValue)
  {
    _name=name;
    _type=type;
    _mandatory=mandatory;
    _defaultValue=defaultValue;
  }

  /**
   * Get the name for this parameter.
   * @return the name for this parameter.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the default value for this parameter.
   * @return the default value for this parameter.
   */
  public String getDefaultValue()
  {
    return _defaultValue;
  }

  /**
   * Indicates if the value of this parameter is mandatory (must be defined).
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isMandatory()
  {
    return _mandatory;
  }
  /**
   * Get the type for this parameter.
   * @return the type for this parameter.
   */
  public String getType()
  {
    return _type;
  }
}
