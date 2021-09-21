package delta.common.utils.collections;

import java.util.Arrays;
import java.util.Set;

/**
 * A thrifty (memory-wise) int set.
 * <p>
 * It supports only a limited set of operations:
 * <ul>
 * <li>retainAll
 * <li>contains
 * </ul>
 * @author DAM
 */
public class ThriftyIntSet
{
  private int[] _values;

  /**
   * Constructor.
   */
  public ThriftyIntSet()
  {
    _values=new int[0];
  }

  /**
   * Constructor from an array of ints.
   * @param values Values to store.
   */
  public ThriftyIntSet(int[] values)
  {
    _values=new int[values.length];
    Arrays.sort(_values);
  }

  /**
   * Constructor from another <code>ThriftyIntSet</code>.
   * @param other Set to copy.
   */
  public ThriftyIntSet(ThriftyIntSet other)
  {
    _values=new int[other._values.length];
    System.arraycopy(other._values,0,_values,0,_values.length);
  }

  /**
   * Constructor from a <code>Set</code>.
   * @param values Set to copy.
   */
  public ThriftyIntSet(Set<Integer> values)
  {
    _values=new int[values.size()];
    int index=0;
    for(Integer value : values)
    {
      _values[index]=value.intValue();
      index++;
    }
    Arrays.sort(_values);
  }

  /**
   * Indicates if it contains the given value.
   * @param value Value to test.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean contains(int value)
  {
    return Arrays.binarySearch(_values,value)!=-1;
  }

  /**
   * Retain in this set only the values that are on both this set and the other set.
   * @param other Other set.
   */
  public void retainAll(ThriftyIntSet other)
  {
    int nbEntries=_values.length;
    int writeIndex=0;
    for(int i=0;i<nbEntries;i++)
    {
      if (other.contains(_values[i]))
      {
        _values[writeIndex]=_values[i];
        writeIndex++;
      }
    }
    int[] newValues=new int[writeIndex];
    System.arraycopy(_values,0,newValues,0,writeIndex);
    _values=newValues;
  }

  /**
   * Get the managed values.
   * @return an array of sorted int values. 
   */
  public int[] getValues()
  {
    return _values;
  }
}
