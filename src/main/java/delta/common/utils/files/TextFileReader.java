package delta.common.utils.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

import delta.common.utils.io.StreamTools;
import delta.common.utils.traces.UtilsLoggers;

/**
 * Reader to read a text file line by line.
 * @author DAM
 */
public class TextFileReader
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  private Charset _charset;
  private BufferedReader _bufferedReader;
  private InputStreamReader _isR;
  private FileInputStream _fis;
  private File _path;

  /**
   * Constructor.
   * @param path file to read
   */
  public TextFileReader(File path)
  {
  	_path=path;
    _charset=Charset.defaultCharset();
  }

  /**
   * Constructor.
   * @param path file to read
   * @param encoding Encoding to use.
   */
  public TextFileReader(File path, String encoding)
  {
    _path=path;
    if (encoding!=null)
    {
      _charset=Charset.forName(encoding);
    }
    else
    {
      _charset=Charset.defaultCharset();      
    }
  }

  /**
   * Constructor.
   * @param path file to read
   * @param charset Character set to use.
   */
  public TextFileReader(File path, Charset charset)
  {
    _path=path;
    _charset=charset;
  }

  /**
   * Start reading.
   * Several steps are involved :
   * <ul>
   * <li>check if the file is readable,
   * <li>build a buffered reader to read lines.
   * </ul>
   * @return <code>true</code> if
   */
  public boolean start()
  {
    boolean ret=true;
    // Existenz test
    if(!_path.canRead())
    {
      _logger.error("File not found or unreadable ["+_path.getAbsolutePath()+"]");
      ret=false;
    }
    else
    {
      try
      {
        _fis=new FileInputStream(_path);
        _isR=new InputStreamReader(_fis,_charset);
        _bufferedReader=new BufferedReader(_isR);
      }
      catch(IOException ioException)
      {
        _logger.error("Error when starting a TextFileReader !",ioException);
        terminate();
        ret=false;
      }
    }
    return ret;
  }

  /**
   * Read the next line.
   * @return a line or <code>null</code> if the end of file is reached.
   */
  public String getNextLine()
  {
    String line_l=null;
    try
    {
      if(_bufferedReader.ready())
      {
        line_l=_bufferedReader.readLine();
      }
    }
    catch(IOException ioException)
    {
      _logger.error("Error while reading file : "+_path.getAbsolutePath(),ioException);
    }
    return line_l;
  }

  /**
   * Terminate reading.
   * Closes the buffered reader.
   */
  public void terminate()
  {
    StreamTools.close(_bufferedReader);
    _bufferedReader=null;
    StreamTools.close(_isR);
    _isR=null;
    StreamTools.close(_fis);
    _fis=null;
  }
}
