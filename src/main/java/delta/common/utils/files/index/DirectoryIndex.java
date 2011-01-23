package delta.common.utils.files.index;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class DirectoryIndex
{
  private File _rootDir;
  private long _totalSize;
  private long _nbFiles;
  private TreeMap<String,FileData> _files;
  private HashMap<Long,ArrayList<FileData>> _crcIndex;
  private List<FileData> _filesList;

  public DirectoryIndex(File rootDir)
  {
    _rootDir=rootDir;
    _files=new TreeMap<String,FileData>();
    _filesList=new ArrayList<FileData>();
    _crcIndex=new HashMap<Long,ArrayList<FileData>>();
  }

  public void addFile(String name, long size, long crc)
  {
    name=name.replace("\n","\\n");
    FileData newData=new FileData(null,name,size,crc);
    FileData r=_files.put(name,newData);
    if (r==null)
    {
      Long key=Long.valueOf(newData.getCRC());
      ArrayList<FileData> list=_crcIndex.get(key);
      if(list==null)
      {
        list=new ArrayList<FileData>();
        _crcIndex.put(key, list);
      }
      list.add(newData);
      _filesList.add(newData);
      _totalSize+=size;
      _nbFiles++;
    }
  }

  public boolean removeFile(String name)
  {
    boolean ret=false;
    FileData data=_files.get(name);
    if (data!=null)
    {
      _files.remove(name);
      Long crc=Long.valueOf(data.getCRC());
      ArrayList<FileData> list=_crcIndex.get(crc);
      if (list!=null)
      {
        list.remove(data);
      }
      _nbFiles--;
      _filesList.remove(data);
      ret=true;
    }
    return ret;
  }

  public void clear()
  {
    _files.clear();
    _nbFiles=0;
  }

  public File getRootDir()
  {
    return _rootDir;
  }

  public long getTotalSize()
  {
    return _totalSize;
  }

  public long getNbFiles()
  {
    return _nbFiles;
  }

  public Set<Long> getCRCs()
  {
    return _crcIndex.keySet();
  }

  public List<FileData> getEntriesWithCRC(long crc)
  {
    Long key=Long.valueOf(crc);
    List<FileData> ret=_crcIndex.get(key);
    return ret;
  }

  public FileData getEntry(int index)
  {
    return _filesList.get(index);
  }

  public Iterator<FileData> getEntries()
  {
    return _files.values().iterator();
  }
}
