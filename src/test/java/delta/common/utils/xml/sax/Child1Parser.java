package delta.common.utils.xml.sax;

import org.xml.sax.Attributes;

import delta.common.utils.xml.SAXParsingTools;
import delta.common.utils.xml.sax.pojos.Child1Pojo;
import delta.common.utils.xml.sax.pojos.ChildofChild1Pojo;

/**
 * Parser for "child 1" elements.
 * @author DAM
 */
public class Child1Parser extends SAXParserValve<Child1Pojo>
{
  @Override
  public SAXParserValve<?> handleStartTag(String tagName, Attributes attrs)
  {
    if (SampleSaxParserConstants.CHILD1.equals(tagName))
    {
      Child1Pojo child1=new Child1Pojo();
      setResult(child1);
      // ID
      int id=SAXParsingTools.getIntAttribute(attrs,SampleSaxParserConstants.ID,0);
      child1.setId(id);
      // Name
      String name=SAXParsingTools.getStringAttribute(attrs,SampleSaxParserConstants.NAME,"");
      child1.setName(name);
    }
    else if (SampleSaxParserConstants.CHILDOFCHILD1.equals(tagName))
    {
      ChildofChild1Pojo child=new ChildofChild1Pojo();
      getResult().addChild(child);
      // ID
      int id=SAXParsingTools.getIntAttribute(attrs,SampleSaxParserConstants.ID,0);
      child.setId(id);
      // Name
      String name=SAXParsingTools.getStringAttribute(attrs,SampleSaxParserConstants.NAME,"");
      child.setName(name);
    }
    return this;
  }

  @Override
  public SAXParserValve<?> handleEndTag(String tagName)
  {
    if (SampleSaxParserConstants.CHILD1.equals(tagName))
    {
      return getParent();
    }
    return this;
  }

  @Override
  public String toString()
  {
    return "Child1";
  }
}
