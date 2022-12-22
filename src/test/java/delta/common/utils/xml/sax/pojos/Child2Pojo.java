package delta.common.utils.xml.sax.pojos;

import delta.common.utils.io.streams.IndentableStream;

/**
 * Sample child 2 POJO for SAX parsing tests.
 * @author DAM
 */
public class Child2Pojo extends IdAndName
{
  /**
   * Dump contents to the given stream.
   * @param out Output stream.
   */
  public void dump(IndentableStream out)
  {
    out.println(toString());
  }
}
