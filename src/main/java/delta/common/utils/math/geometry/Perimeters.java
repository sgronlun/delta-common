package delta.common.utils.math.geometry;

/**
 * Perimeters computations.
 * @author DAM
 */
public class Perimeters
{
  /**
   * Computes the perimeter of an ellipse.
   * @param semiMajorAxisLength Length semi-major axis.
   * @param semiMinorAxisLength Length semi-minor axis.
   * @return The computed value.
   */
  public static double getEllipsePerimeter(double semiMajorAxisLength, double semiMinorAxisLength)
  {
    if(semiMinorAxisLength==0)
    {
      return semiMajorAxisLength;
    }
    if(semiMajorAxisLength==0)
    {
      return semiMinorAxisLength;
    }
    return Math.PI*(semiMajorAxisLength+semiMinorAxisLength);
  }

  /**
   * Computes the length of a circle arc.
   * @param radius Circle's radius.
   * @param arcAngle Angle of arc (using radians)
   * @return The computed value.
   */
  public static double getCircleArcLength(double radius, double arcAngle)
  {
    return Math.abs(radius*arcAngle);
  }
}
