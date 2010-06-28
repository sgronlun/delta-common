package delta.common.utils.math.geometry;

public class Conversions
{
  public static final double DEGREES_TO_RADIANS_FACTOR=Math.PI/180.0;
  public static final double RADIANS_TO_DEGREES_FACTOR=180.0/Math.PI;

  public static double degreesToRadians(double degrees)
  {
    return degrees*DEGREES_TO_RADIANS_FACTOR;
  }

  public static double radiansToDegrees(double radians)
  {
    return radians*RADIANS_TO_DEGREES_FACTOR;
  }
}
