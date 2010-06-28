package delta.common.utils.files.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * Name-based file filter.
 * It is used to select files or directories with a specified name.
 * @author DAM
 */
public class NamePredicate implements FileFilter
{
  private String _name;

  /**
   * Constructor.
   * @param name Name used to select files and directories.
   */
  public NamePredicate(String name)
  {
    _name=name;
  }

  /**
   * Get the name used when filtering.
   * @return the name used when filtering.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Implementation of the <tt>FileFilter</tt> interface.
   * This filter selects files or directories whose name is the one specified
   * in the constructor.
   * @param file File to test.
   * @return <code>true</code> if the specified file passed this filter, <code>false</code> otherwise.
   */
  public boolean accept(File file)
  {
    return _name.equals(file.getName());
  }
}
