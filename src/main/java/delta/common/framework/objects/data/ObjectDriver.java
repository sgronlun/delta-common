package delta.common.framework.objects.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

public class ObjectDriver<E extends DataObject<E>>
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  /**
   * Default constructor.
   */
  public ObjectDriver()
  {
    // Nothing to do !
  }

  /**
   * Load an object from the data source.
   * @param primaryKey Identifying key for the targeted object.
   * @return The loaded object or <code>null</code> if not found.
   */
  public E getByPrimaryKey(long primaryKey)
  {
    return null;
  }

  public E getPartialByPrimaryKey(long primaryKey)
  {
    return getByPrimaryKey(primaryKey);
  }

  public List<E> getAll()
  {
  	return new ArrayList<E>();
  }

  public List<E> getByPrimaryKeyList(List<Long> primaryKeys)
  {
    List<E> list=new ArrayList<E>();
    Long l;
    E element;
    for(Iterator<Long> it=primaryKeys.iterator();it.hasNext();)
    {
      l=it.next();
      element=getByPrimaryKey(l.longValue());
      if (element!=null)
      {
        list.add(element);
      }
      else
      {
        _logger.error("Object not found : "+l.longValue());
      }
    }
    return list;
  }

  public List<Long> getRelatedObjectIDs(String relationName, long primaryKey)
  {
    return new ArrayList<Long>();
  }

  public List<Long> getObjectIDsSet(String setID, Object[] parameters)
  {
    return new ArrayList<Long>();
  }

  public void create(E objectToCreate)
  {
    // Nothing to do !
  }

  public void update(E objectToUpdate)
  {
    // Nothing to do !
  }

  public void delete(long primaryKey)
  {
    // Nothing to do !
  }
}
