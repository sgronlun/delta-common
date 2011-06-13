package delta.common.utils.types;

import delta.common.utils.NumericTools;

/**
 * Type for "string" values.
 * @author DAM
 */
public class StringType extends Type
{
  // Type name
  /**
   * Name of type.
   */
  public static final String TYPE_NAME="STRING";

  // Parameter names
  /**
   * Minimum length parameter.
   */
  public static final String PARAM_MIN_LENGTH="MIN_LENGTH";

  /**
   * Maximum length parameter.
   */
  public static final String PARAM_MAX_LENGTH="MAX_LENGTH";

  /**
   * Multilines or not.
   */
  public static final String PARAM_MULTIPLE_LINES="MULTILINES";

  /**
   * Regexp parameter.
   */
  public static final String PARAM_REGEXP="REGEXP";

  private Integer _min;
  private Integer _max;
  private Boolean _multipleLines;
  private String _regexp;

  /**
   * Default constructor.
   */
  public StringType()
  {
    this(0,Integer.MAX_VALUE);
  }

  /**
   * Partial constructor.
   * @param max Max length for strings of this type.
   */
  public StringType(final int max)
  {
    this(0,max);
  }

  /**
   * Partial constructor.
   * @param min Minimum length for strings of this type.
   * @param max Maximum length for strings of this type.
   */
  public StringType(final int min, final int max)
  {
    this(min,max,false,null);
  }

  /**
   * Full constructor.
   * @param min Minimum length for strings of this type.
   * @param max Maximum length for strings of this type.
   * @param multipleLines Indicates if multiple lines are allowed.
   * @param regexp Regular expression or <code>null</code>.
   */
  public StringType(final int min, final int max, final boolean multipleLines, final String regexp)
  {
    super();
    TypeClassesRegistry typeClassesRegistry=TypeClassesRegistry.getInstance();
    TypeClass stringClass=typeClassesRegistry.getTypeClassByName(StringType.TYPE_NAME);
    setNameAndType("",stringClass);
    uncheckedSetParamValue(PARAM_MIN_LENGTH,Integer.valueOf(min));
    uncheckedSetParamValue(PARAM_MAX_LENGTH,Integer.valueOf(max));
    uncheckedSetParamValue(PARAM_MULTIPLE_LINES,Boolean.valueOf(multipleLines));
    uncheckedSetParamValue(PARAM_REGEXP,regexp);
  }

  /**
   * Get the class of the values for this type.
   * @return A class.
   */
  public Class<?> getValueType()
  {
    return String.class;
  }

  /**
   * Get the minimum value for objects of this type.
   * @return the minimum value for objects of this type.
   */
  public final Integer getMin()
  {
    return _min;
  }

  /**
   * Get the maximum value for objects of this type.
   * @return the maximum value for objects of this type.
   */
  public final Integer getMax()
  {
    return _max;
  }

  /**
   * Indicates if multiple lines are allowed.
   * @return <code>true</code> if they are, <code>false</code> otherwise.
   */
  public final Boolean allowMultipleLines()
  {
    return _multipleLines;
  }

  /**
   * Get the regular expression for this string type.
   * @return A regular expression or <code>null</code> if none.
   */
  public final String getRegexp()
  {
    return _regexp;
  }

  /**
   * Set the value of a parameter.
   * @param parameterName Parameter's name.
   * @param parameterValue Parameter's value.
   * @return <code>true</code> if parameter was accepted, <code>false</code> otherwise.
   */
  @Override
  protected boolean setParameterValue(final String parameterName, final Object parameterValue)
  {
    boolean ret=true;
    if (PARAM_MIN_LENGTH.equals(parameterName))
    {
      _min=NumericTools.buildInteger(parameterValue);
    }
    else if (PARAM_MAX_LENGTH.equals(parameterName))
    {
      _max=NumericTools.buildInteger(parameterValue);
    }
    else if (PARAM_MULTIPLE_LINES.equals(parameterName))
    {
      _multipleLines=(Boolean)parameterValue;
    }
    else if (PARAM_REGEXP.equals(parameterName))
    {
      _regexp=(String)parameterValue;
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
  public String buildInstanceFromValue(Object value)
  {
    if (value!=null)
    {
      return value.toString();
    }
    return null;
  }
}
