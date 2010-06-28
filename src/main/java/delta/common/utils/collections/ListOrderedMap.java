package delta.common.utils.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author DAM
 */
public class ListOrderedMap<T> implements Iterable<T>
{
  private List<T> _list;
  private Map<String,T> _map;

  public ListOrderedMap()
  {
    _list=new ArrayList<T>();
    _map=new HashMap<String,T>();
  }

  public boolean contains(T element)
  {
    return _list.contains(element);
  }

  public boolean containsValue(T value)
  {
    return _list.contains(value);
  }

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

  public T get(String name)
  {
    return _map.get(name);
  }

  public T get(int index)
  {
    return _list.get(index);
  }

  public List<T> values()
  {
    List<T> ret=new ArrayList<T>(_list);
    return ret;
  }

  public int size()
  {
    return _list.size();
  }

  public Iterator<T> iterator()
  {
    return _list.iterator();
  }
}
