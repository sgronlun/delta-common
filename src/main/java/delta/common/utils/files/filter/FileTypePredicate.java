package delta.common.utils.files.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * Type-based file filter.
 * It is used to select files or directories.
 * @author DAM
 */
public class FileTypePredicate implements FileFilter
{
  /**
   * Constant for the directory mode.
   */
  public static final int DIRECTORY=0;

  /**
   * Constant for the file mode.
   */
  public static final int FILE=1;

  /**
   * Selected mode (files or directories).
   */
  private int _mode;

  /**
   * Constructor.
   * @param mode Indicates this filter should match directories or files.
   */
  public FileTypePredicate(int mode)
  {
    if ((mode!=DIRECTORY) && (mode!=FILE))
    {
      throw new IllegalArgumentException("Incorrect mode : "+mode);
    }
    _mode=mode;
  }

  /**
   * Implementation of the <tt>FileFilter</tt> interface.
   * This filter selects files if mode is FILE or directories if mode is DIRECTORY.
   * @param file File to test.
   * @return <code>true</code> if the specified file passed this filter, <code>false</code> otherwise.
   */
  public boolean accept(File file)
  {
    if (_mode==DIRECTORY)
    {
      return file.isDirectory();
    }
    return file.isFile();
  }
}
