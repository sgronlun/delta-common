package delta.common.utils.files.index;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Cleans duplicate files.
 * @author DAM
 */
public class DuplicatesCleaner
{
  private Trash _trash;
  private List<DirectoryIndex> _indexes;
  private FileChooser _chooser;

  /**
   * Constructor.
   */
  public DuplicatesCleaner()
  {
    _indexes=new ArrayList<DirectoryIndex>();
  }

  /**
   * Do the job.
   * @param files List of index files.
   * @param doDelete Perform files deletion or not.
   * @param chooser Object responsible for choosing the file to keep in each
   * series of identical files.
   */
  public void doIt(File[] files, boolean doDelete, FileChooser chooser)
  {
    _trash=new Trash(doDelete,0);
    _chooser=chooser;
    for(int i=0;i<files.length;i++)
    {
      internalDoIt(files[i]);
      for(int j=i+1;j<files.length;j++)
      {
        internalDoIt(files[i],files[j]);
      }
    }
    _trash.status();
  }

  private void internalDoIt(File i1, File i2)
  {
    if (((i1.canRead()) && (i1.canWrite())) &&
        ((i2.canRead()) && (i2.canWrite())))
    {
      DirectoryIndex index1=DirectoryIndexFileIO.loadFromFile(i1);
      DirectoryIndex index2=DirectoryIndexFileIO.loadFromFile(i2);
      DuplicatesFinder finder=new DuplicatesFinder();
      List<List<FileData>> duplicates=finder.findDuplicates(index1,index2);
      _indexes.add(index1);
      _indexes.add(index2);
      handleDuplicates(duplicates);
      DirectoryIndexFileIO.writeToFile(i1,index1);
      DirectoryIndexFileIO.writeToFile(i2,index2);
    }
    else
    {
      System.err.println("Cannot read/write "+i1+" or "+i2);
    }
  }

  private void internalDoIt(File i)
  {
    if ((i.canRead()) && (i.canWrite()))
    {
      DirectoryIndex index=DirectoryIndexFileIO.loadFromFile(i);
      DuplicatesFinder finder=new DuplicatesFinder();
      List<List<FileData>> duplicates=finder.findDuplicates(index);
      _indexes.add(index);
      handleDuplicates(duplicates);
      DirectoryIndexFileIO.writeToFile(i,index);
    }
    else
    {
      System.err.println("Cannot read/write "+i);
    }
  }

  private void handleDuplicates(List<List<FileData>> duplicates)
  {
    for(List<FileData> list : duplicates)
    {
      handleDuplicatesSet(list);
    }
  }

  private void handleDuplicatesSet(List<FileData> duplicates)
  {
    System.out.println("****");
    FileData chosenFile=_chooser.chooseFile(duplicates);
    if (chosenFile!=null)
    {
      System.out.println("Chose ["+chosenFile.getName()+"]");
      if (new File(chosenFile.getName()).exists())
      {
        int nb=duplicates.size();
        for(int i=0;i<nb;i++)
        {
          FileData f=duplicates.get(i);
          if (f!=chosenFile)
          {
            Boolean ok=_trash.removeFile(new File(f.getName()));
            if (Boolean.TRUE.equals(ok))
            {
              for(int j=0;j<_indexes.size();j++)
              {
                _indexes.get(j).removeFile(f.getName());
              }
            }
          }
        }
      }
      else
      {
        System.out.println("WARNING: does not exist !");
      }
    }
  }
}
