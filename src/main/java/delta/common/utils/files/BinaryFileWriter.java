package delta.common.utils.files;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import delta.common.utils.io.StreamTools;

/**
 * Writer to write a binary file.
 * @author DAM
 */
public class BinaryFileWriter
{
  private DataOutputStream _dos;
  private BufferedOutputStream _bos;
  private FileOutputStream _fos;

  /**
   * File to write to.
   */
  private File _file;

  /**
   * Constructor.
   * @param path of file to write to.
   */
  public BinaryFileWriter(File path)
  {
    _file=path;
  }

  /**
   * Start writing.
   * The steps are :
   * <ul>
   * <li>build a file output stream,
   * <li>build a buffered output stream around it,
   * <li>build a data output stream around it. 
   * </ul>
   * @return <code>true</code> if 
   */
  public boolean start()
  {
    boolean ret=true;
    try
    {
      _fos=new FileOutputStream(_file);
      _bos=new BufferedOutputStream(_fos);
      _dos=new DataOutputStream(_bos);
    }
    catch (IOException ioException)
    {
      terminate();
      ret=false;
    }
    return ret;
  }

  /**
   * Get the data output stream associated with this binary file.
   * @return the data output stream associated with this binary file.
   */
  public DataOutputStream getDataOutputStream()
  {
    return _dos;
  }
  
  /**
   * Terminate writing.
   */
  public void terminate()
  {
    StreamTools.close(_dos);
    _dos=null;
    StreamTools.close(_bos);
    _bos=null;
    StreamTools.close(_fos);
    _fos=null;
  }
}
