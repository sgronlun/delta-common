package delta.common.utils.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import org.apache.log4j.Logger;

/**
 * Tool methods related to I/O streams.
 * @author DAM
 */
public class StreamTools
{
  private static final Logger LOGGER=Logger.getLogger(StreamTools.class);

  /**
   * Close an input stream.
   * @param is Input stream to close.
   */
  public static void close(InputStream is)
  {
    if (is!=null)
    {
      try
      {
        is.close();
      }
      catch(IOException ioe)
      {
        LOGGER.error("Could not close input stream!",ioe);
      }
    }
  }

  /**
   * Close an output stream.
   * @param os Output stream to close.
   */
  public static void close(OutputStream os)
  {
    if (os!=null)
    {
      try
      {
        os.close();
      }
      catch(IOException ioe)
      {
        LOGGER.error("Could not close output stream!",ioe);
      }
    }
  }

  /**
   * Close a reader.
   * @param r Reader to close.
   */
  public static void close(Reader r)
  {
    if (r!=null)
    {
      try
      {
        r.close();
      }
      catch(IOException ioe)
      {
        LOGGER.error("Could not close reader!",ioe);
      }
    }
  }

  /**
   * Close a writer.
   * @param w Writer to close.
   */
  public static void close(Writer w)
  {
    if (w!=null)
    {
      try
      {
        w.close();
      }
      catch(IOException ioe)
      {
        LOGGER.error("Could not close writer!",ioe);
      }
    }
  }
}
