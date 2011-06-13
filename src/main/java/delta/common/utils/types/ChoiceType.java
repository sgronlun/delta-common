package delta.common.utils.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.types.utils.TypesLoggers;

/**
 * Type for "choice" values.
 * @author DAM
 */
public class ChoiceType extends Type
{
  private static final Logger _logger=TypesLoggers.getTypesLogger();

  // Type name
  /**
   * Name of type.
   */
  public static final String TYPE_NAME="CHOICE";

  // Parameter names
  /**
   * Used to indicate that multiple choices are possible.
   */
  private static final String PARAM_MULTIPLE="MULTIPLE";
  /**
   * Used to indicate the type of values.
   */
  private static final String PARAM_TYPE="TYPE";
  /**
   * Used to introduce a possible value.
   */
  private static final String PARAM_OPTION="OPTION";

  private Boolean _multiple;
  private Type _valuesType;
  private List<Object> _allowedValues;
  private List<Object> _roAllowedValues;

  private static Type _defaultType;

  /**
   * Default constructor.
   */
  public ChoiceType()
  {
    this(false,getDefaultType());
  }

  /**
   * Full constructor.
   * @param multiple Indicates whether multiple values are allowed.
   * @param valuesType Type of allowed values.
   */
  public ChoiceType(boolean multiple, Type valuesType)
  {
    super();
    _allowedValues=new ArrayList<Object>();
    _roAllowedValues=Collections.unmodifiableList(_allowedValues);
    TypeClassesRegistry typeClassesRegistry=TypeClassesRegistry.getInstance();
    TypeClass choiceClass=typeClassesRegistry.getTypeClassByName(ChoiceType.TYPE_NAME);
    setNameAndType("",choiceClass);
    uncheckedSetParamValue(PARAM_MULTIPLE,Boolean.valueOf(multiple));
    uncheckedSetParamValue(PARAM_TYPE,valuesType);
  }

  /**
   * Get the class of the values for this type.
   * @return A class.
   */
  public Class<?> getValueType()
  {
    // TODO A ChoiceValue class...
    return null;
  }

  /**
   * Indicates if multiple values are allowed.
   * @return <code>true</code> if they are, <code>false</code> otherwise.
   */
  public Boolean allowMultipleValues()
  {
    return _multiple;
  }

  /**
   * Get the type of allowed values.
   * @return the type of allowed values.
   */
  public Type getValuesType()
  {
    return _valuesType;
  }

  /**
   * Get a list of allowed values.
   * @return a list of allowed values.
   */
  public List<Object> getAllowedValues()
  {
    return _roAllowedValues;
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
    if (PARAM_MULTIPLE.equals(parameterName))
    {
      _multiple=(Boolean)parameterValue;
    }
    else if (PARAM_TYPE.equals(parameterName))
    {
      _valuesType=(Type)parameterValue;
    }
    else if (PARAM_OPTION.equals(parameterName))
    {
      if (_valuesType!=null)
      {
        Object choiceValue=_valuesType.buildInstanceFromValue(parameterValue);
        if (choiceValue!=null)
        {
          _allowedValues.add(choiceValue);
        }
        else
        {
          _logger.error("Cannot parse choice option ["+parameterValue+"] for choice ["+getName()+"]. Type is ["+_valuesType+"]");
        }
      }
      else
      {
        _logger.error("Cannot parse choice option ["+parameterValue+"] for choice ["+getName()+"] : no type");
      }
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
    // todo
    return null;
  }

  /**
   * Get the default type for choices.
   * @return the default type for choices.
   */
  private static Type getDefaultType()
  {
    if (_defaultType==null)
    {
      _defaultType=new StringType();
    }
    return _defaultType;
  }
}
