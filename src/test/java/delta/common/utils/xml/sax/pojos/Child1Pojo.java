package delta.common.utils.xml.sax.pojos;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.io.streams.IndentableStream;

/**
 * Sample child 1 POJO for SAX parsing tests.
 * @author DAM
 */
public class Child1Pojo extends IdAndName
{
  private List<ChildofChild1Pojo> _childList;

  /**
   * Constructor;
   */
  public Child1Pojo()
  {
    _childList=new ArrayList<ChildofChild1Pojo>();
  }

  /**
   * Add a child.
   * @param child Child to add.
   */
  public void addChild(ChildofChild1Pojo child)
  {
    _childList.add(child);
  }

  /**
   * Get the list of children.
   * @return a list of children.
   */
  public List<ChildofChild1Pojo> getChildList()
  {
    return _childList;
  }

  /**
   * Dump contents to the given stream.
   * @param out Output stream.
   */
  public void dump(IndentableStream out)
  {
    out.println(toString());
    out.incrementIndendationLevel();
    for(ChildofChild1Pojo pojo : _childList)
    {
      pojo.dump(out);
    }
    out.decrementIndentationLevel();
  }
}
