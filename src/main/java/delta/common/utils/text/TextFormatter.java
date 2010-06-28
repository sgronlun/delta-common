package delta.common.utils.text;

/**
 * Base interface for text formatters.
 * @author DAM
 */
public abstract class TextFormatter
{
  /**
   * Format the specified object into the given <tt>StringBuilder</tt>.
   * @param o Object to format.
   * @param sb Output string builder.
   */
  public abstract void format(Object o, StringBuilder sb);

  /**
   * Format the specified object.
   * @param o Object to format.
   * @return Formatted string.
   */
  public String format(Object o)
  {
    StringBuilder sb=new StringBuilder();
    format(o,sb);
    return sb.toString();
  }
}
