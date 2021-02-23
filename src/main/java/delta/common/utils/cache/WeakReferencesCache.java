package delta.common.utils.cache;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Manages a cache that uses weak references.
 * @author DAM
 * @param <KEY_TYPE> Type of keys.
 * @param <TYPE> Type of objects to manage in this cache.
 */
public class WeakReferencesCache<KEY_TYPE,TYPE> extends Cache<KEY_TYPE,TYPE>
{
  private static final Logger LOGGER=Logger.getLogger(WeakReferencesCache.class);

  private HashMap<KEY_TYPE, WeakReference<TYPE>> _objects;

  /**
   * Constructor.
   * @param size Estimated size for this cache.
   */
  public WeakReferencesCache(int size)
  {
    _objects=new HashMap<KEY_TYPE, WeakReference<TYPE>>(size);
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
      WeakReference<TYPE> ref=new WeakReference<TYPE>(object);
      _objects.put(key, ref);
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
    Collection<WeakReference<TYPE>> items=_objects.values();
    WeakReference<TYPE> item;
    TYPE object;
    for(Iterator<WeakReference<TYPE>> it=items.iterator();it.hasNext();)
    {
      item=it.next();
      object=item.get();
      if (object!=null)
      {
        ret.add(object);
      }
    }
    clean();
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
    TYPE ret=null;
    WeakReference<TYPE> ref=_objects.get(key);
    if (ref!=null)
    {
      ret=ref.get();
    }
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
    clean();
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

  private void clean()
  {
    Map.Entry<KEY_TYPE,WeakReference<TYPE>> entry;
    for(Iterator<Map.Entry<KEY_TYPE,WeakReference<TYPE>>> it=_objects.entrySet().iterator();it.hasNext();)
    {
      entry=it.next();
      if (entry.getValue().get()==null)
      {
        it.remove();
      }
    }
  }
}
