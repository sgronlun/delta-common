package delta.common.utils;

import java.io.File;

import delta.common.utils.misc.CRC;

public class VerifiedDirectory
{
  private long _nbItems;

  public VerifiedDirectory(String pathName)
  {
    _nbItems=0;
    File root=new File(pathName);
    if(root.isDirectory())
    {
      handleDirectory(root);
    }
    System.out.println("Items : "+_nbItems);
  }

  private void handleDirectory(File directory)
  {
    File[] files=directory.listFiles();
    if(files!=null)
    {
      for(int i=0;i<files.length;i++)
      {
        if(files[i].isDirectory())
        {
          handleDirectory(files[i]);
        }
        else
        {
          if(files[i].isFile())
          {
            long crc=CRC.computeCRC(files[i]);
            _nbItems++;
            System.out.println("File ["+files[i].getPath()+"]="+crc);
          }
        }
      }
    }
  }

  public static void main(String[] args)
  {
    long time1=System.currentTimeMillis();
    new VerifiedDirectory("/home/dm/docs");
    System.out.println(System.currentTimeMillis()-time1);
  }
}
