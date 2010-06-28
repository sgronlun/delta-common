package delta.common.utils.properties.io.xml;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import delta.common.utils.BooleanTools;
import delta.common.utils.properties.SymbolicPropertiesRegistry;
import delta.common.utils.properties.SymbolicPropertiesSet;
import delta.common.utils.properties.SymbolicProperty;

/**
 * Parser for symbolic properties stored in XML.
 * @author DAM
 */
public class PropertiesXMLParser
{
  private SymbolicPropertiesRegistry _registry;

  /**
   * Constructor.
   * @param registry Registry for properties.
   */
  public PropertiesXMLParser(SymbolicPropertiesRegistry registry)
  {
    _registry=registry;
  }

  /**
   * Build a set of properties from a properties node.
   * @param propertiesNode Properties node.
   * @return A set of properties.
   */
  public SymbolicPropertiesSet parseFieldProperties(Node propertiesNode)
  {
    SymbolicPropertiesSet properties=null;
    NamedNodeMap attrs=propertiesNode.getAttributes();
    int nbItems=attrs.getLength();
    Node tmp;
    String attrName;
    String attrValue;
    SymbolicProperty p;
    boolean hasProperty;
    String[] propertiesToSet=new String[nbItems];
    int index=0;
    for(int i=0;i<nbItems;i++)
    {
      tmp=attrs.item(i);
      attrName=tmp.getNodeName();
      p=_registry.getProperty(attrName);
      if (p!=null)
      {
        attrValue=tmp.getNodeValue();
        hasProperty=BooleanTools.parseBoolean(attrValue,false);
        if (hasProperty)
        {
          propertiesToSet[index]=attrName;
          index++;
        }
      }
    }
    properties=_registry.getPropertiesSet(propertiesToSet);
    return properties;
  }
}
