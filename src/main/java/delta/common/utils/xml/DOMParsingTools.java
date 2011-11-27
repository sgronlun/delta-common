package delta.common.utils.xml;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import delta.common.utils.BooleanTools;
import delta.common.utils.NumericTools;
import delta.common.utils.traces.UtilsLoggers;

/**
 * Set of tool methods related to DOM trees parsing.
 * @author DAM
 */
public abstract class DOMParsingTools
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  /**
   * Get a child element by its tag name.
   * @param e Root element.
   * @param tagName Name of tag.
   * @return A child element or <code>null</code> if not found.
   */
  public static Element getChildTagByName(Element e,String tagName)
  {
    NodeList nl=e.getElementsByTagName(tagName);
    Element ret=null;
    if (nl!=null)
    {
      int nbPropertiesNodes=nl.getLength();
      if (nbPropertiesNodes>=1)
      {
        if (nbPropertiesNodes>1)
        {
          // todo warn
        }
        ret=(Element)nl.item(0);
      }
    }
    return ret;
  }

  /**
   * Get a list of child elements that have the specified tag name.
   * @param e Root element.
   * @param tagName Name of tag.
   * @return A possibly empty list of child elements.
   */
  public static List<Element> getChildTagsByName(Element e,String tagName)
  {
    List<Element> ret=new ArrayList<Element>();
    NodeList nl=e.getElementsByTagName(tagName);
    if (nl!=null)
    {
      int nb=nl.getLength();
      if (nb>0)
      {
        for(int i=0;i<nb;i++)
        {
          ret.add((Element)nl.item(i));
        }
      }
    }
    return ret;
  }

  /**
   * Build a map that maps the values of attribute <code>attrName</code> of child tags <code>tagName</code>
   * to the list of child elements for which the <code>attrName</code> exists and has the key value.
   * @param e Root element.
   * @param tagName Name of tag.
   * @param attrName Name of filtering attribute.
   * @return A map "attribute values" to element lists.
   */
  public static Map<String,List<Element>> indexChildTagsByAttrValue(Element e, String tagName, String attrName)
  {
    Map<String,List<Element>> ret=new HashMap<String,List<Element>>();

    List<Element> elementsList;
    NodeList nl=e.getElementsByTagName(tagName);
    if (nl!=null)
    {
      int nb=nl.getLength();
      if (nb>0)
      {
        String attrValue;
        Element element;
        NamedNodeMap attrs;
        for(int i=0;i<nb;i++)
        {
          element=(Element)nl.item(i);
          attrs=element.getAttributes();
          attrValue=getStringAttribute(attrs,attrName,"");
          if (attrValue.length()>0)
          {
            elementsList=ret.get(attrValue);
            if (elementsList==null)
            {
              elementsList=new ArrayList<Element>();
              ret.put(attrValue,elementsList);
            }
            elementsList.add(element);
          }
          else
          {
            _logger.warn("No value for attribute ["+attrValue+"] on element ["+tagName+"]");
          }
        }
      }
    }
    return ret;
  }

  /**
   * Get a string attribute from a map of node attributes.
   * @param attrs Attributes to use.
   * @param attrName Name of attribute to search.
   * @param defaultValue Default value (returned if no such attribute is found).
   * @return A string value (found value or default value).
   */
  public static String getStringAttribute(NamedNodeMap attrs, String attrName, String defaultValue)
  {
    Node nameNode=attrs.getNamedItem(attrName);
    if (nameNode!=null) return nameNode.getNodeValue();
    return defaultValue;
  }

  /**
   * Get an integer attribute from a map of node attributes.
   * @param attrs Attributes to use.
   * @param attrName Name of attribute to search.
   * @param defaultValue Default value (returned if no such attribute is found, or if attribute's value does not parse as an integer).
   * @return An integer value (found value or default value).
   */
  public static int getIntAttribute(NamedNodeMap attrs, String attrName, int defaultValue)
  {
    Node tmp=attrs.getNamedItem(attrName);
    if (tmp!=null)
    {
      return NumericTools.parseInt(tmp.getNodeValue(),defaultValue);
    }
    return defaultValue;
  }

  /**
   * Get a long attribute from a map of node attributes.
   * @param attrs Attributes to use.
   * @param attrName Name of attribute to search.
   * @param defaultValue Default value (returned if no such attribute is found, or if attribute's value does not parse as a long).
   * @return A long value (found value or default value).
   */
  public static long getLongAttribute(NamedNodeMap attrs, String attrName, long defaultValue)
  {
    Node tmp=attrs.getNamedItem(attrName);
    if (tmp!=null)
    {
      return NumericTools.parseLong(tmp.getNodeValue(),defaultValue);
    }
    return defaultValue;
  }

  /**
   * Get a boolean attribute from a map of node attributes.
   * @param attrs Attributes to use.
   * @param attrName Name of attribute to search.
   * @param defaultValue Default value (returned if no such attribute is found, or if attribute's value does not parse as a boolean).
   * @return A boolean value (found value or default value).
   */
  public static boolean getBooleanAttribute(NamedNodeMap attrs, String attrName, boolean defaultValue)
  {
    Node tmp=attrs.getNamedItem(attrName);
    if (tmp!=null)
    {
      return BooleanTools.parseBoolean(tmp.getNodeValue(),defaultValue);
    }
    return defaultValue;
  }

  /**
   * Build a DOM tree from an URL.
   * @param url Targeted URL.
   * @return The root element of parsed tree or <code>null</code> if any problem.
   */
  public static Element parse(URL url)
  {
    if (url==null) return null;
    try
    {
      String uri=url.toURI().toString();
      DocumentBuilder builder=DocumentBuilderFactory.newInstance().newDocumentBuilder();
      builder.setEntityResolver(new ClasspathEntityResolver());
      Document doc=builder.parse(uri);
      Element root=doc.getDocumentElement();
      return root;
    }
    catch (Exception e)
    {
      _logger.error("Parsing error",e);
    }
    return null;
  }

  /**
   * Build a DOM tree from a file.
   * @param source Source file
   * @return The root element of parsed tree or <code>null</code> if any problem.
   */
  public static Element parse(File source)
  {
    if (source==null) return null;
    try
    {
      DocumentBuilder builder=DocumentBuilderFactory.newInstance().newDocumentBuilder();
      builder.setEntityResolver(new ClasspathEntityResolver());
      Document doc=builder.parse(source);
      Element root=doc.getDocumentElement();
      return root;
    }
    catch (Exception e)
    {
      _logger.error("Parsing error",e);
    }
    return null;
  }
}
