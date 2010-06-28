package delta.common.framework.objects.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

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

  public E get(long id)
  {
    E ret=_cache.get(Long.valueOf(id));
    return ret;
  }

  public void put(E object)
  {
    long id=object.getPrimaryKey();
    _cache.put(Long.valueOf(id),object);
  }

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

  public void remove(long primaryKey)
  {
    Long key=Long.valueOf(primaryKey);
    _cache.remove(key);
  }
}
