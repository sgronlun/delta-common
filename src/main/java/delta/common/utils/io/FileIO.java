package delta.common.utils.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

public class FileIO
{
  private static final Logger _logger=UtilsLoggers.getIOLogger();

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
        _logger.error("readLength="+readLength+" != length="+l);
        ret=null;
      }
    }
    catch (IOException ioe)
    {
      _logger.error("",ioe);
      ret=null;
    }
    finally
    {
      StreamTools.close(fis);
    }
    return ret;
  }

  public static boolean writeFile(File current, byte[] buffer)
  {
    boolean ok=false;
    FileOutputStream fos=null;
    try
    {
      fos=new FileOutputStream(current);
      fos.write(buffer);
      ok=true;
    }
    catch (IOException ioe)
    {
      _logger.error("",ioe);
    }
    finally
    {
      StreamTools.close(fos);
    }
    return ok;
  }
}
