package delta.common.utils.io;

import java.io.ByteArrayInputStream;

/**
 * Byte array input stream with access to the current position.
 * @author DAM
 */
public class RandomAccessByteArrayInputStream extends ByteArrayInputStream
{
  /**
   * Constructor.
   * @param buffer Buffer to use.
   */
  public RandomAccessByteArrayInputStream(byte[] buffer)
  {
    super(buffer);
  }

  /**
   * Set the current position.
   * @param position Position to set.
   */
  public void setPosition(int position)
  {
    pos=position;
  }

  /**
   * Get the current position.
   * @return A position (starting at 0).
   */
  public int getPosition()
  {
    return pos;
  }
}
