package delta.common.utils.text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.files.TextFileReader;
import delta.common.utils.misc.MiscStringConstants;

/**
 * A collection of tool methods related to text management.
 * @author DAM
 */
public class TextUtils
{
  private static final Logger LOGGER=Logger.getLogger(TextUtils.class);

  /**
   * Reads the contents of a text file as a string.
   * @param path path of file to read.
   * @param encoding Encoding to use.
   * @return a <tt>String</tt> that reflects the contents of the given file or <code>null</code> if an error occurred.
   */
  public static String loadTextFile(File path, String encoding)
  {
    String ret=null;
    List<String> lines=readAsLines(path,encoding);
    if ((lines!=null) && (lines.size()>0))
    {
      StringBuilder sb=new StringBuilder();
      for(String line : lines)
      {
        sb.append(line);
        sb.append(MiscStringConstants.NATIVE_EOL);
      }
      ret=sb.toString();
    }
    return ret;
  }

  /**
   * Reads the contents of a text file as a series of lines.
   * @param path path of file to read.
   * @return a <tt>List</tt> of <tt>String</tt>s that reflects the contents of the given file or <code>null</code> if an error occurred.
   */
  public static List<String> readAsLines(File path)
  {
    return readAsLines(path,null);
  }

  /**
   * Reads the contents of a text file as a series of lines.
   * @param path path of file to read.
   * @param encoding Encoding to use.
   * @return a <tt>List</tt> of <tt>String</tt>s that reflects the contents of the given file
   * or <code>null</code> if an error occurred.
   */
  public static List<String> readAsLines(File path, String encoding)
  {
    TextFileReader reader=new TextFileReader(path,encoding);
    List<String> ret=readAsLines(reader);
    return ret;
  }

  /**
   * Reads the contents of a text source as a series of lines.
   * @param reader Text reader.
   * @return a <tt>List</tt> of <tt>String</tt>s that reflects the contents of the given source
   * or <code>null</code> if an error occurred.
   */
  public static List<String> readAsLines(TextFileReader reader)
  {
    List<String> ret=null;
    try
    {
      if (reader.start())
      {
        ret=new ArrayList<String>();
        String line;
        while(true)
        {
          line=reader.getNextLine();
          if (line!=null)
          {
            ret.add(line);
          }
          else
          {
            break;
          }
        }
        reader.terminate();
      }
    }
    catch(Exception e)
    {
      LOGGER.error("Error while reading text file contents",e);
    }
    return ret;
  }
}
