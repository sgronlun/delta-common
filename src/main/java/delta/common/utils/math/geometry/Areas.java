package delta.common.utils.math.geometry;

/**
 * Area computation methods for various shapes.
 * @author DAM
 */
public class Areas
{
  /**
   * Computes the area of an ellipse.
   * @param semiMajorAxisLength Length semi-major axis.
   * @param semiMinorAxisLength Length semi-minor axis.
   * @return The computed value.
   */
  public static double getEllipseArea(double semiMajorAxisLength, double semiMinorAxisLength)
  {
    return Math.PI*semiMajorAxisLength*semiMinorAxisLength;
  }

  /**
   * Computes the area of a circle arc.
   * @param radius Circle's radius.
   * @param arcAngle Angle of arc (using radians)
   * @return The computed value.
   */
  public static double getCircleArcArea(double radius, double arcAngle)
  {
    return Math.abs(radius*radius*arcAngle/2);
  }
}
