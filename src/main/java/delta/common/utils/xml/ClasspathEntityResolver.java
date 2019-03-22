package delta.common.utils.xml;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * This class looks for entities in the classpath when the parser can't find them locally.
 * @author DAM
 */
public class ClasspathEntityResolver implements EntityResolver
{
  private static final Logger LOGGER=Logger.getLogger(ClasspathEntityResolver.class);

  /**
   * Resolve entities by looking in the classpath.
   * @param publicId Unused.
   * @param systemId Entity to look for.
   * @return An <tt>InputSource</tt> or <code>null</code> if not found.
   * @throws SAXException
   * @throws IOException
   */
  public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException
  {
    if (LOGGER.isDebugEnabled())
    {
      LOGGER.debug("Trying to resolve publicId=["+publicId+"], systemId=["+systemId+"]");
    }

    // Handle cases that we decide to forward to the default implementation :
    // A) A null systemId
    if (systemId==null) return null;
    // B) http:// protocol
    if (systemId.toLowerCase().startsWith("http:")) return null;

    // Windowsish characters replacement
    systemId=systemId.replace('\\','/');

    // Look in the classpath
    String aStreamSystemId=systemId;
    ClassLoader classLoader=ClasspathEntityResolver.class.getClassLoader();
    InputStream aStream=classLoader.getResourceAsStream(aStreamSystemId);

    // Try with a subpart of the entity ID
    int n=0;
    while ((aStream==null)&&(n>=0))
    {
      // Try with the end of systemId;
      n=systemId.indexOf('/',n+1);

      if (n>=0)
      {
        aStreamSystemId=systemId.substring(n+1);
        aStream=classLoader.getResourceAsStream(aStreamSystemId);
      }
    }

    if (aStream==null) return null;

    // Set systemId to the InputSource to avoid NPE in Xerces parser.
    InputSource result=new InputSource(aStream);
    //result.setSystemId(aStreamSystemId);

    if (LOGGER.isDebugEnabled())
    {
      LOGGER.debug("Resolved ["+systemId+"] to ["+aStreamSystemId+"]");
    }
    return result;
  }
}
