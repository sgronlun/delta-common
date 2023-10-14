package delta.common.utils.variables;

import org.apache.log4j.Logger;

/**
 * Resolves string formats that contain variable parts.
 * @author DAM
 */
public class VariablesResolver
{
  private static final Logger LOGGER=Logger.getLogger(VariablesResolver.class);

  private static final String OPEN_VARIABLE="${";
  private static final String END_VARIABLE="}";

  private VariableValueProvider _provider;

  /**
   * Constructor.
   * @param provider Provider for variable values.
   */
  public VariablesResolver(VariableValueProvider provider)
  {
    _provider=provider;
  }

  /**
   * Render a string.
   * @param format String format.
   * @return A displayable string.
   */
  public String render(String format)
  {
    try
    {
      StringBuilder sb=new StringBuilder();
      int index=0;
      while(true)
      {
        int variableStartIndex=format.indexOf(OPEN_VARIABLE,index);
        if (variableStartIndex==-1)
        {
          sb.append(format,index,format.length());
          break;
        }
        sb.append(format,index,variableStartIndex);
        int variableEndIndex=format.indexOf(END_VARIABLE,variableStartIndex+OPEN_VARIABLE.length());
        String variableName;
        if (variableEndIndex!=-1)
        {
          variableName=format.substring(variableStartIndex+OPEN_VARIABLE.length(),variableEndIndex);
          index=variableEndIndex+END_VARIABLE.length();
        }
        else
        {
          // Assume the end of the string!
          variableName=format.substring(variableStartIndex+OPEN_VARIABLE.length());
        }
        renderVariable(sb,variableName);
        if (variableEndIndex==-1)
        {
          break;
        }
      }
      return sb.toString();
    }
    catch(Exception e)
    {
      LOGGER.warn("Caught exception when rendering string: "+format,e);
    }
    return "?";
  }

  /**
   * Render a variable part.
   * @param sb Output.
   * @param variableName Variable name.
   */
  private void renderVariable(StringBuilder sb, String variableName)
  {
    String value=_provider.getVariable(variableName);
    if (value!=null)
    {
      sb.append(value);
    }
  }
}
