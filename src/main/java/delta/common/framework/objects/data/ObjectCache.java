package delta.common.framework.objects.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Cache for data objects.
 * @author DAM
 * @param <E> Type of the data objects to manage.
 */
public class ObjectCache<E extends DataObject<E>>
{
  private HashMap<Long,E> _cache;

  /**
   * Default constructor.
   */
  public ObjectCache()
  {
    _cache=new HashMap<Long,E>();
  }

  /**
   * Find an object in this cache.
   * @param primaryKey Primary key of the object to get.
   * @return An object or <code>null</code> if not found.
   */
  public E get(long primaryKey)
  {
    E ret=_cache.get(Long.valueOf(primaryKey));
    return ret;
  }

  /**
   * Put an object in this cache.
   * @param object Object to put.
   */
  public void put(E object)
  {
    long id=object.getPrimaryKey();
    _cache.put(Long.valueOf(id),object);
  }

  /**
   * Put a series of objects in this cache.
   * @param collection Objects to put.
   */
  public void putAll(Collection<E> collection)
  {
    if ((collection!=null) && (collection.size()>0))
    {
      E current;
      for(Iterator<E> it=collection.iterator();it.hasNext();)
      {
        current=it.next();
        put(current);
      }
    }
  }

  /**
   * Remove an object from this cache.
   * @param primaryKey Primary key of the object to remove.
   */
  public void remove(long primaryKey)
  {
    Long key=Long.valueOf(primaryKey);
    _cache.remove(key);
  }
}
