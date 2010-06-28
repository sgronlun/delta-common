package delta.common.utils.types.io.xml;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.w3c.dom.Element;

import delta.common.utils.types.Type;
import delta.common.utils.url.URLTools;
import delta.common.utils.xml.DOMParsingTools;

/**
 * Unit test class for the types XML parser.
 * @author DAM
 */
public class TestTypeXMLParser extends TestCase
{
  /**
   * Test the types parser.
   */
  public void testParser()
  {
    URL url=URLTools.getFromClassPath("types.xml",this);
    TypeXMLParser parser=new TypeXMLParser();
    Element root=DOMParsingTools.parse(url);
    List<Element> typeElements=DOMParsingTools.getChildTagsByName(root,"TYPE");
    Element typeElement;
    Type type;
    for(Iterator<Element> it=typeElements.iterator();it.hasNext();)
    {
      typeElement=it.next();
      type=parser.parseTypeNode(typeElement);
      Assert.assertNotNull(type);
      type.finish();
      type.dump(System.out);
    }
  }
}
