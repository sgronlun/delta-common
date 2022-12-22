package delta.common.utils.xml.sax.pojos;

import delta.common.utils.io.streams.IndentableStream;

/**
 * Child of Child 1 POJO for SAX parsing tests.
 * @author DAM
 */
public class ChildofChild1Pojo extends IdAndName
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
