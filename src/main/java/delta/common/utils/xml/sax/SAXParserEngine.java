package delta.common.utils.xml.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author dmorcellet
 */
public class SAXParserEngine extends DefaultHandler
{
  private SAXParserValve<?> _current;

  
  @Override
  public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) throws SAXException
  {
    SAXParserValve<?> next=_current.handleStartTag(qualifiedName,attributes);
    _current=next;
  }

  @Override
  public void endElement(String uri, String localName, String qualifiedName)
  {
    _current.handleEndTag(qualifiedName);
  }
}
