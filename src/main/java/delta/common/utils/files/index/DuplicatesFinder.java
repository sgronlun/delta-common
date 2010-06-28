package delta.common.utils.files.index;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DuplicatesFinder
{
  public List<List<FileData>> findDuplicates(DirectoryIndex index)
  {
    List<List<FileData>> duplicates=new ArrayList<List<FileData>>();
    Set<Long> crcs=index.getCRCs();
    //System.out.println("Number of CRCs : "+crcs.size());
    List<FileData> list;
    long crc;
    for(Iterator<Long> it=crcs.iterator();it.hasNext();)
    {
      crc=it.next().longValue();
      list=index.getEntriesWithCRC(crc);
      if(list.size()>=2)
      {
        handleSameCRCFilesList(duplicates,crc,list);
      }
    }
    return duplicates;
  }

  public List<List<FileData>> findDuplicates(DirectoryIndex i1, DirectoryIndex i2)
  {
    List<List<FileData>> duplicates=new ArrayList<List<FileData>>();
    Set<Long> crcs=i1.getCRCs();
    //System.out.println("Number of CRCs : "+crcs.size());
    List<FileData> list;
    List<FileData> list1;
    List<FileData> list2;
    long crc;
    for(Iterator<Long> it=crcs.iterator();it.hasNext();)
    {
      crc=it.next().longValue();
      list2=i2.getEntriesWithCRC(crc);
      if (list2!=null)
      {
        list1=i1.getEntriesWithCRC(crc);
        list=new ArrayList<FileData>();
        list.addAll(list1);
        list.addAll(list2);
        if(list.size()>=2)
        {
          handleSameCRCFilesList(duplicates,crc,list);
        }
      }
    }
    return duplicates;
  }

  private void handleSameCRCFilesList(List<List<FileData>> duplicates, long crc, List<FileData> list)
  {
    if (crc==0) return;
    if (list.size()==0) return;

    //System.out.println("CRC="+crc);
    int n=list.size();
    long realSize=list.get(0).getSize();
    // Check that all files in the list have the same size
    boolean doIt=true;
    FileData f;
    for(int i=0;i<n;i++)
    {
      f=list.get(i);
      if (f.getSize()!=realSize)
      {
        doIt=false;
      }
    }
    if (doIt)
    {
      List<FileData> newList=new ArrayList<FileData>();
      newList.addAll(list);
      duplicates.add(newList);
    }
    else
    {
      System.err.println("***** size of files differ !! *****");
      for(int i=0;i<n;i++)
      {
        f=list.get(i);
        System.err.println("Kept ["+list.get(i).getName()+"]");
      }
    }
  }
}
