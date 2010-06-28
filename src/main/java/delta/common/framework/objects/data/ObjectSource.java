package delta.common.framework.objects.data;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.configuration.Configuration;
import delta.common.utils.configuration.Configurations;

public class ObjectSource<E extends DataObject<E>>
{
  public static long _nbGetRequests=0;
  private ObjectCache<E> _cache;
  private ObjectDriver<E> _driver;

  public ObjectSource(ObjectDriver<E> driver)
  {
    _driver=driver;
    Configuration cfg=Configurations.getConfiguration();

    boolean useCache=cfg.getBooleanValue("OBJECTS","USE_CACHE",false);
    if (useCache)
    {
      _cache=new ObjectCache<E>();
    }
  }

  public void create(E object)
  {
    _driver.create(object);
    if (_cache!=null)
    {
      _cache.put(object);
    }
  }

  public void update(E object)
  {
    _driver.update(object);
    if (_cache!=null)
    {
      _cache.put(object);
    }
  }

  public void delete(long primaryKey)
  {
    _driver.delete(primaryKey);
    if (_cache!=null)
    {
      _cache.remove(primaryKey);
    }
  }

  public E load(long primaryKey)
  {
    E ret=null;
    if (_cache!=null)
    {
      ret=_cache.get(primaryKey);
    }
    if (ret==null)
    {
      if(_driver!=null)
      {
        ret=_driver.getByPrimaryKey(primaryKey);
        _nbGetRequests++;
        if ((ret!=null) && (_cache!=null))
        {
          _cache.put(ret);
        }
      }
    }
    else
    {
      //System.out.println("Cache hit for : "+ret);
    }
    return ret;
  }

  public E loadPartial(long primaryKey)
  {
    E ret=null;
    if(_driver!=null)
    {
      ret=_driver.getPartialByPrimaryKey(primaryKey);
    }
    return ret;
  }

  public List<E> loadAll()
  {
  	List<E> ret=new ArrayList<E>();
  	if(_driver!=null)
    {
  	  ret=_driver.getAll();
      if (_cache!=null)
      {
        _cache.putAll(ret);
        System.out.println("Cache/loadAll : "+_driver.getClass().getName());
      }
    }
  	return ret;
  }

  public List<E> loadObjectSet(String setID, Object[] parameters)
  {
  	List<E> ret=null;
    if(_driver!=null)
    {
      List<Long> ids=_driver.getObjectIDsSet(setID, parameters);
      ret=loadAll(ids);
    }
    return ret;

  }

  public List<E> loadAll(List<Long> primaryKeys)
  {
  	List<E> ret=new ArrayList<E>();
  	if(_driver!=null)
    {
      int nb=primaryKeys.size();
      E o=null;
      long id;
      for(int i=0;i<nb;i++)
      {
        id=primaryKeys.get(i).longValue();
        o=load(id);
        if(o!=null)
        {
          ret.add(o);
        }
      }
    }
    if (_cache!=null)
    {
      _cache.putAll(ret);
    }
  	return ret;
  }

  public List<E> loadAllUsingPartials(List<Long> primaryKeys)
  {
    List<E> ret=new ArrayList<E>();
    if(_driver!=null)
    {
      int nb=primaryKeys.size();
      E o=null;
      for(int i=0;i<nb;i++)
      {
        o=_driver.getPartialByPrimaryKey(primaryKeys.get(i).longValue());
        if(o!=null)
        {
          ret.add(o);
        }
      }
    }
    return ret;
  }

  public List<E> loadRelation(String relationName, long primaryKey)
  {
  	List<E> ret=null;
    if(_driver!=null)
    {
      List<Long> ids=_driver.getRelatedObjectIDs(relationName, primaryKey);
      ret=loadAll(ids);
    }
    return ret;
  }

  public List<E> loadRelationUsingPartials(String relationName, long primaryKey)
  {
    List<E> ret=null;
    if(_driver!=null)
    {
      List<Long> ids=_driver.getRelatedObjectIDs(relationName, primaryKey);
      ret=loadAllUsingPartials(ids);
    }
    return ret;
  }
}
