package delta.common.utils.types;

import delta.common.utils.BooleanTools;

/**
 * Type for "boolean" values.
 * @author DAM
 */
public class BooleanType extends Type
{
  // Type name
  /**
   * Name of type.
   */
  public static final String TYPE_NAME="BOOLEAN";

  /**
   * Default constructor.
   */
  public BooleanType()
  {
    super();
    TypeClassesRegistry typeClassesRegistry=TypeClassesRegistry.getInstance();
    TypeClass booleanClass=typeClassesRegistry.getTypeClassByName(BooleanType.TYPE_NAME);
    setNameAndType("",booleanClass);
  }

  /**
   * Get the class of the values for this type.
   * @return A class.
   */
  public Class<?> getValueType()
  {
    return Boolean.class;
  }

  /**
   * Set the value of a parameter.
   * @param parameterName Parameter's name.
   * @param parameterValue Parameter's value.
   * @return Always <code>false</code> (no accepted parameter).
   */
  @Override
  protected boolean setParameterValue(String parameterName, Object parameterValue)
  {
    return false;
  }

  /**
   * Build an instance object from a value.
   * @param value Value to use to build the instance.
   * @return A value for this type or <code>null</code> if value is unusable.
   */
  @Override
  public Boolean buildInstanceFromValue(Object value)
  {
    Boolean ret=BooleanTools.buildBoolean(value);
    return ret;
  }
}
