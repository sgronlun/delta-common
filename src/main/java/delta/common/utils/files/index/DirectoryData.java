package delta.common.utils.files.index;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class DirectoryData extends FSComponentData
{
  private HashMap<String,FSComponentData> _children;

  public DirectoryData(DirectoryData parent, String name)
  {
    super(parent,name);
  }

  public void addChild(FSComponentData child)
  {
    if (_children==null)
    {
      _children=new HashMap<String,FSComponentData>();
    }
    _children.put(child.getName(),child);
  }

  public DirectoryData getChildDirectory(String name)
  {
    FSComponentData child=null;
    if (_children!=null) child=_children.get(name);
    if (child instanceof DirectoryData) return (DirectoryData)child;
    return null;
  }

  public FileData getChildFile(String name)
  {
    FSComponentData child=null;
    if (_children!=null) child=_children.get(name);
    if (child instanceof FileData) return (FileData)child;
    return null;
  }

  @Override
  public long getSize()
  {
    long size=0;
    if (_children!=null)
    {
      Collection<FSComponentData> dataCollection=_children.values();
      FSComponentData data;
      long subSize;
      for(Iterator<FSComponentData> it=dataCollection.iterator();it.hasNext();)
      {
        data=it.next();
        subSize=data.getSize();
        size+=subSize;
      }
    }
    return size;
  }

  @Override
  public long getNumberOfFiles()
  {
    long ret=0;
    if (_children!=null)
    {
      Collection<FSComponentData> dataCollection=_children.values();
      FSComponentData data;
      for(Iterator<FSComponentData> it=dataCollection.iterator();it.hasNext();)
      {
        data=it.next();
        if (data instanceof DirectoryData)
        {
          long subNbFiles=data.getNumberOfFiles();
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
