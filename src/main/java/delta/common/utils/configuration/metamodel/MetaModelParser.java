package delta.common.utils.configuration.metamodel;

import java.net.URL;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import delta.common.utils.BooleanTools;
import delta.common.utils.traces.UtilsLoggers;

/**
 * Parser for the configuration file metamodel.
 * @author DAM
 */
public class MetaModelParser
{
  private static final Logger _logger=UtilsLoggers.getCfgLogger();

  private ConfigurationModel _model;

  /**
   * Simple constructor.
   */
  public MetaModelParser()
  {
    _model=null;
    parseXML();
  }

  /**
   * Get the client-editable configuration model.
   * @return the client-editable configuration model.
   */
  public ConfigurationModel getModel()
  {
    return _model;
  }

  /**
   * Parse the XML file.
   */
  private void parseXML()
  {
    try
    {
      ConfigurationModel model_l=new ConfigurationModel();

      ClassLoader classLoader=getClass().getClassLoader();
      URL u=classLoader.getResource(getConfig());
      //URL u=ClassLoader.getSystemClassLoader().getResource(getConfig());
      String uri=u.toURI().toString();
      Document doc_l=DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(uri);
      Element root_l=doc_l.getDocumentElement();

      // Parse type tags
      parseTypes(root_l,model_l);
      // Parse section tags
      parseSections(root_l,model_l);

      _model=model_l;
      doc_l = null;
    }
    catch (Exception e_p)
    {
      _logger.error("",e_p);
    }
  }

  /**
   * Parse type tags.
   * @param root Root node.
   * @param model Model to store data to.
   * @throws Exception
   */
  private void parseTypes(Element root, ConfigurationModel model) throws Exception
  {
    // TAGs
    final String TYPE_TAG="type";
    final String NAME_TAG="name";
    final String TYPE_HANDLER_TAG="typeHandler";
    final String PARAMETER_TAG="parameter";
    final String MANDATORY_TAG="mandatory";
    final String DEFAULT_VALUE_TAG="defaultValue";

    NodeList nlTypes_l=root.getElementsByTagName(TYPE_TAG);
    int nbTypes_l=nlTypes_l.getLength();
    for(int i=0;i<nbTypes_l;i++)
    {
      Node nType_l=nlTypes_l.item(i);
      if (nType_l.getNodeType()==Node.ELEMENT_NODE)
      {
        Element eType_l=(Element)nType_l;
        NamedNodeMap attributes_l=eType_l.getAttributes();
        String name_l=attributes_l.getNamedItem(NAME_TAG).getNodeValue();
        String typeHandler_l=attributes_l.getNamedItem(TYPE_HANDLER_TAG).getNodeValue();
        TypeModel typeModel_l=new TypeModel(name_l,typeHandler_l);

        // Parse parameters tags
        NodeList nlParameters_l=eType_l.getElementsByTagName(PARAMETER_TAG);
        int nbParameters_l=nlParameters_l.getLength();
        for(int j=0;j<nbParameters_l;j++)
        {
          Node nParameter_l=nlParameters_l.item(j);
          if (nParameter_l.getNodeType()==Node.ELEMENT_NODE)
          {
            NamedNodeMap paramAttr_l=nParameter_l.getAttributes();

            String attrName_l=paramAttr_l.getNamedItem(NAME_TAG).getNodeValue();

            String mandatory_l=paramAttr_l.getNamedItem(MANDATORY_TAG).getNodeValue();
            boolean isMandatory_l=BooleanTools.parseBoolean(mandatory_l,false);

            String defaultValue_l=null;
            Node defaultValueNode_l=paramAttr_l.getNamedItem(DEFAULT_VALUE_TAG);
            if (defaultValueNode_l!=null) defaultValue_l=defaultValueNode_l.getNodeValue();

            typeModel_l.addParameter(attrName_l,isMandatory_l);
            if (defaultValue_l!=null) typeModel_l.defineDefaultValueForParameter(attrName_l,defaultValue_l);
          }
        }
        model.addType(typeModel_l);
      }
    }
  }

