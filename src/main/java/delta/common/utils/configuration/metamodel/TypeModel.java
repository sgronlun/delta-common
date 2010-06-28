package delta.common.utils.configuration.metamodel;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Contains all the data that modelizes a data type for the configuration file.
 * @author DAM
 */
public class TypeModel
{
  // Name of the type
  private String _name;
  // Class name of type handler
  private String _typeHandler;

  // Name of mandatory parameters of the type (String parameter names)
  private ArrayList<String> _mandatoryParameters;
  // Name of parameters of the type (String parameter names)
  private ArrayList<String> _parameters;
  // Default values for parameters of the type (String parameter name -> String default value)
  private HashMap<String, String> _defaultParameterValues;

  /**
   * Complete constructor.
   * @param name Name.
   * @param typeHandler Type handler.
   */
  public TypeModel(String name, String typeHandler)
  {
    _name=name;
    _typeHandler=typeHandler;
    _mandatoryParameters=new ArrayList<String>();
    _parameters=new ArrayList<String>();
    _defaultParameterValues=new HashMap<String, String>();
  }

  /**
   * Get the type name.
   * @return the type name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the name of the type handler's class.
   * @return the name of the type handler's class.
   */
  public String getTypeHandler()
  {
    return _typeHandler;
  }

  /**
   * Define a new parameter by its name.
   * @param parameterName name of the parameter to add.
   * @param mandatory Mandatory or not.
   */
  public void addParameter(String parameterName, boolean mandatory)
  {
    if (!_parameters.contains(parameterName))
    {
      _parameters.add(parameterName);
    }
    if (mandatory)
    {
      if (!_mandatoryParameters.contains(parameterName))
      {
        _mandatoryParameters.add(parameterName);
      }
    }
  }

  /**
   * Checks the presence of all mandatory parameters in the given
   * parameters list (which must have a key for each mandatory parameter).
   * @param parametersList Parameters list to check.
   * @return null if OK, the name of the first mandatory parameter name missing.
   */
  public String checkMandatoryParameters(HashMap<String, String> parametersList)
  {
    String ret=null;

    String name;
    for(Iterator<String> it=_mandatoryParameters.iterator();it.hasNext();)
    {
      name=it.next();
      if (parametersList.get(name)==null)
      {
        ret=name;
        break;
      }
    }

    return ret;
  }

  /**
   * Completes the given variable/value map with default values, so that
   * this map contains an entry for each mandatory variable.
   * @param parametersList map to complete.
   */
  public void completeWithDefaultValues(HashMap<String, String> parametersList)
  {
    String name;
    String defaultValue;
    for(Iterator<String> it=_mandatoryParameters.iterator();it.hasNext();)
    {
      name=it.next();
      if (parametersList.get(name)==null)
      {
        defaultValue=getDefaultValueForParameter(name);
        if (defaultValue!=null)
        {
          parametersList.put(name,defaultValue);
        }
      }
    }
  }

  /**
   * Define a new parameter default value.
   * @param parameterName Name of the parameter to define.
   * @param value Default value to use.
   */
  public void defineDefaultValueForParameter(String parameterName, String value)
  {
    _defaultParameterValues.put(parameterName,value);
  }

  /**
   * Returns the default value for the given parameter or null if none.
   * @param parameterName_p Targeted parameter.
   * @return Default value or null.
   */
  public String getDefaultValueForParameter(String parameterName_p)
  {
    return (_defaultParameterValues.get(parameterName_p));
  }

  /**
   * Pretty output for this object's content.
   * @param out Output stream.
   * @param nbSpaces Number of spaces at the beginning of each line.
   */
  public void prettyPrint(PrintStream out, int nbSpaces)
  {
    for(int i=0;i<nbSpaces;i++) out.print(' ');
    out.println("Type ["+_name+"]. Handler ["+_typeHandler+"]");
    for(Iterator<String> it_l=_parameters.iterator();it_l.hasNext();)
    {
      String parameter_l=(it_l.next());
      for(int i=0;i<nbSpaces+3;i++) out.print(' ');
      out.print("Parameter ["+parameter_l+"] ");
      if (_mandatoryParameters.contains(parameter_l)) out.print(" (mandatory)");
      String defaultValue_l=getDefaultValueForParameter(parameter_l);
      if (defaultValue_l!=null) out.print(" (default=["+defaultValue_l+"])");
      out.println("");
    }
  }
}
