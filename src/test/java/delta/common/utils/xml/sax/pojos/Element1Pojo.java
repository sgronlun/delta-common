package delta.common.utils.xml.sax.pojos;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.io.streams.IndentableStream;

/**
 * Sample element 1 POJO for SAX parsing tests.
 * @author DAM
 */
public class Element1Pojo extends IdAndName
{
  private List<Child1Pojo> _child1List;
  private List<Child2Pojo> _child2List;

  /**
   * Constructor;
   */
  public Element1Pojo()
  {
    _child1List=new ArrayList<Child1Pojo>();
    _child2List=new ArrayList<Child2Pojo>();
  }

  /**
   * Add a child 1.
   * @param child1 Child 1 to add.
   */
  public void addChild1(Child1Pojo child1)
  {
    _child1List.add(child1);
  }

  /**
   * Add a child 2.
   * @param child2 Child 1 to add.
   */
  public void addChild2(Child2Pojo child2)
  {
    _child2List.add(child2);
  }

  /**
   * Get the list of child 1.
   * @return a list of child 1.
   */
  public List<Child1Pojo> getChild1List()
  {
    return _child1List;
  }

  /**
   * Get the list of child 2.
   * @return a list of child 2.
   */
  public List<Child2Pojo> getChild2List()
  {
    return _child2List;
  }

  /**
   * Dump contents to the given stream.
   * @param out Output stream.
   */
  public void dump(IndentableStream out)
  {
    out.println(toString());
    out.incrementIndendationLevel();
    for(Child1Pojo pojo : _child1List)
    {
      pojo.dump(out);
    }
    for(Child2Pojo pojo : _child2List)
    {
      pojo.dump(out);
    }
    out.decrementIndentationLevel();
  }
}
