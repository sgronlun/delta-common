package delta.common.utils.math.geometry;

/**
 * Storage for a 3D vector.
 * @author DAM
 */
public class Vector3D
{
  private float _x;
  private float _y;
  private float _z;

  /**
   * Constructor.
   */
  public Vector3D()
  {
  }

  /**
   * Get X.
   * @return x value.
   */
  public float getX()
  {
    return _x;
  }

  /**
   * Get Y.
   * @return y value.
   */
  public float getY()
  {
    return _y;
  }

  /**
   * Get Z.
   * @return z value.
   */
  public float getZ()
  {
    return _z;
  }

  /**
   * Set position.
   * @param x X value.
   * @param y Y value.
   * @param z Z value.
   */
  public void set(float x, float y, float z)
  {
    _x=x;
    _y=y;
    _z=z;
  }

  @Override
  public String toString()
  {
    return "x="+_x+",y="+_y+",z="+_z;
  }
}
