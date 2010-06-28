package delta.common.utils.misc;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;

import delta.common.utils.io.StreamTools;

public class CRC
{
  public static long computeCRC(String fileName)
  {
    return computeCRC(new File(fileName));
  }

  public static long computeCRC(File fileName)
  {
    long result=0;

    FileInputStream fis=null;
    BufferedInputStream bis=null;
    try
    {
      byte[] buffer=new byte[8192];
      fis=new FileInputStream(fileName);
      bis=new BufferedInputStream(fis);
      CRC32 crc=new CRC32();
      int readResult=0;
      while (readResult>=0)
      {
        readResult=bis.read(buffer);
        if (readResult>0)
        {
          crc.update(buffer,0,readResult);
        }
      }
      result=crc.getValue();
    }
    catch (IOException ioe)
    {
      result=-1;
    }
    finally
    {
      StreamTools.close(bis);
      StreamTools.close(fis);
    }
    return result;
  }
}
