package delta.common.utils;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * Tools related to numeric values management.
 * @author DAM
 */
public class NumericTools
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  /**
   * String that indicates an infinite positive value.
   */
  public static final String INFINITY="INFINITY";

  /**
   * Parse a float.
   * If the given <code>value</code> is equal to <code>INFINITY</code>, returns <code>Float.MAX_VALUE</code>.
   * <p>
   * Otherwise, the <code>value</code> is parsed using <code>Float.parseFloat(String)</code>.
   * <p>
   * If it cannot be parsed to a numerical value, returns the <code>defaultValue</code>.
   * @param value string to parse
   * @param defaultValue value to use if parsing fails.
   * @return A float value.
   */
  public static float parseFloat(String value, float defaultValue)
  {
    if ((value==null) || (value.length()==0))
    {
      return defaultValue;
    }
    value=value.trim();
    if (NumericTools.INFINITY.equals(value))
    {
      return Float.MAX_VALUE;
    }
    try
    {
      return Float.parseFloat(value);
    }
    catch(NumberFormatException nfe)
    {
      _logger.error("Cannot parse float '"+value+"'",nfe);
      return defaultValue;
    }
  }

  /**
   * Parse a float.
   * If the given <code>value</code> is equal to <code>INFINITY</code>, returns <code>Float.MAX_VALUE</code>.
   * <br>
   * Otherwise, the <code>value</code> is parsed using <code>Float.valueOf(String)</code>.
   * <br>
   * If it cannot be parsed to a numerical value, returns the <code>null</code>.
   * @param value string to parse.
   * @return A float value or <code>null</code>.
   */
  public static Float parseFloat(String value)
  {
    return parseFloat(value,true);
  }

  /**
   * Parse a float.
   * If the given <code>value</code> is equal to <code>INFINITY</code>, returns <code>Float.MAX_VALUE</code>.
   * <br>
   * Otherwise, the <code>value</code> is parsed using <code>Float.valueOf(String)</code>.
   * <br>
   * If it cannot be parsed to a numerical value, returns the <code>null</code>.
   * @param value string to parse.
   * @param doWarn Indicates if a warning is to be raised when the value is not valid.
   * @return A float value or <code>null</code>.
   */
  public static Float parseFloat(String value, boolean doWarn)
  {
    if ((value==null) || (value.length()==0))
    {
      return null;
    }
    value=value.trim();
    if (NumericTools.INFINITY.equals(value))
    {
      return Float.valueOf(Float.MAX_VALUE);
    }
    try
    {
      return Float.valueOf(value);
    }
    catch(NumberFormatException nfe)
    {
      if (doWarn)
      {
        _logger.error("Cannot parse float '"+value+"'",nfe);
      }
      return null;
    }
  }

  /**
   * Parse a double.
   * If the given <code>value</code> is equal to <code>INFINITY</code>, returns <code>Double.MAX_VALUE</code>.
   * <p>
   * Otherwise, the <code>value</code> is parsed using <code>Double.parseDouble(String)</code>.
   * <p>
   * If it cannot be parsed to a numerical value, returns the <code>defaultValue</code>.
   * @param value string to parse
   * @param defaultValue value to use if parsing fails.
   * @return A double value.
   */
  public static double parseDouble(String value, double defaultValue)
  {
    if ((value==null) || (value.length()==0))
    {
      return defaultValue;
    }
    value=value.trim();
    if (NumericTools.INFINITY.equals(value))
    {
      return Double.MAX_VALUE;
    }
    try
    {
      return Double.parseDouble(value);
    }
    catch(NumberFormatException nfe)
    {
      _logger.error("Cannot parse double '"+value+"'",nfe);
      return defaultValue;
    }
  }

  /**
   * Parse a long.
   * If the given <code>value</code> is equal to <code>INFINITY</code>, returns <code>Long.MAX_VALUE</code>.
   * <p>
   * Otherwise, the <code>value</code> is parsed using <code>Long.parseLong(String)</code>.
   * <p>
   * If it cannot be parsed to a numerical value, returns the <code>defaultValue</code>.
   * @param value string to parse.
   * @param defaultValue value to use if parsing fails.
   * @return A long value.
   */
  public static long parseLong(String value, long defaultValue)
  {
    Long ret=parseLong(value);
    if (ret==null)
    {
      return defaultValue;
    }
    return ret.longValue();
  }

  /**
   * Parse a long.
   * If the given <code>value</code> is equal to <code>INFINITY</code>, returns <code>Long.MAX_VALUE</code>.
   * <p>
   * Otherwise, the <code>value</code> is parsed using <code>Long.parseLong(String)</code>.
   * <p>
   * If it cannot be parsed to a numerical value, returns the <code>null</code>.
   * @param value string to parse.
   * @return A <code>Long</code> value or <code>null</code>.
   */
  public static Long parseLong(String value)
  {
    return parseLong(value,true);
  }

  /**
   * Parse a long.
   * If the given <code>value</code> is equal to <code>INFINITY</code>, returns <code>Long.MAX_VALUE</code>.
   * <p>
   * Otherwise, the <code>value</code> is parsed using <code>Long.parseLong(String)</code>.
   * <p>
   * If it cannot be parsed to a numerical value, returns the <code>null</code>.
   * @param value string to parse.
   * @param doWarn Indicates if a warning is to be raised when the value is not valid.
   * @return A <code>Long</code> value or <code>null</code>.
   */
  public static Long parseLong(String value, boolean doWarn)
  {
    if ((value==null) || (value.length()==0))
    {
      return null;
    }
    value=value.trim();
    if (NumericTools.INFINITY.equals(value))
    {
      return Long.valueOf(Long.MAX_VALUE);
    }
    try
    {
      return Long.valueOf(value);
    }
    catch(NumberFormatException nfe)
    {
      if (doWarn)
      {
        _logger.error("Cannot parse long '"+value+"'",nfe);
      }
      return null;
    }
  }

  /**
   * Build an integer from an object value (String or Number).
   * @param value Value to use.
   * @return An integer value or <code>null</code> if conversion fails.
   */
  public static Integer buildInteger(Object value)
  {
    if (value==null) return null;
    if (value instanceof Number)
    {
      return Integer.valueOf(((Number)value).intValue());
    }
    else if (value instanceof String)
    {
      return parseInteger((String)value);
    }
    return null;
  }

  /**
   * Parse an integer.
   * If the given <code>value</code> is equal to <code>INFINITY</code>, returns <code>Integer.MAX_VALUE</code>.
   * <p>
   * Otherwise, the <code>value</code> is parsed using <code>Integer.parseInt(String)</code>.
   * <p>
   * If it cannot be parsed to a numerical value, returns the <code>null</code>.
   * @param value string to parse.
   * @return An <tt>Integer</tt> or <code>null</code>.
   */
  public static Integer parseInteger(String value)
  {
    return parseInteger(value,true);
  }

  /**
   * Parse an integer.
   * If the given <code>value</code> is equal to <code>INFINITY</code>, returns <code>Integer.MAX_VALUE</code>.
   * <p>
   * Otherwise, the <code>value</code> is parsed using <code>Integer.parseInt(String)</code>.
   * <p>
   * If it cannot be parsed to a numerical value, returns the <code>null</code>.
   * @param value string to parse.
   * @param doWarn Indicates if a warning is to be raised when the value is not valid.
   * @return An <tt>Integer</tt> or <code>null</code>.
   */
  public static Integer parseInteger(String value, boolean doWarn)
  {
    if ((value==null) || (value.length()==0))
    {
      return null;
    }
    value=value.trim();
    if (INFINITY.equals(value))
    {
      return Integer.valueOf(Integer.MAX_VALUE);
    }
    try
    {
      Integer ret=Integer.valueOf(value);
      return ret;
    }
    catch(NumberFormatException nfe)
    {
      if (doWarn)
      {
        _logger.error("Cannot parse int '"+value+"'",nfe);
      }
      return null;
    }
  }

  /**
   * Parse an integer.
   * If the given <code>value</code> is equal to <code>INFINITY</code>, returns <code>Integer.MAX_VALUE</code>.
   * <p>
   * Otherwise, the <code>value</code> is parsed using <code>Integer.parseInt(String)</code>.
   * <p>
   * If it cannot be parsed to a numerical value, returns the <code>defaultValue</code>.
   * @param value string to parse.
   * @param defaultValue value to use if parsing fails.
   * @return An int value.
   */
  public static int parseInt(String value, int defaultValue)
  {
    Integer ret=parseInteger(value);
    if (ret==null)
    {
      return defaultValue;
    }
    return ret.intValue();
  }

  /**
   * Parse a short.
   * If the given <code>value</code> is equal to <code>INFINITY</code>, returns <code>Short.MAX_VALUE</code>.
   * <p>
   * Otherwise, the <code>value</code> is parsed using <code>Short.parseShort(String)</code>.
   * <p>
   * If it cannot be parsed to a numerical value, returns the <code>defaultValue</code>.
   * @param value string to parse
   * @param defaultValue value to use if parsing fails.
   * @return A short value.
   */
  public static short parseShort(String value, short defaultValue)
  {
    if ((value==null) || (value.length()==0))
    {
      return defaultValue;
    }
    value=value.trim();
    if (NumericTools.INFINITY.equals(value))
    {
      return Short.MAX_VALUE;
    }
    try
    {
      return Short.parseShort(value);
    }
    catch(NumberFormatException nfe)
    {
      _logger.error("Cannot parse short '"+value+"'",nfe);
      return defaultValue;
    }
  }
}
