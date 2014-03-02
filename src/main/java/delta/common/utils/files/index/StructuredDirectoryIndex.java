package delta.common.utils.files.index;

import java.io.File;

/**
 * Structured directory index.
 * @author DAM
 */
public class StructuredDirectoryIndex
{
  private File _rootDir;
  private long _totalSize;
  private long _nbItems;
  private DirectoryData _rootDirData;

  /**
   * Constructor.
   * @param rootDir Root directory of indexed files.
   */
  public StructuredDirectoryIndex(File rootDir)
  {
    _rootDir=rootDir;
    clear();
  }

  /**
   * Add a file to this index.
   * @param path Path of file.
   * @param size Size of file.
   * @param crc CRC of file contents.
   * @return newly create file data.
   */
  public FileData addFile(File path, long size, long crc)
  {
    FileData fd=null;
    File parentFile=path.getParentFile();
    DirectoryData d=getRelativeDirectory(parentFile,true);
    String fileName=path.getName();
    fd=new FileData(d,fileName,size,crc);
    d.addChild(fd);
    _totalSize+=size;
    _nbItems++;
    return fd;
  }

  /**
   * Get the directory data for a given path.
   * @param path Directory path to search.
   * @param createItIfNecessary Indicates if it shall be created if it does not
   * exist in this index.
   * @return A directory data or <code>null</code>.
   */
  public DirectoryData getRelativeDirectory(File path, boolean createItIfNecessary)
  {
    if (path==null)
    {
      return _rootDirData;
    }
    DirectoryData ret=null;
    File parent=path.getParentFile();
    DirectoryData parentData=getRelativeDirectory(parent,createItIfNecessary);
    if (parentData!=null)
    {
      String name=path.getName();
      ret=parentData.getChildDirectory(name);
      if ((ret==null) && (createItIfNecessary))
      {
        ret=new DirectoryData(parentData,name);
        parentData.addChild(ret);
      }
    }
    return ret;
  }

  /**
   * Clear the contents of this index.
   */
  public void clear()
  {
    String name=_rootDir.getAbsolutePath();
    _rootDirData=new DirectoryData(null,name);
  }

  /**
   * Get the managed root directory.
   * @return A directory.
   */
  public File getRootDir()
  {
    return _rootDir;
  }

  /**
   * Get the total size of the indexed files.
   * @return a size in bytes.
   */
  public long getTotalSize()
  {
    return _totalSize;
  }

  /**
   * Get the total number of the indexed files.
   * @return a number of files.
   */
  public long getNbItems()
  {
    return _nbItems;
  }

  /**
   * Get the file data for a given path.
   * @param relativeFile File path to search.
   * @return A file data or <code>null</code>.
   */
  public FileData getRelativeFile(File relativeFile)
  {
    FileData ret=null;
    File parent=relativeFile.getParentFile();
    DirectoryData d=getRelativeDirectory(parent,false);
    if (d!=null)
    {
      ret=d.getChildFile(relativeFile.getName());
    }
    return ret;
  }
}
