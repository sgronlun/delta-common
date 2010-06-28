package delta.common.utils.io;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * Tool class to serialize some data to a byte array.
 * @author DAM
 */
public class ByteArraySerializer
{
  private static final Logger _logger=UtilsLoggers.getIOLogger();

  /**
   * Constructor.
   */
  public ByteArraySerializer()
  {
    // Nothing to do !!
  }

  /**
   * Do serialization.
   * @param r Runnable that runs business serialization code.
   * @return Result buffer.
   */
  public byte[] doIt(SerializeRunnable r)
  {
    byte[] buffer=null;
    ByteArrayOutputStream baos=null;
    DataOutputStream dos=null;
    try
    {
      baos=new ByteArrayOutputStream();
      dos=new DataOutputStream(baos);
      r.serialize(dos);
      buffer=baos.toByteArray();
    }
    catch(Exception e)
    {
      _logger.error("",e);
    }
    finally
    {
      StreamTools.close(dos);
      dos=null;
      StreamTools.close(baos);
      baos=null;
    }
    return buffer;
  }

}
