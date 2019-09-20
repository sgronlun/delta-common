package delta.common.utils.files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.log4j.Logger;

import delta.common.utils.io.StreamTools;

/**
 * File copy tools.
 * @author DAM
 */
public abstract class FileCopy
{
  private static final Logger LOGGER=Logger.getLogger(FileCopy.class);

  private static final int BUFFER_SIZE=8192*4;

  /**
   * Copy a file to a directory.
   * @param from File to copy.
   * @param toDir Destination directory.
   * @return <code>true</code> if copy was successfull, <code>false</code>
   * otherwise.
   */
  public static boolean copyToDir(File from, File toDir)
  {
    return copy(from,new File(toDir,from.getName()));
  }

  /**
   * Copy a file from an URL.
   * @param from URL to get bytes from.
   * @param to Destination file (file to write).
   * @return <code>true</code> if copy was successfull, <code>false</code>
   * otherwise.
   */
  public static boolean copyFromURL(URL from, File to)
  {
    try
    {
      InputStream is=from.openStream();
      return copy(is,to);
    }
    catch (IOException ioe)
    {
      LOGGER.error("",ioe);
      return false;
    }
  }

  /**
   * Copy a file from an URL.
   * @param fromURL URL to get bytes from.
   * @param to Destination file (file to write).
   * @return <code>true</code> if copy was successfull, <code>false</code>
   * otherwise.
   */
  public static boolean copyFromURL(String fromURL, File to)
  {
    try
    {
      URL from=new URL(fromURL);
      InputStream is=from.openStream();
      return copy(is,to);
    }
    catch (IOException ioe)
    {
      LOGGER.error("",ioe);
      return false;
    }
  }

  /**
   * Copy a file.
   * @param from Source file (file to read).
   * @param to Destination file (file to write).
   * @return <code>true</code> if copy was successfull, <code>false</code>
   * otherwise.
   */
  public static boolean copy(File from, File to)
  {
    try
    {
      FileInputStream fis=new FileInputStream(from);
      return copy(fis,to);
    }
    catch (FileNotFoundException fnfe)
    {
      LOGGER.error("",fnfe);
      return false;
    }
  }

  /**
   * Implementation of the copy.
   * @param is Stream to read bytes from.
   * @param to Destination file (file to write).
   * @return <code>true</code> if copy was successfull, <code>false</code>
   * otherwise.
   */
  public static boolean copy(InputStream is, File to)
  {
    boolean result=false;
    BufferedInputStream bis=null;
    FileOutputStream fos=null;
    BufferedOutputStream bos=null;
    try
    {
      byte[] buffer=new byte[BUFFER_SIZE];
      bis=new BufferedInputStream(is);
      fos=new FileOutputStream(to);
      bos=new BufferedOutputStream(fos);
      int readResult=0;
      while (readResult>=0)
      {
        readResult=bis.read(buffer);
        if (readResult>0)
        {
          bos.write(buffer,0,readResult);
        }
      }
      result=true;
    }
    catch (IOException e)
    {
      LOGGER.error("File copy error. To: "+to,e);
      result=false;
    }
    finally
    {
      StreamTools.close(bis);
      StreamTools.close(is);
      StreamTools.close(bos);
      StreamTools.close(fos);
    }
    return result;
  }
}
