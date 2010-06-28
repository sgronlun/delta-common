package delta.common.utils.identifiers;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author DAM
 */
public class ImageStreamIdentifier implements StreamIdentifier
{
  /* (non-Javadoc)
   * @see delta.common.utils.identifiers.StreamIdentifier#identify(java.io.InputStream)
   */
  public TypeInfo identify(InputStream io) throws IOException
  {
    readBytes(null,0);
	  return null;
  }

  private byte[] readBytes(InputStream is, int length)
  {
	  return null;
  }
}
