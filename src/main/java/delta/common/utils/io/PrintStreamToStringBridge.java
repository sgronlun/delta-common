package delta.common.utils.io;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

/**
 * A tool class that makes a bridge from a print stream to a string.
 * It is used to build a <tt>String</tt> from data written to a <tt>PrintStream</tt>.
 * @author DAM
 */
public class PrintStreamToStringBridge
{
  private static final Logger LOGGER=Logger.getLogger(PrintStreamToStringBridge.class);

  private static final String DEFAULT_ENCODING="UTF-8";
  private String _encoding;
  private ByteArrayOutputStream _baos;
  private PrintStream _ps;

  /**
   * Constructor.
   */
  public PrintStreamToStringBridge()
  {
    _encoding=DEFAULT_ENCODING;
  }

  /**
   * Get the print stream managed by this instance.
   * @return the print stream managed by this instance.
   */
  public PrintStream getPrintStream()
  {
    if (_ps==null)
    {
      try
      {
        _baos=new ByteArrayOutputStream();
        _ps=new PrintStream(_baos,false,_encoding);
      }
      catch(UnsupportedEncodingException e)
      {
        LOGGER.warn("Unsupported encoding: "+_encoding,e);
      }
    }
    return _ps;
  }

  /**
   * Get the text written to the stream managed by this instance.
   * @return the text written to the stream managed by this instance.
   */
  public String getText()
  {
    String ret="";
    if (_ps!=null)
    {
      _ps.flush();
      byte[] buffer=_baos.toByteArray();
      try
      {
        ret=new String(buffer,_encoding);
      }
      catch(UnsupportedEncodingException e)
      {
        LOGGER.warn("Unsupported encoding: "+_encoding,e);
      }
    }
    return ret;
  }

  /**
   * Close the managed stream.
   */
  public void close()
  {
    _ps.flush();
    _ps.close();
    _ps=null;
    _baos=null;
  }
}
