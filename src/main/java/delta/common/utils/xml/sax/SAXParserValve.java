package delta.common.utils.xml.sax;

import org.xml.sax.Attributes;

/**
 * Valve of a SAX parser.
 * Handles a limited scope of tags in an XML document.
 * @author DAM
 * @param <RESULT> Type of parsing result.
 */
public class SAXParserValve<RESULT>
{
  private SAXParserValve<?> _parent;
  private RESULT _result;

  /**
   * Constructor.
   */
  public SAXParserValve()
  {
    // Nothing!
  }

  /**
   * Get the parent.
   * @return A parent.
   */
  public SAXParserValve<?> getParent()
  {
    return _parent;
  }

  /**
   * Set the parent.
   * @param parent Parent to set.
   */
  public void setParent(SAXParserValve<?> parent)
  {
    _parent=parent;
  }

  /**
   * Get the managed result.
   * @return the managed result.
   */
  public RESULT getResult()
  {
    return _result;
  }

  /**
   * Handle the start of a tag.
   * @param tagName Tag name.
   * @param attrs Tag attributes.
   * @return the valve that will handle the next start/end tag.
   */
  public SAXParserValve<?> handleStartTag(String tagName, Attributes attrs)
  {
    return this;
  }

  /**
   * Handle the end of a tag.
   * @param tagName Tag Name.
   */
  public void handleEndTag(String tagName)
  {
    // Nothing!
  }
}
