package delta.common.utils.types;

import delta.common.utils.NumericTools;

/**
 * Type for "integer" values.
 * @author DAM
 */
public class IntegerType extends Type
{
  // Type name
  /**
   * Name of type.
   */
  public static final String TYPE_NAME="INTEGER";

  // Parameter names
  /**
   * Minimum parameter.
   */
  public static final String PARAM_MIN="MIN";

  /**
   * Maximum parameter.
   */
  public static final String PARAM_MAX="MAX";

  private Integer _min;
  private Integer _max;

  /**
   * Default constructor.
   */
  public IntegerType()
  {
    this(Integer.MIN_VALUE,Integer.MAX_VALUE);
  }

  /**
   * Partial constructor.
   * @param min Minimum value for objects of this type.
   */
  public IntegerType(int min)
  {
    this(min,Integer.MAX_VALUE);
  }

  /**
   * Full constructor.
   * @param min Minimum value for objects of this type.
   * @param max Maximum value for objects of this type.
   */
  public IntegerType(int min, int max)
  {
    super();
    TypeClassesRegistry typeClassesRegistry=TypeClassesRegistry.getInstance();
    TypeClass integerClass=typeClassesRegistry.getTypeClassByName(IntegerType.TYPE_NAME);
    setNameAndType("",integerClass);
    uncheckedSetParamValue(PARAM_MIN,Integer.valueOf(min));
    uncheckedSetParamValue(PARAM_MAX,Integer.valueOf(max));
  }

  /**
   * Get the class of the values for this type.
   * @return A class.
   */
  public Class<?> getValueType()
  {
    return Integer.class;
  }

  /**
   * Get the minimum value for objects of this type.
   * @return the minimum value for objects of this type.
   */
  public Integer getMin()
  {
    return _min;
  }

  /**
   * Get the maximum value for objects of this type.
   * @return the maximum value for objects of this type.
   */
  public Integer getMax()
  {
    return _max;
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
    if (PARAM_MIN.equals(parameterName))
    {
      _min=NumericTools.buildInteger(parameterValue);
    }
    else if (PARAM_MAX.equals(parameterName))
    {
      _max=NumericTools.buildInteger(parameterValue);
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
  public Integer buildInstanceFromValue(Object value)
  {
    Integer ret=NumericTools.buildInteger(value);
    return ret;
  }
}
