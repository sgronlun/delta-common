package delta.common.utils.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * @author DAM
 */
public class StreamTools
{
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
