package delta.common.utils.io.xml;

import javax.xml.transform.sax.TransformerHandler;

/**
 * API to write some XML data to a <code>TransformerHandler</code>.
 * @author DAM
 */
public interface XmlWriter
{
  /**
   * CDATA constant.
   */
  public static final String CDATA="CDATA";

  /**
   * Write some XML data to the given stream.
   * @param hd Output stream.
   * @throws Exception If an error occurs.
   */
  void writeXml(TransformerHandler hd) throws Exception;
}
