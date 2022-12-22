package delta.common.utils.xml.sax;

import org.xml.sax.Attributes;

import delta.common.utils.xml.SAXParsingTools;
import delta.common.utils.xml.sax.pojos.Element1Pojo;
import delta.common.utils.xml.sax.pojos.MainPojo;

/**
 * Parser for "root" and "element1" elements.
 * @author DAM
 */
public class MainParser extends SAXParserValve<MainPojo>
{
  private Element1Pojo _currentElement1;
  private Child1Parser _child1Parser;
  private Child2Parser _child2Parser;

  /**
   * Constructor.
   */
  public MainParser()
  {
    _child1Parser=new Child1Parser();
    _child1Parser.setParent(this);
    _child2Parser=new Child2Parser();
    _child2Parser.setParent(this);
  }

  @Override
  public SAXParserValve<?> handleStartTag(String tagName, Attributes attrs)
  {
    if (SampleSaxParserConstants.ROOT.equals(tagName))
    {
      MainPojo main=new MainPojo();
      setResult(main);
    }
    else if (SampleSaxParserConstants.ELEMENT1.equals(tagName))
    {
      _currentElement1=new Element1Pojo();
      getResult().addChild(_currentElement1);
      // ID
      int id=SAXParsingTools.getIntAttribute(attrs,SampleSaxParserConstants.ID,0);
      _currentElement1.setId(id);
      // Name
      String name=SAXParsingTools.getStringAttribute(attrs,SampleSaxParserConstants.NAME,"");
      _currentElement1.setName(name);
    }
    else if (SampleSaxParserConstants.CHILD1.equals(tagName))
    {
      return _child1Parser;
    }
    else if (SampleSaxParserConstants.CHILD2.equals(tagName))
    {
      return _child2Parser;
    }
    return super.handleStartTag(tagName,attrs);
  }

  @Override
  public SAXParserValve<?> handleEndTag(String tagName)
  {
    if (SampleSaxParserConstants.CHILD1.equals(tagName))
    {
      _currentElement1.addChild1(_child1Parser.getResult());
    }
    else if (SampleSaxParserConstants.CHILD2.equals(tagName))
    {
      _currentElement1.addChild2(_child2Parser.getResult());
    }
    return this;
  }

  @Override
  public String toString()
  {
    return "Main";
  }
}
