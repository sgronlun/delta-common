package delta.common.utils.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * Serialization/deserialization tools.
 * @author DAM
 */
public class SerializationTools
{
  private static final Logger _logger=UtilsLoggers.getIOLogger();

  /**
   * Serialization to a byte buffer.
   * @param o Object to serialize.
   * @return A byte buffer that contains the serialized object, or <code>null</code> if a
   * problem occurred.
   */
  public static byte[] serialize(Object o)
  {
    byte[] ret=null;
    if (o!=null)
    {
      ByteArrayOutputStream baos=null;
      ObjectOutputStream oos=null;
      try
      {
        baos=new ByteArrayOutputStream();
        oos=new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.flush();
        ret=baos.toByteArray();
      }
      catch(Exception e)
      {
        _logger.error("",e);
      }
      finally
      {
        StreamTools.close(oos);
        oos=null;
        StreamTools.close(baos);
        baos=null;
      }
    }
    return ret;
  }

  /**
   * Deserialize an object from a byte buffer.
   * @param buffer Serialized version of object.
   * @return An object or <code>null</code> if a problem occurred.
   */
  public static Object deserialize(byte[] buffer)
  {
    Object o=null;
    if ((buffer!=null) && (buffer.length>0))
    {
      ByteArrayInputStream bais=null;
      ObjectInputStream ois=null;
      try
      {
        bais=new ByteArrayInputStream(buffer);
        ois=new ObjectInputStream(bais);
        o=ois.readObject();
      }
      catch(Exception e)
      {
        _logger.error("",e);
      }
      finally
      {
        StreamTools.close(ois);
        ois=null;
        StreamTools.close(bais);
        bais=null;
      }
    }
    return o;
  }

  /**
   * Open a data input stream that reads from a buffer.
   * @param buffer Buffer to read from.
   * @return A <tt>DataInputStream</tt>.
   */
  public static DataInputStream openDataInputStream(byte[] buffer)
  {
    ByteArrayInputStream bais=new ByteArrayInputStream(buffer);
    DataInputStream dis=new DataInputStream(bais);
    return dis;
  }
}
