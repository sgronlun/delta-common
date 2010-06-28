package delta.common.utils.files.index;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A virtual trash.
 * @author DAM
 */
public class Trash
{
  private List<File> _preTrash;
  private int _preTrashSize; 
  // Size of biggest file removed
  private long _maxSize;
  // Really do it ?
  private boolean _doDelete;
  // Total size of deleted files
  private long _deleteFileSize;
  // Number of deleted files
  private int _nbDeletedFiles;
  // Number of failed deletions
  private int _nbFailedFiles;

  /**
   * Constructor.
   * @param doDelete Do it for real.
   */
  public Trash(boolean doDelete, int preTrashSize)
  {
    _maxSize=0;
    _doDelete=doDelete;
    _deleteFileSize=0;
    _nbDeletedFiles=0;
    _nbFailedFiles=0;
    _preTrash=new ArrayList<File>();
    _preTrashSize=preTrashSize;
  }

  public void flush()
  {
    for(int i=0;i<_preTrash.size();i++)
    {
      doDelete(_preTrash.get(i));
    }
    _preTrash.clear();
  }

  /**
   * Delete file.
   * @param file Targeted file.
   * @return <code>Boolean.TRUE</code> if it was done, <code>Boolean.FALSE</code> if
   * it failed, or <code>null</code> if we're in 'no delete' mode.
   */
  public Boolean removeFile(File file)
  {
    Boolean ret=null;
    _preTrash.add(file);
    int nbToRemove=_preTrash.size()-_preTrashSize;
    if (nbToRemove>0)
    {
      for(int i=0;i<nbToRemove;i++)
      {
        File toRemove=_preTrash.remove(0);
        Boolean ok=doDelete(toRemove);
        if (toRemove==file)
        {
          ret=ok;
        }
      }
    }
    return ret;
  }
  
  private Boolean doDelete(File file)
  {
    Boolean ret=null;
    long size=file.length();
    if (size>_maxSize)
    {
      _maxSize=size;
    }
    if (_doDelete)
    {
      boolean ok=file.delete();
      if (ok)
      {
        _deleteFileSize+=size;
        _nbDeletedFiles++;
        System.out.println("Removed ["+file.getAbsolutePath()+"]");
        File parent=file.getParentFile();
        if (parent.delete())
        {
          System.out.println("Removed directory ["+parent+"]");
        }
        ret=Boolean.TRUE;
      }
      else
      {
        _nbFailedFiles++;
        System.err.println("Failed to remove ["+file.getAbsolutePath()+"]");
        ret=Boolean.FALSE;
      }
    }
    else
    {
      _deleteFileSize+=size;
      _nbDeletedFiles++;
      System.out.println("Should remove ["+file.getAbsolutePath()+"], size="+size);
    }
    return ret;
  }

  /**
   * Print a status.
   */
  public void status()
  {
    System.out.println("Max size : "+_maxSize);
    if (_doDelete)
    {
      System.out.println("Economie : "+_deleteFileSize);
      System.out.println("Nb fichiers effacés : "+_nbDeletedFiles);
      System.out.println("Nb fichiers non effacés : "+_nbFailedFiles);
    }
    else
    {
      System.out.println("Economie potentielle : "+_deleteFileSize);
      System.out.println("Nb fichiers à effacer : "+_nbDeletedFiles);
    }
  }
}
