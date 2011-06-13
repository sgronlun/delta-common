package delta.common.utils.types;

/**
 * Type for "type" values.
 * @author DAM
 */
public class TypeType extends Type
{
  // Type name
  /**
   * Name of type.
   */
  public static final String TYPE_NAME="TYPE";

  // Parameter names
  /**
   * Registry name parameter.
   */
  public static final String PARAM_NAMESPACE="NAMESPACE";

  private String _defaultNamespace;

  /**
   * Default constructor.
   */
  public TypeType()
  {
    this(TypesRegistry.DEFAULT_NAMESPACE);
  }

  /**
   * Full constructor.
   * @param defaultNamespace Namespace to use as the default namespace.
   */
  public TypeType(String defaultNamespace)
  {
    super();
    TypeClassesRegistry typeClassesRegistry=TypeClassesRegistry.getInstance();
    TypeClass typeClass=typeClassesRegistry.getTypeClassByName(TypeType.TYPE_NAME);
    setNameAndType("",typeClass);
    uncheckedSetParamValue(PARAM_NAMESPACE,defaultNamespace);
  }

  /**
   * Get the class of the values for this type.
   * @return A class.
   */
  public Class<?> getValueType()
  {
    return Type.class;
  }

  /**
   * Set the value of a parameter.
   * @param parameterName Parameter's name.
   * @param parameterValue Parameter's value.
   * @return <code>true</code> if parameter was accepted, <code>false</code> otherwise.
   */
  @Override
  protected boolean setParameterValue(String parameterName, Object parameterValue)
  {
    boolean ret=true;
    if (PARAM_NAMESPACE.equals(parameterName))
    {
      _defaultNamespace=(String)parameterValue;
    }
    else
    {
      ret=false;
    }
    return ret;
  }

  /**
   * Build an instance object from a value.
   * @param value Value to use to build the instance.
   * @return A value for this type or <code>null</code> if value is unusable.
   */
  @Override
  public Type buildInstanceFromValue(Object value)
  {
    Type ret=null;
    if (value instanceof Type)
    {
      ret=(Type)value;
    }
    else if (value instanceof String)
    {
      ret=TypesRegistry.getInstance().getType(_defaultNamespace,(String)value);
    }
    return ret;
  }
}
