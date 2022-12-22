package delta.common.utils.xml.sax.pojos;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.io.streams.IndentableStream;

/**
 * Sample main POJO for SAX parsing tests.
 * @author DAM
 */
public class MainPojo
{
  private List<Element1Pojo> _childList;

  /**
   * Constructor;
   */
  public MainPojo()
  {
    _childList=new ArrayList<Element1Pojo>();
  }

  /**
   * Add a child.
   * @param child Child to add.
   */
  public void addChild(Element1Pojo child)
  {
    _childList.add(child);
  }

  /**
   * Get the list of children.
   * @return a list of children.
   */
  public List<Element1Pojo> getChildList()
  {
    return _childList;
  }

  /**
   * Dump contents to the given stream.
   * @param out Output stream.
   */
  public void dump(IndentableStream out)
  {
    for(Element1Pojo pojo : _childList)
    {
      pojo.dump(out);
    }
  }
}
