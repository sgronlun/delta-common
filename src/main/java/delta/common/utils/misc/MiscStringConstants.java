package delta.common.utils.misc;

/**
 * Misc. string constants (end of line, eMail separator, ...).
 * @author DAM
 */
public class MiscStringConstants
{
  /**
   * Native OS End of Line.
   */
  public static final String NATIVE_EOL=System.getProperty("line.separator");

  /**
   * Un*x End of Line.
   */
  public static final String UNIX_EOL="\n";

  /**
   * Un*x End of Line as a char.
   */
  public static final char UNIX_EOL_AS_CHAR='\n';

  /**
   * Windows End of Line.
   */
  public static final String WINDOWS_EOL="\r\n";

  /**
   * MacOS End of Line.
   */
  public static final String MACOS_EOL="\r";

  /**
   * ACP127 End of Line.
   */
  public static final String ACP127_EOL="\r\r\n";

  /**
   * Seprator used in eMails (between username and hostname).
   */
  public static final String EMAIL_SEPARATOR="@";
}
