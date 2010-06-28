package delta.common.framework.objects.data;

public abstract class DataObject<E extends DataObject<E>>
{
  private long _primaryKey;
  private ObjectSource<E> _source;

  /**
   * Default constructor.
   */
  public DataObject()
  {
    // Nothing to do !
  }

  public DataObject(long primaryKey, ObjectSource<E> source)
  {
    _primaryKey=primaryKey;
    _source=source;
  }

  public ObjectSource<E> getSource()
  {
    return _source;
  }

  public long getPrimaryKey()
  {
    return _primaryKey;
  }

  public void setPrimaryKey(long primaryKey)
  {
    _primaryKey=primaryKey;
  }

  public String getLabel()
  {
    return "";
  }

  public String getClassName()
  {
    return null;
  }
}
