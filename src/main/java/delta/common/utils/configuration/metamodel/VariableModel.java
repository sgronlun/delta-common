package delta.common.utils.configuration.metamodel;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * Represents the model for a <tt>Configuration</tt>'s variable.
 * @author DAM
 */
public class VariableModel
{
  private static final Logger _logger=UtilsLoggers.getCfgLogger();

  private String _name;
  private String _defaultValue;
  private String _tooltip;
  private TypeModel _typeModel;
  private HashMap<String,String> _typeParameters;

  /**
   * Complete constructor.
   * @param name_p Name.
   * @param defaultValue_p Default value.
   * @param typeModel_p Type model.
   */
  public VariableModel(String name_p, String defaultValue_p, TypeModel typeModel_p)
  {
    _name=name_p;
    _defaultValue=defaultValue_p;
    _typeModel=typeModel_p;
    _typeParameters=new HashMap<String,String>();
  }

  void addTypeParameter(String paramName_p, String paramValue_p)
  {
    _typeParameters.put(paramName_p,paramValue_p);
  }

  /**
   * Get the value for a parameter of the associated type.
   * @param paramName_p Parameter's name.
   * @return the parameter's value or <code>null</code> if it has no value.
   */
  public String getTypeParameter(String paramName_p)
  {
    return _typeParameters.get(paramName_p);
  }

  /**
   * Get the variable's name.
   * @return the variable's name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the default value for this variable.
   * @return the default value for this variable.
   */
  public String getDefaultValue()
  {
    return _defaultValue;
  }

  /**
   * Get the type associated with this variable.
   * @return the type associated with this variable.
   */
  public TypeModel getTypeModel()
  {
    return _typeModel;
  }

  /**
   * Associate a tooltip text with this variable.
   * @param tooltip_p Tooltip text to use.
   */
  public void setTooltip(String tooltip_p)
  {
    _tooltip=tooltip_p;
  }

  /**
   * Get the tooltip text associated with this variable.
   * @return a tooltip <tt>String</tt> or <code>null</code> if no tooptip
   * text defined.
   */
  public String getTooltip()
  {
    return _tooltip;
  }

  /**
   * Finalize type parameters : completes the type parameters map with default
   * value and check if all mandatory type parameters do have a value.
   * @return <code>true</code> if the associated type was successfully
   * parametered, or <code>false</code> if not.
   */
  boolean finalizeTypeParameters()
  {
    boolean return_l=true;
    _typeModel.completeWithDefaultValues(_typeParameters);
    String attrName_l=_typeModel.checkMandatoryParameters(_typeParameters);
    if (attrName_l!=null)
    {
      _logger.error("Missing mandatory parameter ["+attrName_l+"] for variable ["+_name+"] (type=["+_typeModel.getName()+"]) !");
      return_l=false;
    }
    return return_l;
  }

  /**
   * Pretty output for this object's content.
   * @param out Output stream.
   * @param nbSpaces Number of spaces at the beginning of each line.
   */
  public void prettyPrint(PrintStream out, int nbSpaces)
  {
    for(int i=0;i<nbSpaces;i++)
    {
      out.print(' ');
    }
    out.println("Variable ["+_name+"] Default value ["+_defaultValue+"], Tooltip ["+_tooltip+"]");
    for(int i=0;i<nbSpaces;i++)
    {
      out.print(' ');
    }
    out.println("Type ["+_typeModel.getName()+"]");
    for(Iterator<?> it_l=_typeParameters.entrySet().iterator();it_l.hasNext();)
    {
      Map.Entry entry_l=(Map.Entry)(it_l.next());
      String paramName_l=(String)(entry_l.getKey());
      String paramValue_l=(String)(entry_l.getValue());
      for(int i=0;i<nbSpaces+3;i++)
      {
        out.print(' ');
      }
      out.println("Type parameter ["+paramName_l+"]=["+paramValue_l+"]");
    }
  }
}
