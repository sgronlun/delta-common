package delta.common.utils.xml.sax;

import java.io.File;

import delta.common.utils.xml.SAXParsingTools;
import delta.common.utils.xml.sax.pojos.MainPojo;
import junit.framework.TestCase;

/**
 * Test class for the SAX parser engine.
 * @author DAM
 */
public class SAXParserEngineTest extends TestCase
{
  /**
   * Test the SAX parser engine.
   */
  public void testEngine()
  {
    File from=new File("sample.xml");
    SAXParserEngine<MainPojo> engine=new SAXParserEngine<>(new MainParser());
    MainPojo result=SAXParsingTools.parseFile(from,engine);
    System.out.println("Result: "+result);
  }
}
