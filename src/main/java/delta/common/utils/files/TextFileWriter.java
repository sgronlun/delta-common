package delta.common.utils.files;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

import delta.common.utils.io.StreamTools;
import delta.common.utils.misc.MiscStringConstants;
import delta.common.utils.traces.UtilsLoggers;

/**
 * Writer to write a text file line by line.
 * @author DAM
 */
public class TextFileWriter
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  private BufferedWriter _bufferedWriter;
  private OutputStreamWriter _osWriter;
  private FileOutputStream _fos;

  /**
   * File to write to.
   */
  private File _file;

  /**
   * Encoding to use.
   */
  //private String _encoding;
  private Charset _charset;

  /**
   * End of line to use.
   */
  private String _endOfLine;

  /**
   * Constructor.
   * @param path of file to write to.
   */
  public TextFileWriter(File path)
  {
    this(path,null);
  }

  /**
   * Full constructor.
   * @param path of file to write to.
   * @param encoding Encoding.
   */
  public TextFileWriter(File path, String encoding)
  {
    this(path,encoding,null);
  }

  /**
   * Full constructor.
   * @param path of file to write to.
   * @param encoding Encoding.
   * @param endOfLine End of line (or <code>null</code> for default/native EOL).
   */
  public TextFileWriter(File path, String encoding, String endOfLine)
  {
    this(path,(encoding!=null)?Charset.forName(encoding):Charset.defaultCharset(),endOfLine);      
  }
  
  /**
   * Full constructor.
   * @param path of file to write to.
   * @param charset Charset to use.
   * @param endOfLine End of line (or <code>null</code> for default/native EOL).
   */
  public TextFileWriter(File path, Charset charset, String endOfLine)
  {
    _file=path;
    _charset=charset;
    String eof=endOfLine;
    if (eof==null)
    {
      eof=MiscStringConstants.NATIVE_EOL;
    }
    _endOfLine=eof;
  }

  /**
   * Start writing.
   * The steps are :
   * <ul>
   * <li>build a buffered writer to write lines.
   * </ul>
   * @return <code>true</code> if everything was ok, <code>false</code> otherwise.
   */
  public boolean start()
  {
    boolean ret=true;
    try
    {
      _fos=new FileOutputStream(_file);
      _osWriter=new OutputStreamWriter(_fos,_charset);
      _bufferedWriter=new BufferedWriter(_osWriter);
    }
    catch (IOException ioException)
    {
      _logger.error("Cannot open file ["+_file+"]",ioException);
      terminate();
      ret=false;
    }
    return ret;
  }

  /**
   * Write a piece of text.
   * @param text to write
   * @return <code>true</code> if line was successfully written, <code>false</code> otherwise.
   */
  public boolean writeSomeText(String text)
  {
    boolean ret=false;
    try
    {
      _bufferedWriter.write(text);
      ret=true;
    }
    catch (IOException ioException)
    {
      _logger.error("Cannot write text to file ["+_file+"]",ioException);
    }
    return ret;
  }

  /**
   * Write a line.
   * @param line to write
   * @return <code>true</code> if line was successfully written, <code>false</code> otherwise.
   */
  public boolean writeNextLine(String line)
  {
    boolean ret=false;
    try
    {
      _bufferedWriter.write(line);
      _bufferedWriter.write(_endOfLine);
      ret=true;
    }
    catch (IOException ioException)
    {
      _logger.error("Cannot write text line to file ["+_file+"]",ioException);
    }
    return ret;
  }

  /**
   * Write a line.
   * @param value long to write on a single line
   * @return <code>true</code> if line was successfully written, <code>false</code> otherwise.
   */
  public boolean writeNextLine(long value)
  {
    boolean ret=false;
    try
    {
      _bufferedWriter.write(String.valueOf(value));
      _bufferedWriter.write(_endOfLine);
      ret=true;
    }
    catch (IOException ioException)
    {
      _logger.error("Cannot write a line with a long to file ["+_file+"]", ioException);
    }
    return ret;
  }

  /**
   * Terminate writing.
   * Closes the buffered writer.
   */
  public void terminate()
  {
    StreamTools.close(_bufferedWriter);
    _bufferedWriter=null;
    StreamTools.close(_osWriter);
    _osWriter=null;
    StreamTools.close(_fos);
    _fos=null;
  }
}
