package delta.common.utils.types.io.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import delta.common.utils.types.Type;
import delta.common.utils.types.TypeClass;
import delta.common.utils.types.TypeClassParameter;
import delta.common.utils.types.TypeClassesRegistry;
import delta.common.utils.types.TypesRegistry;
import delta.common.utils.types.utils.TypesLoggers;
import delta.common.utils.xml.DOMParsingTools;

/**
 * Parser used to build <tt>Type</tt> instances from XML trees.
 * @author DAM
 */
public class TypeXMLParser
{
  private static final Logger _logger=TypesLoggers.getTypesLogger();

  // Tags
  private static final String PARAM_TAG="PARAM";
  // Attributes
  private static final String CLASS_ATTR="CLASS";
  private static final String NAME_ATTR="NAME";
  private static final String VALUE_ATTR="VALUE";

  /**
   * Parse a type parameter from a XML node.
   * @param e Node to parse.
   * @return Parsed object.
   */
  public Type parseTypeNode(Element e)
  {
    Type type=null;
    NamedNodeMap attrs=e.getAttributes();
    // Class name
    String className=DOMParsingTools.getStringAttribute(attrs,CLASS_ATTR,"");
    String typeName=DOMParsingTools.getStringAttribute(attrs,NAME_ATTR,"");
    TypeClassesRegistry typeClassRegistry=TypeClassesRegistry.getInstance();
    TypeClass typeClass=typeClassRegistry.getTypeClassByName(className);
    if (typeClass!=null)
    {
      type=typeClass.buildType(typeName);
      if (type!=null)
      {
        TypesRegistry registry=TypesRegistry.getInstance();
        // Parse nodes
        List<TypeClassParameter> parameters=typeClass.getParameters();
        Map<String,List<Element>> map=DOMParsingTools.indexChildTagsByAttrValue(e,PARAM_TAG,NAME_ATTR);
        TypeClassParameter parameter;
        String parameterName;
        String paramTypeName;
        Type paramType;
        List<Element> elements;
        List<String> strValues;
        for(Iterator<TypeClassParameter> itParams=parameters.iterator();itParams.hasNext();)
        {
          parameter=itParams.next();
          parameterName=parameter.getName();
          paramTypeName=parameter.getType();
          paramType=registry.getType(paramTypeName);
          elements=map.get(parameterName);

          // Build the list of values for the current parameter (as a string list)
          strValues=new ArrayList<String>();
          if (elements==null)
          {
            if (parameter.isMandatory())
            {
              _logger.warn("Missing parameter value for mandatory parameter ["+parameterName+"] on type ["+typeName+"]");
              String defaultValue=parameter.getDefaultValue();
              _logger.warn("Using default value ["+defaultValue+"]");
              strValues.add(defaultValue);
            }
          }
          else
          {
            Element childElement;
            NamedNodeMap paramAttrs;
            String paramValueStr;
            for(Iterator<Element> itElements=elements.iterator();itElements.hasNext();)
            {
              childElement=itElements.next();
              paramAttrs=childElement.getAttributes();
              paramValueStr=DOMParsingTools.getStringAttribute(paramAttrs,VALUE_ATTR,"");
              strValues.add(paramValueStr);
            }
          }

          // Set parameter values to the newly instanciated type
          {
            Object paramValue;
            String paramValueStr;
            for(Iterator<String> itValues=strValues.iterator();itValues.hasNext();)
            {
              paramValueStr=itValues.next();
              paramValue=paramType.parseFromString(paramValueStr);
              if (paramValue!=null)
              {
                type.setParamValue(parameterName,paramValue);
              }
            }
          }

          map.remove(parameterName);
        }

        // Warn for useless parameters
        {
          Set<String> unusedParameters=map.keySet();
          String unusedParameterName;
          for(Iterator<String> it=unusedParameters.iterator();it.hasNext();)
          {
            unusedParameterName=it.next();
            _logger.warn("Unused parameter ["+unusedParameterName+"] on type ["+typeName+"]");
          }
        }
      }
      else
      {
        _logger.warn("Cannot build an instance of type '"+typeName+"'");
      }
    }
    else
    {
      _logger.warn("Unknown class type '"+className+"'");
    }
    return type;
  }
}
