package delta.common.utils.files.index;

import java.io.File;

public class StructuredDirectoryIndex
{
  private File _rootDir;
  private long _totalSize;
  private long _nbItems;
  private DirectoryData _rootDirData;

  public StructuredDirectoryIndex(File rootDir)
  {
    _rootDir=rootDir;
    clear();
  }

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

/*
  public DirectoryData findOrCreateDirectory(File path)
  {
    String name=path.getAbsolutePath();
    DirectoryData ret=_directories.get(name);
    if (ret==null)
    {
      File parentFile=path.getParentFile();
      DirectoryData parentDirData=null;
      if (parentFile!=null)
      {
        parentDirData=findOrCreateDirectory(parentFile);
      }
      ret=new DirectoryData(parentDirData,name);
      if (parentDirData!=null)
      {
        parentDirData.addChild(ret);
      }
      _directories.put(name,ret);
    }
    return ret;
  }
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

  public void clear()
  {
    String name=_rootDir.getAbsolutePath();
    _rootDirData=new DirectoryData(null,name);
  }

  public File getRootDir()
  {
    return _rootDir;
  }

  public long getTotalSize()
  {
    return _totalSize;
  }

  public long getNbItems()
  {
    return _nbItems;
  }

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
