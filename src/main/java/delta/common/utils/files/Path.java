package delta.common.utils.files;

import java.io.File;

import delta.common.utils.text.StringSplitter;

/**
 * Represents a path.
 * @author DAM
 */
public class Path
{
  private String[] _paths;
  private String _path;
  private char _separator;

  /**
   * Default constructor. Builds a path with no entry.
   */
  public Path()
  {
    this(new String[]{},"",File.separatorChar);
  }

  /**
   * Simple constructor. Builds a one entry path.
   * @param path Entry of this path.
   * @param separator Separator to use.
   */
  public Path(String path, char separator)
  {
    this(StringSplitter.split(path,separator),path,separator);
  }

  private Path(String[] paths, String path, char separator)
  {
    _paths=paths;
    _path=path;
    _separator=separator;
  }

  /**
   * Build a new path using this path and a child entry.
   * @param childEntry Entry to add to this path.
   * @return the newly built path.
   */
  public Path buildChildPath(String childEntry)
  {
    int index=childEntry.indexOf(_separator);
    if (index!=-1)
    {
      String entry=childEntry.substring(0,index);
      Path tmp=unsafeBuildChildPath(entry);
      return tmp.buildChildPath(childEntry.substring(index+1));
    }
    return unsafeBuildChildPath(childEntry);
  }
  
  private Path unsafeBuildChildPath(String childEntry)
  {
    String[] paths=new String[_paths.length+1];
    System.arraycopy(_paths,0,paths,0,_paths.length);
    paths[_paths.length]=childEntry;
    String path;
    if (_path.length()==0)
    {
      path=childEntry;
    }
    else
    {
      path=_path+_separator+childEntry;
    }
    return new Path(paths,path,_separator);
  }

  /**
   * Get this path as a string, using the system's file separator as the
   * separator between path entries.
   * @return A path string.
   */
  public String getPath()
  {
    return _path;
  }

  /**
   * Get the number of entries in this path.
   * @return A positive number.
   */
  public int getLevel()
  {
    return (_paths!=null)?_paths.length:0;
  }

  /**
   * Get the path entry at the specified level
   * (starting at 0, in ascending order from parent to child).
   * @param level Level to get.
   * @return A path entry or <code>null</code> if not found.
   */
  public String getPath(int level)
  {
    if (_paths==null) return null;
    if ((level>=0) && (level<_paths.length))
    {
      return _paths[level]; 
    }
    return null;
  }

  @Override
  public String toString()
  {
    return _path;
  }
}
