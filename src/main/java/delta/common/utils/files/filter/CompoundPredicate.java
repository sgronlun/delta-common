package delta.common.utils.files.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * Compound file filter.
 * This filter is a composition of two file filters (in AND or OR mode).
 * @author DAM
 */
public class CompoundPredicate implements FileFilter
{
  /**
   * Constant for the OR mode.
   */
  public static final int MODE_OR=0;

  /**
   * Constant for the AND mode.
   */
  public static final int MODE_AND=1;

  /**
   * First file filter.
   */
  private FileFilter _f1;

  /**
   * Second file filter.
   */
  private FileFilter _f2;

  /**
   * Composition mode.
   */
  private int _mode;

  /**
   * Constructor.
   * @param f1 A file filter.
   * @param f2 Another file filter.
   * @param mode Composition mode.
   */
  public CompoundPredicate(FileFilter f1, FileFilter f2, int mode)
  {
    if ((mode!=MODE_OR) && (mode!=MODE_AND))
    {
      throw new IllegalArgumentException("Incorrect mode : "+mode);
    }
    _mode=mode;
    _f1=f1;
    _f2=f2;
  }

  /**
   * Implementation of the <tt>FileFilter</tt> interface.
   * In OR mode, a file matches this filter if it matches one of the two managed file filters.
   * In AND mode, a file matches this filter if it matches both managed file filters.
   * @param file File to test.
   * @return <code>true</code> if the specified file passed this filter, <code>false</code> otherwise.
   */
  public boolean accept(File file)
  {
    if (_mode==MODE_OR)
    {
      return (_f1.accept(file) || _f2.accept(file));
    }
    return (_f1.accept(file) && _f2.accept(file));
  }
}
