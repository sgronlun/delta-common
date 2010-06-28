package delta.common.utils.files.index;

public abstract class FSComponentData
{
  private DirectoryData _parent;
  private String _name;

  public FSComponentData(DirectoryData parent, String name)
  {
    _parent=parent;
    _name=name;
  }

  public DirectoryData getParent()
  {
    return _parent;
  }

  public String getName() { return _name; }

  public abstract long getSize();

  public abstract long getNumberOfFiles();
}