  /**
   * Parse section tags.
   * @param root Root node.
   * @param model Model to store data to.
   * @throws Exception
   */
  private void parseSections(Element root, ConfigurationModel model) throws Exception
  {
    // TAGs
    final String TYPE_TAG="type";
    final String NAME_TAG="name";
    final String VARIABLE_TAG="variable";
    final String TOOLTIP_TAG="tooltip";
    final String PARAMETER_TAG="parameter";
    final String VALUE_TAG="value";
    final String DEFAULT_VALUE_TAG="defaultValue";
    NodeList nlSections_l=root.getElementsByTagName("section");
    int nbSections_l=nlSections_l.getLength();
    for(int i=0;i<nbSections_l;i++)
    {
      Node nSection_l=nlSections_l.item(i);
      if (nSection_l.getNodeType()==Node.ELEMENT_NODE)
      {
        Element eType_l=(Element)nSection_l;
        NamedNodeMap attributes_l=eType_l.getAttributes();
        String sectionName_l=attributes_l.getNamedItem(NAME_TAG).getNodeValue();

        SectionModel sectionModel_l=new SectionModel(sectionName_l);

        // Parse variable tags
        NodeList nlVariables_l=eType_l.getElementsByTagName(VARIABLE_TAG);
        int nbVariables_l=nlVariables_l.getLength();
        for(int j=0;j<nbVariables_l;j++)
        {
          Node nVariable_l=nlVariables_l.item(j);
          if (nVariable_l.getNodeType()==Node.ELEMENT_NODE)
          {
            NamedNodeMap paramAttr_l=nVariable_l.getAttributes();

            String name_l=paramAttr_l.getNamedItem(NAME_TAG).getNodeValue();
            String type_l=paramAttr_l.getNamedItem(TYPE_TAG).getNodeValue();

            String defaultValue_l=null;
            {
              Node defaultValueNode_l=paramAttr_l.getNamedItem(DEFAULT_VALUE_TAG);
              if (defaultValueNode_l!=null)
              {
                defaultValue_l=defaultValueNode_l.getNodeValue();
              }
            }
            TypeModel typeModel_l=model.getTypeModel(type_l);
            if (typeModel_l==null)
            {
              _logger.error("Unknown type specification for variable ["+sectionName_l+"]/["+name_l+"] : ["+type_l+"]");
              typeModel_l=model.getDefaultTypeModel();
            }

            VariableModel variableModel_l=new VariableModel(name_l,defaultValue_l,typeModel_l);

            // DAM - 09/03/2005 - Added tooltip management
            String tooltip_l=null;
            Node tooltipNode_l=paramAttr_l.getNamedItem(TOOLTIP_TAG);
            if (tooltipNode_l!=null) tooltip_l=tooltipNode_l.getNodeValue();
            variableModel_l.setTooltip(tooltip_l);
            // END DAM

            Element eVariable_l=(Element)nVariable_l;
            NodeList nlParameters_l=eVariable_l.getElementsByTagName(PARAMETER_TAG);
            int nbParameters_l=nlParameters_l.getLength();
            for(int k=0;k<nbParameters_l;k++)
            {
              Node nParameter_l=nlParameters_l.item(k);
              if (nParameter_l.getNodeType()==Node.ELEMENT_NODE)
              {
                Element eParameter_l=(Element)nParameter_l;
                NamedNodeMap paramAttributes_l=eParameter_l.getAttributes();
                String paramName_l=paramAttributes_l.getNamedItem(NAME_TAG).getNodeValue();
                String paramValue_l=paramAttributes_l.getNamedItem(VALUE_TAG).getNodeValue();
                variableModel_l.addTypeParameter(paramName_l,paramValue_l);
              }
            }

            variableModel_l.finalizeTypeParameters();
            sectionModel_l.addVariable(variableModel_l);
          }
        }
        model.addSection(sectionModel_l);
      }
    }
  }

  /**
   * Get the configuration file path (config.xml in this package).
   * @return the configuration file path.
   */
  private String getConfig()
  {
    String dir=MetaModelParser.class.getPackage().getName().replace('.','/');
    return dir+"/config.xml";
  }
}
