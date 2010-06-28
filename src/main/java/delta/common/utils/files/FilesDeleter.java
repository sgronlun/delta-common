package delta.common.utils.files;

import java.io.File;
import java.io.FileFilter;

import delta.common.utils.files.iterator.AbstractFileIteratorCallback;
import delta.common.utils.files.iterator.FileIterator;

/**
 * Files deleter.
 * An object capable of deleting files recursively in a file system tree.
 * Deleted files can be filtered.
 * @author DAM
 */
public class FilesDeleter extends AbstractFileIteratorCallback
{
  private File _rootPath;
  private FileFilter _filter;
  private boolean _deleteEmptyDirs;

  /**
   * Implementation of the <code>handleFile</code> method of the
   * <tt>FileIteratorCallback</tt> interface.
   * @param absolute Path of the file to handle (absolute).
   * @param relative Path of the file to handle (relative to _rootPath).
   */
  @Override
  public void handleFile(File absolute, File relative)
  {
    if ((_filter==null) || (_filter.accept(absolute)))
    {
      absolute.delete();
    }
  }

  /**
   * Implementation of the <code>leaveDirectory</code> method of the
   * <tt>FileIteratorCallback</tt> interface.
   * @param absolute Path of the directory to handle (absolute).
   * @param relative Path of the directory to handle (relative to _rootPath).
   */
  @Override
  public void leaveDirectory(File absolute, File relative)
  {
    if (_deleteEmptyDirs)
    {
      absolute.delete();
    }
  }

  /**
   * Constructor.
   * @param rootPath Root of tree used for deletion.
   * @param filter Filter used to selected files to delete. Give <code>null</code> to delete all files.
   * @param deleteEmptyDirs Indicates if empty dirs should be deleted or not.
   */
  public FilesDeleter(File rootPath, FileFilter filter, boolean deleteEmptyDirs)
  {
    _rootPath=rootPath;
    _filter=filter;
    _deleteEmptyDirs=deleteEmptyDirs;
  }

  /**
   * Activates deletion search.
   */
  public void doIt()
  {
    FileIterator fi=new FileIterator(_rootPath, true, this);
    fi.run();
  }
}
