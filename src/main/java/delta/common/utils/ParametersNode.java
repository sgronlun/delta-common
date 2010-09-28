package delta.common.utils;

/**
 * Interface of a parameters node.
 * @author DAM
 */
public interface ParametersNode
{
  /**
   * Get the value of a parameter, designated by its name.
   * @param name Name of the targeted parameter.
   * @return A value or <code>null</code> if not found.
   */
  public Object getParameter(String name);
  
  /**
   * Get the parent node, if any.
   * @return The parent node, or <code>null</code> if there's no parent node.
   */
  public ParametersNode getParent();
}
