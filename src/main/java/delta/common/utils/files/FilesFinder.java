package delta.common.utils.files;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import delta.common.utils.files.iterator.AbstractFileIteratorCallback;
import delta.common.utils.files.iterator.FileIterator;

/**
 * Find files.
 * @author DAM
 */
public class FilesFinder extends AbstractFileIteratorCallback
{
  /**
   * Absolute mode.
   */
  public static final int ABSOLUTE_MODE=0;
  /**
   * Relative mode.
   */
  public static final int RELATIVE_MODE=1;

  private int _mode;
  private List<File> _files;
  private FileFilter _predicate;

  /**
   * Constructor.
   */
  public FilesFinder()
  {
    _files=null;
  }

  /**
   * Find some files.
   * @param mode Result mode: compute absolute (<code>ABSOLUTE_MODE</code>) or relative (<code>RELATIVE_MODE</code>) file paths.
   * @param root Root directory for the search.
   * @param predicate Predicate for the files to select.
   * @param recursive <code>true</code> to search recursively, <code>false</code> otherwise.
   * @return A list of of found files (using absolute of relative file paths).
   */
  public List<File> find(int mode, File root, FileFilter predicate, boolean recursive)
  {
    _mode=mode;
    _files=new ArrayList<File>();
    _predicate=predicate;
    FileIterator it=new FileIterator(root,recursive,this);
    it.run();
    List<File> files=_files;
    _files=null;
    return files;
  }

  @Override
  public void handleDirectory(File absolute, File relative)
  {
    File toUse=absolute;
    if (_mode==RELATIVE_MODE) toUse=relative;
    if (toUse!=null)
      if (_predicate.accept(absolute)) _files.add(toUse);
  }

  @Override
  public void handleFile(File absolute, File relative)
  {
    File toUse=absolute;
    if (_mode==RELATIVE_MODE) toUse=relative;
    if (_predicate.accept(absolute))
      _files.add(toUse);
  }
}
