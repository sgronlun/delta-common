package delta.common.utils;

public interface ParametersNode
{
  public Object getParameter(String name, boolean useParent);
  public ParametersNode getParent();
}
