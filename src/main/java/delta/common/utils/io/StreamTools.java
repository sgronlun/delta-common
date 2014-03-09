package delta.common.utils.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * Tool methods related to I/O streams.
 * @author DAM
 */
public class StreamTools
{
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
        ioe.printStackTrace();
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
        ioe.printStackTrace();
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
        ioe.printStackTrace();
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
        ioe.printStackTrace();
      }
    }
  }
}
