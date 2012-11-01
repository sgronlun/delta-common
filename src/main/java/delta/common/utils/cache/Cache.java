package delta.common.utils.cache;

import java.util.Collection;

/**
 * Abstract base class for caches.
 * @author DAM
 * @param <KEY_TYPE> Type of keys.
 * @param <TYPE> Type of objects to manage in this cache.
 */
public abstract class Cache<KEY_TYPE,TYPE>
{
  /**
   * Register a new object.
   * @param key Key.
   * @param object object to register.
   */
  public abstract void registerObject(KEY_TYPE key, TYPE object);

  /**
   * Get a collection of all registered objects.
   * @return a collection of all registered objects.
   */
  public abstract Collection<TYPE> getAllObjects();

  /**
   * Get an object for this cache.
   * @param key Key identifying the object to get.
   * @return an object or <code>null</code> if not found.
   */
  public abstract TYPE getObject(KEY_TYPE key);
  
  /**
   * Get the number of items in this cache.
   * @return the number of items in this cache.
   */
  public abstract int size();

  /**
   * Empty this cache.
   */
  public abstract void clear();
}
