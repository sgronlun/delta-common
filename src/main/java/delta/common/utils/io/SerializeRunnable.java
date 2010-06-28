package delta.common.utils.io;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Interface of objects used by the <tt>ByteArraySerializer</tt>.
 * @author DAM
 */
public interface SerializeRunnable
{
  /**
   * Serialize some data to the given stream.
   * @param stream Stream to write to.
   * @throws IOException
   */
  public void serialize(DataOutputStream stream) throws IOException;
}
