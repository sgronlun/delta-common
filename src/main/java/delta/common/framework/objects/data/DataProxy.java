package delta.common.framework.objects.data;

public class DataProxy<E extends DataObject<E>>
{
  private long _primaryKey;
  private E _target;
  private ObjectSource<E> _source;

  /**
   * Full constructor.
   * @param primaryKey Object identifier.
   * @param source Targeted objects source.
   */
  public DataProxy(long primaryKey, ObjectSource<E> source)
  {
    _primaryKey=primaryKey;
    _source=source;
    if (_source==null)
    {
      throw new AssertionError("source is null when building a "+getClass().getName()+" proxy !");
    }
  }

  public String getClassName()
  {
  	return _target.getClassName();
  }

  public E getDataObject()
  {
    if((_target==null)&&(_primaryKey!=0))
    {
      _target=_source.load(_primaryKey);
    }
    return _target;
  }

  public ObjectSource<E> getSource()
  {
  	return _source;
  }

  public long getPrimaryKey()
  {
    return _primaryKey;
  }
}
