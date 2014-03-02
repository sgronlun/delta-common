package delta.common.utils.files.index;

import java.util.Collection;
import java.util.HashMap;

/**
 * Stores data about a single directory:
 * <ul>
 * - parent directory data,
 * - directory name,
 * - children items (directories or files).
 * </ul>
 * @author DAM
 */
public class DirectoryData extends FSComponentData
{
  private HashMap<String,FSComponentData> _children;

  /**
   * Constructor.
   * @param parent Parent directory data (or <code>null</code>).
   * @param name Name of this directory.
   */
  public DirectoryData(DirectoryData parent, String name)
  {
    super(parent,name);
  }

  /**
   * Add a child item.
   * @param child Child item to add.
   */
  public void addChild(FSComponentData child)
  {
    if (_children==null)
    {
      _children=new HashMap<String,FSComponentData>();
    }
    _children.put(child.getName(),child);
  }

  /**
   * Get a child directory using its name.
   * @param name Name to search.
   * @return A directory data item or <code>null</code> if not found.
   */
  public DirectoryData getChildDirectory(String name)
  {
    if (_children!=null)
    {
      FSComponentData child=_children.get(name);
      if (child instanceof DirectoryData)
      {
        return (DirectoryData)child;
      }
    }
    return null;
  }

  /**
   * Get a child file using its name.
   * @param name Name to search.
   * @return A file data item or <code>null</code> if not found.
   */
  public FileData getChildFile(String name)
  {
    if (_children!=null)
    {
      FSComponentData child=_children.get(name);
      if (child instanceof FileData)
      {
        return (FileData)child;
      }
    }
    return null;
  }

  @Override
  public long getSize()
  {
    long size=0;
    if (_children!=null)
    {
      Collection<FSComponentData> dataCollection=_children.values();
      long subSize;
      for(FSComponentData data : dataCollection)
      {
        subSize=data.getSize();
        size+=subSize;
      }
    }
    return size;
  }

  /**
   * Get the total number of files in this directory (recursively). 
   * @return A number of files.
   */
  public long getNumberOfFiles()
  {
    long ret=0;
    if (_children!=null)
    {
      Collection<FSComponentData> dataCollection=_children.values();
      for(FSComponentData data : dataCollection)
      {
        if (data instanceof DirectoryData)
        {
          long subNbFiles=((DirectoryData)data).getNumberOfFiles();
          ret+=subNbFiles;
        }
        else
        {
          ret++;
        }
      }
    }
    return ret;
  }
}
