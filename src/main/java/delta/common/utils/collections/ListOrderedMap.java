package delta.common.utils.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A collection that merges map and list functionalities. 
 * @author DAM
 * @param <T> Type of objects managed by this collection.
 */
public class ListOrderedMap<T> implements Iterable<T>
{
  private List<T> _list;
  private Map<String,T> _map;

  /**
   * Constructor.
   */
  public ListOrderedMap()
  {
    _list=new ArrayList<T>();
    _map=new HashMap<String,T>();
  }

  /**
   * Indicates if this collection contains the specified element.
   * @param element Element to test.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean contains(T element)
  {
    return _list.contains(element);
  }

  /**
   * Indicates if this collection contains the specified element.
   * @param value Value to test.
   * @return <code>true</code> if it does, <code>false</code> otherwise.
   */
  public boolean containsValue(T value)
  {
    return _list.contains(value);
  }

  /**
   * Put an item in this ordered list.
   * @param name Name of item.
   * @param element Item to add.
   * @return An old item whose name is <code>name</code> or <code>null</code>.
   */
  public T put(String name, T element)
  {
    T old=_map.put(name,element);
    if (old!=null)
    {
      _list.remove(old);
    }
    _list.add(element);
    return old;
  }

  /**
   * Get an item using it name.
   * @param name Name of the item to get.
   * @return An item or <code>null</code> if not found.
   */
  public T get(String name)
  {
    return _map.get(name);
  }

  /**
   * Get the item at the specified index.
   * @param index Index of targeted object.
   * @return An object.
   * @throws IndexOutOfBoundsException if the index is out of range
   * (<tt>index &lt; 0 || index &gt;= size()</tt>).
   */
  public T get(int index)
  {
    return _list.get(index);
  }

  /**
   * Get a list that contains all the objects of this collection.
   * @return a list that contains all the objects of this collection.
   */
  public List<T> values()
  {
    List<T> ret=new ArrayList<T>(_list);
    return ret;
  }

  /**
   * Get the number of items in this collection.
   * @return the number of items in this collection.
   */
  public int size()
  {
    return _list.size();
  }

  /**
   * Get an iterator on this container.
   * @return an iteractor.
   */
  public Iterator<T> iterator()
  {
    return _list.iterator();
  }

  /**
   * Remove all.
   */
  public void clear()
  {
    _list.clear();
    _map.clear();
  }

  /**
   * Remove an entry using its key.
   * @param key Key to remove.
   * @return the removed value or <code>null</code>.
   */
  public T remove(String key)
  {
    T ret=_map.remove(key);
    if (ret!=null)
    {
      _list.remove(ret);
    }
    return ret;
  }
}
