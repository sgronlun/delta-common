package delta.common.utils.properties;

import delta.common.utils.collections.ListOrderedMap;

/**
 * Registry for symbolic properties.
 * @author DAM
 */
public class SymbolicPropertiesRegistry
{
  private int _counter;
  private ListOrderedMap<SymbolicProperty> _properties;

  /**
   * Constructor.
   */
  public SymbolicPropertiesRegistry()
  {
    _counter=0;
    _properties=new ListOrderedMap<SymbolicProperty>();
  }

  /**
   * Create a new property.
   * @param propertyName
   * @return Newly created property if any. <code>null</code> otherwise.
   */
  public SymbolicProperty createProperty(String propertyName)
  {
    SymbolicProperty ret=null;
    if ((propertyName!=null) && (propertyName.length()>=0))
    {
      SymbolicProperty old=_properties.get(propertyName);
      if (old==null)
      {
        ret=new SymbolicProperty(propertyName,_counter);
        _properties.put(propertyName,ret);
        _counter++;
      }
    }
    return ret;
  }

  /**
   * Get a properties set from a set of property names.
   * @param names Property names.
   * @return A properties set.
   */
  public SymbolicPropertiesSet getPropertiesSet(String[] names)
  {
    SymbolicPropertiesSet set=new SymbolicPropertiesSet(this);
    set=set.addProperties(names);
    return set;
  }

  /**
   * Get a properties set from a property name.
   * @param name Property name.
   * @return A properties set that contains the desired property.
   */
  public SymbolicPropertiesSet getPropertiesSet(String name)
  {
    SymbolicPropertiesSet set=new SymbolicPropertiesSet(this);
    set=set.addProperty(name);
    return set;
  }

  /**
   * Get an empty properties set.
   * @return an empty properties set.
   */
  public SymbolicPropertiesSet getEmptySet()
  {
    SymbolicPropertiesSet set=new SymbolicPropertiesSet(this);
    return set;
  }

  public boolean hasProperty(SymbolicProperty property)
  {
    return _properties.containsValue(property);
  }

  /**
   * Get a property by name.
   * @param name Property name.
   * @return A property or <code>null</code> if not found.
   */
  public SymbolicProperty getProperty(String name)
  {
    return _properties.get(name);
  }

  /**
   * Get a property by its index value.
   * @param index Property name.
   * @return A property or <code>null</code> if not found.
   */
  public SymbolicProperty getPropertyByValue(int index)
  {
    if ((index>=0) && (index<_counter))
    {
      return _properties.get(index);
    }
    return null;
  }
}
