package delta.common.utils;

/**
 * Base types formatting tools.
 * @author DAM (byte buffers)
 */
public class BaseTypesFormatter
{
  private static final char[] HEX_CHARS= {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

  /**
   * Default number of bytes for a single line of text.
   */
  public static final int DEFAULT_NB_BYTES_PER_LINE=16;

  /**
   * Format a bytes buffer.
   * @param buffer byte buffer.
   * @return formatted buffer.
   */
  public static String format(byte[] buffer)
  {
    int length=0;
    if (buffer!=null) length=buffer.length;
    String ret=format(buffer,0,length);
    return ret;
  }

  /**
   * Format a bytes buffer.
   * @param buffer byte buffer.
   * @param startOffset start offset in the byte buffer.
   * @param length number of bytes to handle.
   * @return formatted buffer.
   */
  public static String format(byte[] buffer, int startOffset, int length)
  {
    if (buffer==null) return "";
    String ret="";
    if ((buffer!=null)&&(startOffset>=0)&&(length>=0)&&(buffer.length>=startOffset+length))
    {
      StringBuilder sb=new StringBuilder();
      int currentIndex=startOffset;
      int bytesInCurrentLine=0;
      for(int i=0;i<length;i++)
      {
        if (bytesInCurrentLine>0) sb.append(' ');
        bytesInCurrentLine++;
        int value=buffer[currentIndex];
        if (value<0) value+=256;
        int high=value/16;
        sb.append(HEX_CHARS[high]);
        int low=value%16;
        sb.append(HEX_CHARS[low]);
        if (bytesInCurrentLine==DEFAULT_NB_BYTES_PER_LINE)
        {
          sb.append('\n');
          bytesInCurrentLine=0;
        }
        currentIndex++;
      }
      ret=sb.toString();
    }
    return ret;
  }
}
