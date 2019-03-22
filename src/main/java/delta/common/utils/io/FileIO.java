package delta.common.utils.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * File I/O methods.
 * @author DAM
 */
public class FileIO
{
  private static final Logger LOGGER=Logger.getLogger(FileIO.class);

  /**
   * Read the contents of a file into a byte buffer.
   * @param f File to read.
   * @return A byte buffer or <code>null</code> if the file cannot be read.
   */
  public static byte[] readFile(File f)
  {
    byte[] ret=null;
    FileInputStream fis=null;
    try
    {
      fis=new FileInputStream(f);
      int l=(int)f.length();
      ret=new byte[l];
      int readLength=fis.read(ret);
      if (readLength!=l)
      {
        LOGGER.error("readLength="+readLength+" != length="+l);
        ret=null;
      }
    }
    catch (IOException ioe)
    {
      LOGGER.error("",ioe);
      ret=null;
    }
    finally
    {
      StreamTools.close(fis);
    }
    return ret;
  }

  /**
   * Write a byte buffer to a file.
   * @param f File to write to.
   * @param buffer Buffer to use.
   * @return <code>true</code> if it was successfull, <code>false</code> otherwise.
   */
  public static boolean writeFile(File f, byte[] buffer)
  {
    boolean ok=false;
    FileOutputStream fos=null;
    try
    {
      fos=new FileOutputStream(f);
      fos.write(buffer);
      ok=true;
    }
    catch (IOException ioe)
    {
      LOGGER.error("",ioe);
    }
    finally
    {
      StreamTools.close(fos);
    }
    return ok;
  }
}
