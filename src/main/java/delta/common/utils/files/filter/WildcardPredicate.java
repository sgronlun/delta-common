package delta.common.utils.files.filter;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Name-based file filter. It is used to select files or directories whose name
 * match a wildcard.
 * @author DAM
 */
public class WildcardPredicate implements FileFilter
{
  private String _wildcard;
  private Pattern _pattern;

  /**
   * Constructor.
   * @param wildcard Wildcard used to select files.
   */
  public WildcardPredicate(String wildcard)
  {
    _wildcard=wildcard;
    _pattern=Pattern.compile(_wildcard);
  }

  /**
   * Get the wildcard used to selected files.
   * @return the wildcard used to selected files.
   */
  public String getWildcard()
  {
    return _wildcard;
  }

  /**
   * Implementation of the <tt>FileFilter</tt> interface. This filter selects
   * files or directories whose name match the specified wildcard.
   * @param file File to test.
   * @return <code>true</code> if the specified file passed this filter,
   * <code>false</code> otherwise.
   */
  public boolean accept(File file)
  {
    Matcher matcher=_pattern.matcher(file.getName());
    return matcher.matches();
  }
}
