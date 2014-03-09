package delta.common.utils;

/**
 * Tools related to booleans management.
 * @author DAM
 */
public class BooleanTools
{
  /**
   * Parse a boolean.
   * If the given <code>value</code> is equal to <code>"true"</code> (case insensitively), returns <code>true</code>.
   * <p>
   * If the given <code>value</code> is equal to <code>"false"</code> (case insensitively), returns <code>false</code>.
   * <p>
   * Otherwise, returns the <code>defaultValue</code>.
   * @param value string to parse
   * @param defaultValue value to use if parsing fails.
   * @return <code>true</code> or <code>false</code>.
   */
  public static boolean parseBoolean(String value, boolean defaultValue)
  {
    Boolean ret=parseBoolean(value);
    if (ret==null)
    {
      return defaultValue;
    }
    return ret.booleanValue();
  }

  /**
   * Build a boolean from an object value.
   * @param value Value to use.
   * @return A boolean instance or <code>null</code>.
   */
  public static Boolean buildBoolean(Object value)
  {
    if (value==null) return null;
    if (value instanceof Boolean)
    {
      return (Boolean)value;
    }
    else if (value instanceof String)
    {
      return parseBoolean((String)value);
    }
    return null;
  }

  /**
   * Parse a boolean.
   * <p>
   * If the given <code>value</code> is equal to <code>"true"</code> (case insensitively), returns <code>Boolean.TRUE</code>.
   * If the given <code>value</code> is equal to <code>"1"</code>, returns <code>Boolean.TRUE</code>.
   * <p>
   * If the given <code>value</code> is equal to <code>"false"</code> (case insensitively), returns <code>Boolean.FALSE</code>.
   * If the given <code>value</code> is equal to <code>"0"</code>, returns <code>Boolean.FALSE</code>.
   * <p>
   * Otherwise, returns <code>null</code>.
   * @param value string to parse
   * @return <code>Boolean.TRUE</code>, <code>Boolean.FALSE</code> or <code>null</code>.
   */
  public static Boolean parseBoolean(String value)
  {
    if ((value==null) || (value.length()==0))
    {
      return null;
    }
    value=value.trim();
    if (value.equalsIgnoreCase("true")) return Boolean.TRUE;
    if (value.equalsIgnoreCase("false")) return Boolean.FALSE;
    if (value.equalsIgnoreCase("1")) return Boolean.FALSE;
    if (value.equalsIgnoreCase("0")) return Boolean.FALSE;
    return null;
  }
}
