package delta.common.utils.files.index;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * Flat directory index.
 * @author DAM
 */
public class DirectoryIndex
{
  private File _rootDir;
  private long _totalSize;
  private long _nbFiles;
  private TreeMap<String,FileData> _files;
  private HashMap<Long,ArrayList<FileData>> _crcIndex;
  private List<FileData> _filesList;

  /**
   * Constructor.
   * @param rootDir Root directory of indexed files.
   */
  public DirectoryIndex(File rootDir)
  {
    _rootDir=rootDir;
    _files=new TreeMap<String,FileData>();
    _filesList=new ArrayList<FileData>();
    _crcIndex=new HashMap<Long,ArrayList<FileData>>();
  }

  /**
   * Add a file to this index.
   * @param name Name of file.
   * @param size Size of file.
   * @param crc CRC of file contents.
   */
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

  /**
   * Remove a file entry.
   * @param name Name of entry to remove.
   * @return <code>true</code> if it was removed, <code>false</code> otherwise.
   */
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

  /**
   * Clear the contents of this index.
   */
  public void clear()
  {
    _files.clear();
    _nbFiles=0;
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
  public long getNbFiles()
  {
    return _nbFiles;
  }

  /**
   * Get a set of all file CRCs.
   * @return a set of CRCs.
   */
  public Set<Long> getCRCs()
  {
    return _crcIndex.keySet();
  }

  /**
   * Get all the entries with the given CRC.
   * @param crc CRC to use.
   * @return A list of file data or <code>null</code> if not found.
   */
  public List<FileData> getEntriesWithCRC(long crc)
  {
    Long key=Long.valueOf(crc);
    List<FileData> ret=_crcIndex.get(key);
    return ret;
  }

  /**
   * Get the file entry at the given index.
   * @param index Index of entry to get.
   * @return A file entry.
   */
  public FileData getEntry(int index)
  {
    return _filesList.get(index);
  }

  /**
   * Get an iterator on file entries.
   * @return an iterator.
   */
  public Iterator<FileData> getEntries()
  {
    return _files.values().iterator();
  }
}
