package delta.common.utils.xml.sax;

import org.xml.sax.Attributes;

import delta.common.utils.xml.SAXParsingTools;
import delta.common.utils.xml.sax.pojos.Child2Pojo;

/**
 * Parser for "child 2" elements.
 * @author DAM
 */
public class Child2Parser extends SAXParserValve<Child2Pojo>
{
  @Override
  public SAXParserValve<?> handleStartTag(String tagName, Attributes attrs)
  {
    if (SampleSaxParserConstants.CHILD2.equals(tagName))
    {
      Child2Pojo child2=new Child2Pojo();
      setResult(child2);
      // ID
      int id=SAXParsingTools.getIntAttribute(attrs,SampleSaxParserConstants.ID,0);
      child2.setId(id);
      // Name
      String name=SAXParsingTools.getStringAttribute(attrs,SampleSaxParserConstants.NAME,"");
      child2.setName(name);
    }
    return this;
  }

  @Override
  public SAXParserValve<?> handleEndTag(String tagName)
  {
    if (SampleSaxParserConstants.CHILD2.equals(tagName))
    {
      return getParent();
    }
    return this;
  }

  @Override
  public String toString()
  {
    return "Child2";
  }
}
