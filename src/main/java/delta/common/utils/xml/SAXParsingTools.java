package delta.common.utils.xml;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;

import delta.common.utils.BooleanTools;
import delta.common.utils.NumericTools;
import delta.common.utils.xml.sax.SAXParserEngine;

/**
 * SAX parsing tools.
 * @author DAM
 */
public class SAXParsingTools
{
  private static final Logger LOGGER=Logger.getLogger(SAXParsingTools.class);

  /**
   * Parse the XML file.
   * @param source Source file.
   * @param engine Parsing engine.
   * @return result.
   */
  public static <RESULT> RESULT parseFile(File source, SAXParserEngine<RESULT> engine)
  {
    try
    {
      // Use the default (non-validating) parser
      SAXParserFactory factory=SAXParserFactory.newInstance();
      SAXParser saxParser=factory.newSAXParser();
      saxParser.parse(source,engine);
      saxParser.reset();
      return engine.getResult();
    }
    catch (Exception e)
    {
      LOGGER.error("Error when loading items file "+source,e);
    }
    return null;
  }

  /**
   * Get an integer attribute from SAX attributes.
   * @param attrs Attributes to use.
   * @param attrName Name of attribute to search.
   * @param defaultValue Default value (returned if no such attribute is found, or if attribute's value does not parse as an integer).
   * @return An integer value (found value or default value).
   */
  public static int getIntAttribute(Attributes attrs, String attrName, int defaultValue)
  {
    String valueStr=attrs.getValue(attrName);
    int ret=NumericTools.parseInt(valueStr,defaultValue);
    return ret;
  }

  /**
   * Get a float attribute from SAX attributes.
   * @param attrs Attributes to use.
   * @param attrName Name of attribute to search.
   * @param defaultValue Default value (returned if no such attribute is found, or if attribute's value does not parse as a float).
   * @return A float value (found value or default value).
   */
  public static float getFloatAttribute(Attributes attrs, String attrName, float defaultValue)
  {
    String valueStr=attrs.getValue(attrName);
    float ret=NumericTools.parseFloat(valueStr,defaultValue);
    return ret;
  }

  /**
   * Get a string attribute from SAX attributes.
   * @param attrs Attributes to use.
   * @param attrName Name of attribute to search.
   * @param defaultValue Default value (returned if no such attribute is found).
   * @return A string value (found value or default value).
   */
  public static String getStringAttribute(Attributes attrs, String attrName, String defaultValue)
  {
    String valueStr=attrs.getValue(attrName);
    return (valueStr!=null)?valueStr:defaultValue;
  }

  /**
   * Get a boolean attribute from SAX attributes.
   * @param attrs Attributes to use.
   * @param attrName Name of attribute to search.
   * @param defaultValue Default value (returned if no such attribute is found, or if attribute's value does not parse as a boolean).
   * @return A boolean value (found value or default value).
   */
  public static boolean getBooleanAttribute(Attributes attrs, String attrName, boolean defaultValue)
  {
    String valueStr=attrs.getValue(attrName);
    if (valueStr!=null)
    {
      return BooleanTools.parseBoolean(valueStr,defaultValue);
    }
    return defaultValue;
  }
}
