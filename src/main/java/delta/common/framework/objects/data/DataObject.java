package delta.common.framework.objects.data;

/**
 * Base class for data objects.
 * @author DAM
 * @param <E> Type of the data object.
 */
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

  /**
   * Constructor.
   * @param primaryKey Object identifier.
   * @param source Parent objects source.
   */
  public DataObject(long primaryKey, ObjectSource<E> source)
  {
    _primaryKey=primaryKey;
    _source=source;
  }

  /**
   * Get the objects source for this object.
   * @return the objects source for this object.
   */
  public ObjectSource<E> getSource()
  {
    return _source;
  }

  /**
   * Get the primary key of this object.
   * @return the primary key of this object.
   */
  public long getPrimaryKey()
  {
    return _primaryKey;
  }

  /**
   * Set the primary key of this object.
   * @param primaryKey Primary key to set.
   */
  public void setPrimaryKey(long primaryKey)
  {
    _primaryKey=primaryKey;
  }

  /**
   * Get a readable label for this object.
   * @return a readable string.
   */
  public String getLabel()
  {
    return "";
  }

  /**
   * Get the class name for this object.
   * @return the class name for this object.
   */
  public String getClassName()
  {
    return null;
  }
}
