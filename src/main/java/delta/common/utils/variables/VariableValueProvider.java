package delta.common.utils.variables;

/**
 * Provides values for variables.
 * @author DAM
 */
public interface VariableValueProvider
{
  /**
   * Get the value for the targeted variable.
   * @param variableName Variable name.
   * @return A value or <code>null</code> if not found.
   */
  String getVariable(String variableName);
}
