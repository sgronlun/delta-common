package delta.common.utils.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Manages a cache that uses strong references.
 * @author DAM
 * @param <KEY_TYPE> Type of keys.
 * @param <TYPE> Type of objects to manage in this cache.
 */
public class StrongReferencesCache<KEY_TYPE,TYPE> extends Cache<KEY_TYPE,TYPE>
{
  private static final Logger LOGGER=Logger.getLogger(StrongReferencesCache.class);

  private HashMap<KEY_TYPE, TYPE> _objects;

  /**
   * Constructor.
   * @param size Estimated size for this cache.
   */
  public StrongReferencesCache(int size)
  {
    _objects=new HashMap<KEY_TYPE, TYPE>(size);
  }

  /**
   * Register a new object.
   * @param key Key.
   * @param object object to register.
   */
  @Override
  public void registerObject(KEY_TYPE key, TYPE object)
  {
    if (object!=null)
    {
      _objects.put(key, object);
    }
    else
    {
      LOGGER.error("Cannot register a null object.");
    }
  }

  /**
   * Get a collection of all registered objects.
   * @return a collection of all registered objects.
   */
  @Override
  public Collection<TYPE> getAllObjects()
  {
    List<TYPE> ret=new ArrayList<TYPE>();
    Collection<TYPE> items=_objects.values();
    ret.addAll(items);
    return ret;
  }

  /**
   * Get an object for this cache.
   * @param key Key identifying the object to get.
   * @return an object or <code>null</code> if not found.
   */
  @Override
  public TYPE getObject(KEY_TYPE key)
  {
    TYPE ret=_objects.get(key);
    return ret;
  }

  @Override
  public boolean hasObject(KEY_TYPE key)
  {
    return _objects.containsKey(key);
  }

  /**
   * Get the number of items in this cache.
   * @return the number of items in this cache.
   */
  @Override
  public int size()
  {
    return _objects.size();
  }

  /**
   * Empty this cache.
   */
  @Override
  public void clear()
  {
    _objects.clear();
  }
}
