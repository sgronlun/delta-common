package delta.common.utils.types.io.xml;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.misc.ClassResolver;
import delta.common.utils.types.Type;
import delta.common.utils.types.TypeClass;
import delta.common.utils.types.TypeClassParameter;
import delta.common.utils.xml.DOMParsingTools;

/**
 * Parser used to build <tt>TypeClass</tt> instances from XML trees.
 * @author DAM
 */
public class TypeClassXMLParser
{
  // Tags
  private static final String TYPE_CLASS_TAG="TYPE_CLASS";
  private static final String PARAM_TAG="PARAM";
  // Attributes
  private static final String JAVACLASS_ATTR="JAVACLASS";
  private static final String NAME_ATTR="NAME";
  private static final String TYPE_ATTR="TYPE";
  private static final String DEFAULT_ATTR="DEFAULT";
  private static final String MANDATORY_ATTR="MANDATORY";

  /**
   * Parse a type parameter from a XML node.
   * @param e Node to parse.
   * @return Parsed object.
   */
  public TypeClass parseTypeClassNode(Element e)
  {
    NamedNodeMap attrs=e.getAttributes();
    // Class name
    String className=DOMParsingTools.getStringAttribute(attrs,NAME_ATTR,"");
    // Java Class name
    String javaClassName=DOMParsingTools.getStringAttribute(attrs,JAVACLASS_ATTR,"");
    Class<? extends Type> javaTypeClass=new ClassResolver<Type>(Type.class).findClass(javaClassName);
    // Parameters
    List<TypeClassParameter> parameters=new ArrayList<TypeClassParameter>();
    List<Element> paramElements=DOMParsingTools.getChildTagsByName(e,PARAM_TAG);
    Element paramElement;
    TypeClassParameter parameter;
    for(Iterator<Element> it=paramElements.iterator();it.hasNext();)
    {
      paramElement=it.next();
      parameter=parseParamNode(paramElement);
      if (parameter!=null)
      {
        parameters.add(parameter);
      }
    }
    TypeClass typeClass=new TypeClass(className,javaTypeClass,parameters);
    return typeClass;
  }

  /**
   * Parse a type parameter from a XML node.
   * @param e Node to parse.
   * @return Parsed object.
   */
  private TypeClassParameter parseParamNode(Element e)
  {
    NamedNodeMap attrs=e.getAttributes();
    // Name
    String name=DOMParsingTools.getStringAttribute(attrs,NAME_ATTR,"");
    // Type
    String type=DOMParsingTools.getStringAttribute(attrs,TYPE_ATTR,"");
    // Default value
    String defaultValue=DOMParsingTools.getStringAttribute(attrs,DEFAULT_ATTR,null);
    // Mandatory
    boolean mandatory=DOMParsingTools.getBooleanAttribute(attrs,MANDATORY_ATTR,false);
    TypeClassParameter param=new TypeClassParameter(name,type,mandatory,defaultValue);
    return param;
  }

  /**
   * Parse a <code>TYPE</code> node to build type classes.
   * @param url URL of XML tree to parse.
   * @return A list of <tt>TypeClass</tt>es.
   */
  public List<TypeClass> parseBuildInClasses(URL url)
  {
    List<TypeClass> classes=new ArrayList<TypeClass>();
    Element root=DOMParsingTools.parse(url);
    List<Element> typeElements=DOMParsingTools.getChildTagsByName(root,TYPE_CLASS_TAG);
    Element typeElement;
    TypeClass typeClass;
    for(Iterator<Element> it=typeElements.iterator();it.hasNext();)
    {
      typeElement=it.next();
      typeClass=parseTypeClassNode(typeElement);
      if (typeClass!=null)
      {
        classes.add(typeClass);
      }
    }
    return classes;
  }
}
