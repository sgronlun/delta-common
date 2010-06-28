package delta.common.utils.variables;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import delta.common.utils.types.utils.TypesLoggers;

/**
 * Registry for variables.
 * @author DAM
 */
public final class VariablesRegistry
{
  private static final Logger _logger=TypesLoggers.getTypesLogger();

  private static VariablesRegistry _instance;

  private Map<String,Variable> _variables;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static VariablesRegistry getInstance()
  {
    synchronized (VariablesRegistry.class)
    {
      if (_instance==null)
      {
        _instance=new VariablesRegistry();
      }
      return _instance;
    }
  }

  /**
   * Private constructor.
   */
  private VariablesRegistry()
  {
    _variables=new HashMap<String,Variable>();
  }

  /**
   * Register a new variable.
   * @param variable Variable to register.
   * @return <code>true</code> upon successfull registration, <code>false</code> otherwise.
   */
  public boolean registerVariable(Variable variable)
  {
    String key=variable.getName();
    Variable old=_variables.get(key);
    if (old!=null)
    {
      _logger.warn("Variable ["+key+"] already defined.");
      return false;
    }
    _variables.put(key,variable);
    return true;
  }

  /**
   * Get a variable by name.
   * @param name Variable name.
   * @return A variable or <code>null</code> if not found.
   */
  public Variable getVariableByName(String name)
  {
    return _variables.get(name);
  }
}
