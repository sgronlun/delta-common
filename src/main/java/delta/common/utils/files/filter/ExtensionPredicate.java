package delta.common.utils.files.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * Extension-based file filter.
 * @author DAM
 */
public class ExtensionPredicate implements FileFilter
{
  private static final String EXTENSION_SEPARATOR=".";
  private String _extension;
  private boolean _caseSensitive;

  /**
   * Simple constructor.
   * Filtering is not case sensitive (i.e "jpg" matches "file.jpg" and "file.JPG").
   * @param extension Extension to filter (with or without starting dot).
   */
  public ExtensionPredicate(String extension)
  {
    _extension=extension;
    if (!_extension.startsWith(EXTENSION_SEPARATOR))
    {
      _extension=EXTENSION_SEPARATOR+_extension;
    }
    _caseSensitive=false;
  }

  /**
   * Full constructor.
   * @param extension Extension to filter (with or without starting dot).
   * @param caseSensitive Indicates if filtering should be case sensitive or not.
   */
  public ExtensionPredicate(String extension, boolean caseSensitive)
  {
    if (!extension.startsWith(".")) extension="."+extension;
    _extension=extension;
    _caseSensitive=caseSensitive;
  }

  /**
   * Implementation of the <tt>FileFilter</tt> interface.
   * This filter selects files with the specified extension (case sensitively or not).
   * @param file File to test.
   * @return <code>true</code> if the specified file passed this filter, <code>false</code> otherwise.
   */
  public boolean accept(File file)
  {
    String name=file.getName();
    if (name.length()<_extension.length()) return false;
    if (_caseSensitive)
    {
      return name.endsWith(_extension);
    }
    String endOfName=name.substring(name.length()-_extension.length());
    return endOfName.equalsIgnoreCase(_extension);
  }
}
