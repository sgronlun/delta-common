package delta.common.utils.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * LRU cache.
 * @param <KEY_TYPE> Type of keys.
 * @param <TYPE> Type of objects to manage in this cache.
 * @author DAM
 */
public class LRUCache<KEY_TYPE,TYPE> extends Cache<KEY_TYPE,TYPE>
{
  private LinkedList<KEY_TYPE> _queue;
  private Map<KEY_TYPE,TYPE> _map;
  private int _size;

  /**
   * Constructor.
   * @param size Size of cache.
   */
  public LRUCache(int size)
  {
    _size=size;
    _queue=new LinkedList<KEY_TYPE>();
    _map=new HashMap<KEY_TYPE,TYPE>();
  }

  public TYPE getObject(KEY_TYPE key)
  {
    if (_map.containsKey(key))
    {
      TYPE value=_map.get(key);
      _queue.remove(key);
      _queue.addFirst(key);
      return value;
    }
    return null;
  }

  public void registerObject(KEY_TYPE key, TYPE value)
  {
    if (_map.containsKey(key))
    {
      _queue.remove(key);
    }
    else
    {
      if (_queue.size()==_size)
      {
        KEY_TYPE temp=_queue.removeLast();
        _map.remove(temp);
      }
    }
    _queue.addFirst(key);
    _map.put(key,value);
  }

  /**
   * Get a list of all keys (most recently used to least recently used).
   * @return A list of keys.
   */
  public List<KEY_TYPE> getKeys()
  {
    ArrayList<KEY_TYPE> ret=new ArrayList<KEY_TYPE>(_queue);
    return ret;
  }

  @Override
  public Collection<TYPE> getAllObjects()
  {
    return new ArrayList<TYPE>(_map.values());
  }

  @Override
  public boolean hasObject(KEY_TYPE key)
  {
    return _map.containsKey(key);
  }

  @Override
  public int size()
  {
    return _map.size();
  }

  @Override
  public void clear()
  {
    _queue.clear();
    _map.clear();
  }
}
