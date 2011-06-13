package delta.common.utils.types;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.types.utils.TypesLoggers;

/**
 * Base class for all type implementations.
 * @author DAM
 */
public abstract class Type
{
  private static final Logger _logger=TypesLoggers.getTypesLogger();

  private static final String EMPTY="".intern();

  private String _name;
  private TypeClass _typeClass;
  // todo Handle parameters with multiple values
  private HashMap<String,Object> _paramValues;

  /**
   * Default constructor.
   */
  protected Type()
  {
    _name=EMPTY;
    _typeClass=null;
    _paramValues=new HashMap<String,Object>();
  }

  /**
   * Mandatory fields setter (package protected).
   * @param name Type name.
   * @param typeClass Type class.
   */
  void setNameAndType(String name, TypeClass typeClass)
  {
    if (name==null)
    {
      name=EMPTY;
    }
    _name=name.intern();
    _typeClass=typeClass;
  }

  /**
   * Get the name of this type.
   * @return the name of this type.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the class of this type.
   * @return the class of this type.
   */
  public TypeClass getTypeClass()
  {
    return _typeClass;
  }

  /**
   * Famous <code>toString()</code> method.
   * @return Stringified version of this object.
   */
  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_name);
    sb.append(" (class=");
    sb.append(_typeClass.getName());
    sb.append("), ");
    sb.append(_paramValues);
    String ret=sb.toString();
    return ret;
  }

  /**
   * Set the value of a parameter.
   * (this version assumes that the actual class of the parameter value
   * is compatible with the type designed by the parameter name)
   * @param parameterName Parameter's name.
   * @param parameterValue Parameter's value.
   */
  protected final void uncheckedSetParamValue(String parameterName, Object parameterValue)
  {
    TypeClassParameter typeClassParam=_typeClass.getParameterByName(parameterName);
    if (typeClassParam!=null)
    {
      _paramValues.put(parameterName,parameterValue);
      setParameterValue(parameterName,parameterValue);
    }
    else
    {
      _logger.error("Unknown parameter ["+parameterName+"] for type ["+_name+"]");
    }
  }

  /**
   * Set the value of a parameter.
   * @param parameterName Parameter's name.
   * @param parameterValue Parameter's value.
   */
  public final void setParamValue(String parameterName, Object parameterValue)
  {
    boolean ok=setParameterValue(parameterName,parameterValue);
    if (ok)
    {
      _paramValues.put(parameterName,parameterValue);
    }
    else
    {
      _logger.error("Unknown parameter ["+parameterName+"] for type ["+_name+"]");
    }
  }

  public final Object getParamValue(String parameterName)
  {
    Object ret=_paramValues.get(parameterName);
    return ret;
  }

  /**
   * Get the class of the values for this type.
   * @return A class.
   */
  public abstract Class<?> getValueType();

  /**
   * Set the value of a parameter.
   * @param parameterName Parameter's name.
   * @param parameterValue Parameter's value.
   * @return <code>true</code> if parameter was accepted, <code>false</code> otherwise.
   */
  protected boolean setParameterValue(String parameterName, Object parameterValue)
  {
    return false;
  }

  /**
   * Build an instance object from a value.
   * @param value Value to use to build the instance.
   * @return A value for this type or <code>null</code> if value is unusable.
   */
  public Object buildInstanceFromValue(Object value)
  {
    return null;
  }

  /**
   * Parse a value for this type.
   * @param value String to parse.
   * @return A value for this type or <code>null</code> if value is unusable.
   */
  public final Object parseFromString(String value)
  {
    return buildInstanceFromValue(value);
  }

  public void dump(PrintStream ps)
  {
    String typeClass=(_typeClass!=null)?_typeClass.getName():"";
    ps.println("Type ["+_name+"] of class ["+typeClass+"]");
    ps.println("Values : "+_paramValues);
  }

  public void finish()
  {
    TypeClass typeClass=getTypeClass();
    if (typeClass!=null)
    {
      TypesRegistry registry=TypesRegistry.getInstance();
      List<TypeClassParameter> parameters=typeClass.getParameters();
      for(TypeClassParameter classParam : parameters)
      {
        String typeName=classParam.getType();
        String parameterName=classParam.getName();
        Type type=registry.getType(typeName);
        if (type!=null)
        {
          if (classParam.isMandatory())
          {
            Object paramValue=getParamValue(parameterName);
            if (paramValue==null)
            {
              String defaultValue=classParam.getDefaultValue();
              paramValue=type.buildInstanceFromValue(defaultValue);
              if (paramValue!=null)
              {
                setParamValue(parameterName,paramValue);
              }
              else
              {
                _logger.error("Default value ["+defaultValue+"] for parameter ["+parameterName+"] of type class ["+typeClass.getName()+"]");
              }
            }
          }
          else
          {
            // Nothing to do for a non mandatory parameter.
          }
        }
        else
        {
          _logger.warn("Could not find type ["+typeName+"] used in type ["+_name+"] (class ["+typeClass.getName()+"])");
        }
      }
    }
    else
    {
      _logger.error("No type class for type ["+_name+"]");
    }
  }

  // todo A checkValue method
}
