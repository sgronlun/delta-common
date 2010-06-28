package delta.common.utils.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import delta.common.utils.types.utils.TypesLoggers;

/**
 * Represents the class of a series of types.
 * @author DAM
 */
public final class TypeClass
{
  private static final Logger _logger=TypesLoggers.getTypesLogger();

  private String _name;
  private Class<? extends Type> _typeClass;
  private HashMap<String,TypeClassParameter> _parametersMap;
  private List<TypeClassParameter> _parameters;

  /**
   * Constructor.
   * @param name Name of class.
   * @param typeClass Class of <tt>Type</tt> implementation.
   * @param params Type class parameters.
   */
  public TypeClass(final String name, final Class<? extends Type> typeClass, final List<TypeClassParameter> params)
  {
    _name=name;
    _typeClass=typeClass;
    _parametersMap=new HashMap<String,TypeClassParameter>();
    _parameters=new ArrayList<TypeClassParameter>();
    String key;
    TypeClassParameter param;
    TypeClassParameter old;
    for(Iterator<TypeClassParameter> it=params.iterator();it.hasNext();)
    {
      param=it.next();
      key=param.getName();
      old=_parametersMap.get(key);
      if (old!=null)
      {
        _logger.warn("Type class parameter ["+key+"] already defined for type class ["+name+"].");
      }
      else
      {
        _parametersMap.put(key,param);
        _parameters.add(param);
      }
    }
  }

  /**
   * Get the name of this type class.
   * @return the name of this type class.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get a type class parameter by name.
   * @param paramName Name of parameter to get.
   * @return Found parameter or <code>null</code>.
   */
  public TypeClassParameter getParameterByName(final String paramName)
  {
    TypeClassParameter ret=_parametersMap.get(paramName);
    return ret;
  }

  /**
   * Get the sorted list of all parameter names.
   * @return the sorted list of all parameter names.
   */
  public List<String> getParameterNames()
  {
    Set<String> values=_parametersMap.keySet();
    List<String> ret=new ArrayList<String>(values);
    Collections.sort(ret);
    return ret;
  }

  /**
   * Get the list of all parameters in their declaration order.
   * @return A list of all parameters in their declaration order.
   */
  public List<TypeClassParameter> getParameters()
  {
    return _parameters;
  }

  /**
   * Build a <tt>Type</tt> instance for this type class.
   * @param name Name of type.
   * @return A <tt>Type</tt> instance or <code>null</code>.
   */
  public Type buildType(final String name)
  {
    Type type=null;
    if (_typeClass!=null)
    {
      try
      {
        type=_typeClass.newInstance();
        type.setNameAndType(name,this);
      }
      catch (Exception e)
      {
        _logger.error("",e);
      }
    }
    return type;
  }
}
