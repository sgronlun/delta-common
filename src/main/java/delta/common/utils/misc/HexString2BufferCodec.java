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
   * @param buffer_p Buffer to convert.
   * @return The converted string.
   */
  public static String bufferToString(byte[] buffer_p)
  {
    StringBuffer sb_l=new StringBuffer();
    for(int i=0;i<buffer_p.length;i++)
    {
      sb_l.append(Integer.toHexString(buffer_p[i]).toUpperCase());
    }
    return sb_l.toString();
  }

  /**
   * Decodes the given string into a byte buffer.
   * See bufferToString.
   * @param asciifiedBuffer_p String to decode.
   * @return Decoded buffer.
   */
  public static byte[] stringToBuffer(String asciifiedBuffer_p)
  {
    int length_l=asciifiedBuffer_p.length()/2;
    byte[] return_l=new byte[length_l];
    for(int i=0;i<length_l;i++)
    {
      String substring_l=asciifiedBuffer_p.substring(i*2, (i+1)*2);
      int value_l=Integer.parseInt(substring_l, 16);
      return_l[i]=(byte)value_l;
    }
    return return_l;
  }
}
