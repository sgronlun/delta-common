package delta.common.framework.objects.data;


/**
 * Base class for data objects.
 * @author DAM
 * @param <E> Type of the data object.
 */
public abstract class DataObject<E extends Identifiable<Long>> implements Identifiable<Long>
{
  private Long _primaryKey;
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
  public DataObject(Long primaryKey, ObjectSource<E> source)
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
  public Long getPrimaryKey()
  {
    return _primaryKey;
  }

  /**
   * Set the primary key of this object.
   * @param primaryKey Primary key to set.
   */
  public void setPrimaryKey(Long primaryKey)
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
