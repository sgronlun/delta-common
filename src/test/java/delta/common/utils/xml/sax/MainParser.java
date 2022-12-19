package delta.common.utils.xml.sax;

import delta.common.utils.xml.sax.pojos.MainPojo;

/**
 * Parser for "root" and "element1" elements.
 * @author DAM
 */
public class MainParser extends SAXParserValve<MainPojo>
{
  private Child1Parser _child1Parser;
  private Child2Parser _child2Parser;

  /**
   * Constructor.
   */
  public MainParser()
  {
    _child1Parser=new Child1Parser();
    _child2Parser=new Child2Parser();
  }
}
