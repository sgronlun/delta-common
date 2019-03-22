package delta.common.utils.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

import delta.common.utils.io.StreamTools;

/**
 * Reader to read a text file line by line.
 * @author DAM
 */
public class TextFileReader
{
  private static final Logger LOGGER=Logger.getLogger(TextFileReader.class);

  // Charset to use
  private Charset _charset;
  // Temp mechanics
  private BufferedReader _bufferedReader;
  private InputStreamReader _isR;
  private InputStream _is;
  // Source
  private File _path;
  private URL _url;

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
    _charset=fromEncodingName(encoding);
  }

  private Charset fromEncodingName(String encoding)
  {
    Charset charset;
    if (encoding!=null)
    {
      charset=Charset.forName(encoding);
    }
    else
    {
      charset=Charset.defaultCharset();
    }
    return charset;
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
   * Constructor from an URL.
   * @param url URL to use.
   * @param encoding Encoding to use.
   */
  public TextFileReader(URL url, String encoding)
  {
    _url=url;
    _charset=fromEncodingName(encoding);
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
    /*
    // Existenz test
    if(!_path.canRead())
    {
      LOGGER.error("File not found or unreadable ["+_path.getAbsolutePath()+"]");
      ret=false;
    }
    else
    */
    {
      try
      {
        _is=buildInputStream();
        _isR=new InputStreamReader(_is,_charset);
        _bufferedReader=new BufferedReader(_isR);
      }
      catch(IOException ioException)
      {
        LOGGER.error("Error when starting a TextFileReader !",ioException);
        terminate();
        ret=false;
      }
    }
    return ret;
  }

  private InputStream buildInputStream() throws IOException
  {
    InputStream is=null;
    if (_path!=null)
    {
      is=new FileInputStream(_path);
    }
    else if (_url!=null)
    {
      is=_url.openStream();
    }
    return is;
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
      LOGGER.error("Error while reading file : "+_path.getAbsolutePath(),ioException);
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
    StreamTools.close(_is);
    _is=null;
  }
}
