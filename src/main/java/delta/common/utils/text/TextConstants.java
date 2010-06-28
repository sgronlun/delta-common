package delta.common.utils.text;

/**
 * Text-related constants.
 * @author DAM
 */
public abstract class TextConstants
{
  /**
   * Special chars used to recognize encodings.
   */
  public static final String SPECIAL_CHARS="éÉèÈêÊëËàÀâÂäÄôÔöÖîÎïÏùÙûÛüÛÿŸç\u00B2\u00B3\u03BC\u00B0";
  // DAM 29/11/2007 - encoded chars at the end of stream are square/cube/micro/degree

  /**
   * Get the unicode value for a character.
   * @param code Character code.
   * @return A "unicode in ASCII" string for this character.
   */
  public static String getUnicode(char code)
  {
    String ret=String.format("\\\\u%1$04x",Integer.valueOf(code));
    return ret;
  }
}
