package delta.common.utils.misc;

/**
 * This class provides methods to convert buffers into
 * readable strings and vice versa.
 */
public class HexString2BufferCodec
{
  /**
   * Encodes the given buffer into a string that contains
   * the concatenation of 2 digits hexadecimal values of each
   * byte.
   * For instance 1 2 15 255-> 01020FFF
   * @param buffer Buffer to convert.
   * @return The converted string.
   */
  public static String bufferToString(byte[] buffer)
  {
    StringBuffer sb=new StringBuffer();
    for(int i=0;i<buffer.length;i++)
    {
      sb.append(Integer.toHexString(buffer[i]).toUpperCase());
    }
    return sb.toString();
  }

  /**
   * Decodes the given string into a byte buffer.
   * See bufferToString.
   * @param asciifiedBuffer String to decode.
   * @return Decoded buffer.
   */
  public static byte[] stringToBuffer(String asciifiedBuffer)
  {
    int length=asciifiedBuffer.length()/2;
    byte[] ret=new byte[length];
    for(int i=0;i<length;i++)
    {
      String substring=asciifiedBuffer.substring(i*2, (i+1)*2);
      int value=Integer.parseInt(substring, 16);
      ret[i]=(byte)value;
    }
    return ret;
  }
}
