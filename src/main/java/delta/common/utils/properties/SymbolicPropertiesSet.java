package delta.common.utils.properties;

import java.util.BitSet;

/**
 * Represents a set of symbolic properties.
 * @author DAM
 */
public class SymbolicPropertiesSet
{
  private SymbolicPropertiesRegistry _semantics;
  private BitSet _selectedProperties;

  /**
   * Package protected constructor.
   * @param semantics Parent registry.
   */
  SymbolicPropertiesSet(SymbolicPropertiesRegistry semantics)
  {
    _semantics=semantics;
    _selectedProperties=new BitSet();
  }

  /**
   * Get a properties set that has the properties of this instance, and the
   * additional specified property.
   * @param propertyName Property name.
   * @return A properties set.
   */
  public SymbolicPropertiesSet addProperty(String propertyName)
  {
    SymbolicProperty p=_semantics.getProperty(propertyName);
    return addProperty(p);
  }

  /**
   * Get a properties set that has the properties of this instance, and the
   * additional specified property.
   * @param property Property.
   * @return A properties set.
   */
  public SymbolicPropertiesSet addProperty(SymbolicProperty property)
  {
    if (property==null)
    {
      // todo warn
      return this;
    }
    if (!_semantics.hasProperty(property))
    {
      // todo warn
      return this;
    }
    if (hasProperty(property))
    {
      return this;
    }
    SymbolicPropertiesSet ret=new SymbolicPropertiesSet(_semantics);
    ret._selectedProperties.or(_selectedProperties);
    ret._selectedProperties.set(property.getIntValue());
    return ret;
  }

  /**
   * Get a properties set that contains the specified named properties.
   * @param propertyNames Name of properties to include.
   * @return A properties set instance.
   */
  public SymbolicPropertiesSet addProperties(String[] propertyNames)
  {
    SymbolicPropertiesSet ret=new SymbolicPropertiesSet(_semantics);
    ret._selectedProperties.or(_selectedProperties);
    int value=0;
    SymbolicProperty p;
    for(int i=0;i<propertyNames.length;i++)
    {
      p=_semantics.getProperty(propertyNames[i]);
      if (p!=null)
      {
        value=p.getIntValue();
        ret._selectedProperties.set(value);
      }
    }
    return ret;
  }

  /**
   * Indicates if this object has the specified field property.
   * @param property Field property to test.
   * @return <code>true</code> if is has, <code>false</code> otherwise.
   */
  public boolean hasProperty(SymbolicProperty property)
  {
    boolean hasIt=_selectedProperties.get(property.getIntValue());
    return hasIt;
  }

  /**
   * Indicates if this object has all the specified properties.
   * @param properties Properties to test.
   * @return <code>true</code> if is has, <code>false</code> otherwise.
   */
  public boolean hasProperties(SymbolicPropertiesSet properties)
  {
    BitSet tmp=new BitSet();
    tmp.or(properties._selectedProperties);
    tmp.andNot(_selectedProperties);
    int count=tmp.cardinality();
    return (count==0);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    int nbBits=_selectedProperties.size();
    SymbolicProperty property;
    for(int i=0;i<nbBits;i++)
    {
      if (_selectedProperties.get(i))
      {
        property=_semantics.getPropertyByValue(i);
        if (property!=null)
        {
          if (sb.length()>0)
          {
            sb.append('/');
          }
          sb.append(property.getName());
        }
      }
    }
    String ret=sb.toString();
    return ret;
  }
}
