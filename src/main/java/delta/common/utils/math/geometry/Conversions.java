package delta.common.utils.math.geometry;

/**
 * Conversion methods.
 * @author DAM
 */
public class Conversions
{
  /**
   * Conversion factor between degrees and radians. 
   */
  public static final double DEGREES_TO_RADIANS_FACTOR=Math.PI/180.0;
  /**
   * Conversion factor between radians and degrees.
   */
  public static final double RADIANS_TO_DEGREES_FACTOR=180.0/Math.PI;

  /**
   * Convert a degrees value into a radians value.
   * @param degrees Value to convert.
   * @return Converted value (radians).
   */
  public static double degreesToRadians(double degrees)
  {
    return degrees*DEGREES_TO_RADIANS_FACTOR;
  }

  /**
   * Convert a radians value into a degrees value.
   * @param radians Value to convert.
   * @return Converted value (degrees).
   */
  public static double radiansToDegrees(double radians)
  {
    return radians*RADIANS_TO_DEGREES_FACTOR;
  }
}
