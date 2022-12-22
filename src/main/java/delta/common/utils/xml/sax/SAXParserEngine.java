package delta.common.utils.xml.sax;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SAX parser engine, that uses valves to implement the actual parsing.
 * @param <RESULT> Type of result.
 * @author DAM
 */
public class SAXParserEngine<RESULT> extends DefaultHandler
{
  private static final Logger LOGGER=Logger.getLogger(SAXParserEngine.class);

  private SAXParserValve<RESULT> _initial;
  private SAXParserValve<?> _current;

  /**
   * Constructor.
   * @param initialValve Initial valve.
   */
  public SAXParserEngine(SAXParserValve<RESULT> initialValve)
  {
    _initial=initialValve;
    _current=initialValve;
  }

  @Override
  public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) throws SAXException
  {
    if (LOGGER.isDebugEnabled())
    {
      LOGGER.debug("Handling START of tag "+qualifiedName+" with valve: "+_current);
    }
    SAXParserValve<?> next=_current.handleStartTag(qualifiedName,attributes);
    while (next!=_current)
    {
      _current=next;
      if (LOGGER.isDebugEnabled())
      {
        LOGGER.debug("Switching to a new valve: "+_current);
      }
      startElement(uri,localName,qualifiedName,attributes);
    }
  }

  @Override
  public void endElement(String uri, String localName, String qualifiedName)
  {
    if (LOGGER.isDebugEnabled())
    {
      LOGGER.debug("Handlng END of tag "+qualifiedName+" with parser: "+_current);
    }
    SAXParserValve<?> next=_current.handleEndTag(qualifiedName);
    if (next!=_current)
    {
      _current=next;
      if (LOGGER.isDebugEnabled())
      {
        LOGGER.debug("Switching to a new valve: "+_current);
      }
      endElement(uri,localName,qualifiedName);
    }
  }

  /**
   * Get the parsing result.
   * @return a result.
   */
  public RESULT getResult()
  {
    return _initial.getResult();
  }
}
