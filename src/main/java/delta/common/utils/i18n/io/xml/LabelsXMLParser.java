package delta.common.utils.i18n.io.xml;

import java.io.File;

import org.xml.sax.Attributes;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.xml.SAXParsingTools;
import delta.common.utils.xml.sax.SAXParserEngine;
import delta.common.utils.xml.sax.SAXParserValve;

/**
 * Parser for the labels stored in XML.
 * @author DAM
 */
public class LabelsXMLParser extends SAXParserValve<SingleLocaleLabelsManager>
{
  /**
   * Parse the XML file.
   * @param source Source file.
   * @return the loaded labels manager.
   */
  public SingleLocaleLabelsManager parseSingleLocaleLabels(File source)
  {
    SAXParserEngine<SingleLocaleLabelsManager> engine=new SAXParserEngine<>(this);
    SAXParsingTools.parseFile(source,engine);
    return engine.getResult();
  }

  @Override
  public SAXParserValve<?> handleStartTag(String tagName, Attributes attrs)
  {
    if (LabelsXMLConstants.LABEL_TAG.equals(tagName))
    {
      String key=SAXParsingTools.getStringAttribute(attrs,LabelsXMLConstants.LABEL_KEY_ATTR,null);
      String value=SAXParsingTools.getStringAttribute(attrs,LabelsXMLConstants.LABEL_VALUE_ATTR,null);
      if ((key!=null) && (value!=null))
      {
        getResult().addLabel(key,value);
      }
    }
    else if (LabelsXMLConstants.LABELS_TAG.equals(tagName))
    {
      String localeKey=SAXParsingTools.getStringAttribute(attrs,LabelsXMLConstants.LABELS_LOCALE_ATTR,"?");
      SingleLocaleLabelsManager mgr=new SingleLocaleLabelsManager(localeKey);
      setResult(mgr);
    }
    return this;
  }
}
