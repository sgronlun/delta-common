package delta.common.utils.types;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.common.utils.types.utils.TypesLoggers;

/**
 * Registry for basic types.
 * @author DAM
 */
public class TypesNamespace
{
  private static final Logger _logger=TypesLoggers.getTypesLogger();

  private String _name;
  private Map<String,Type> _typesMap;

  /**
   * Constructor.
   * @param name Name of this registry.
   */
  public TypesNamespace(String name)
  {
    _name=name;
    _typesMap=new HashMap<String,Type>();
  }

  /**
   * Get the name of this types registry.
   * @return the name of this types registry.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Register a new type.
   * @param type Type to register.
   * @return <code>true</code> upon successfull registration, <code>false</code> otherwise.
   */
  public boolean registerType(Type type)
  {
    String key=type.getName();
    Type old=_typesMap.get(key);
    if (old!=null)
    {
      _logger.warn("Type ["+key+"] already defined.");
      return false;
    }
    _typesMap.put(key,type);
    return true;
  }

  /**
   * Get a type by name.
   * @param name Type name.
   * @return A type or <code>null</code> if not found.
   */
  public Type getTypeByName(String name)
  {
    Type type=_typesMap.get(name);
    return type;
  }
}
