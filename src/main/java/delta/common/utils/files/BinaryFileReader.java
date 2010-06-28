package delta.common.utils.files;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * Reader to read a binary file.
 * @author DAM
 */
public class BinaryFileReader
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  private DataInputStream _dis;
  private BufferedInputStream _bis;
  private FileInputStream _fis;

  /**
   * File to read from.
   */
  private File _file;

  /**
   * Constructor.
   * @param path of file to read from.
   */
  public BinaryFileReader(File path)
  {
    _file=path;
  }

  /**
   * Start reading.
   * The steps are :
   * <ul>
   * <li>build a file input stream,
   * <li>build a buffered input stream around it,
   * <li>build a data input stream around it. 
   * </ul>
   * @return <code>true</code> if 
   */
  public boolean start()
  {
    boolean ret=true;
    try
    {
      _fis=new FileInputStream(_file);
      _bis=new BufferedInputStream(_fis);
      _dis=new DataInputStream(_bis);
    }
    catch (IOException ioException)
    {
      terminate();
      ret=false;
    }
    return ret;
  }

  /**
   * Get the data input stream associated with this binary file.
   * @return the data input stream associated with this binary file.
   */
  public DataInputStream getDataInputStream()
  {
    return _dis;
  }
  
  /**
   * Terminate writing.
   */
  public void terminate()
  {
    try
    {
      if (_dis!=null)
      {
        _dis.close();
        _dis=null;
      }
      if (_bis!=null)
      {
        _bis.close();
        _bis=null;
      }
      if (_fis!=null)
      {
        _fis.close();
        _fis=null;
      }
    }
    catch (IOException ioException)
    {
      _logger.error("Cannot close file : "+_file,ioException);
    }
  }
}
