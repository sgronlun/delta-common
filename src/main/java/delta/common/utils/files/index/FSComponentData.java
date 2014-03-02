package delta.common.utils.files.index;

/**
 * Base class for files and directory data.
 * @author DAM
 */
public abstract class FSComponentData
{
  private DirectoryData _parent;
  private String _name;

  /**
   * Constructor.
   * @param parent Parent directory data (or <code>null</code>).
   * @param name Name of this item.
   */
  public FSComponentData(DirectoryData parent, String name)
  {
    _parent=parent;
    _name=name;
  }

  /**
   * Get the parent directory data.
   * @return the directory data (or <code>null</code> if no parent.
   */
  public DirectoryData getParent()
  {
    return _parent;
  }

  /**
   * Get the name of this item.
   * @return An item name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the size of this item.
   * @return A size in bytes.
   */
  public abstract long getSize();
}
