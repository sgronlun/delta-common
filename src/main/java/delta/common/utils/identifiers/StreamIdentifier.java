package delta.common.utils.identifiers;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author DAM
 */
public interface StreamIdentifier
{
  public TypeInfo identify(InputStream io) throws IOException;
}
