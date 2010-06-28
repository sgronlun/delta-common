package delta.common.utils;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

public class ParameterFinder
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  public static int getIntParameter(ParametersNode node, String name, int defaultValue)
  {
    int ret=defaultValue;
    Object pValue=node.getParameter(name,true);
    if (pValue!=null)
    {
      if (pValue instanceof Integer)
      {
        ret=((Integer)pValue).intValue();
      }
      else if (pValue instanceof String)
      {
        try
        {
          ret=Integer.parseInt((String)pValue);
        }
        catch(NumberFormatException nfe)
        {
          _logger.error("",nfe);
        }
      }
      else
      {
        _logger.warn("Type not supported for int conversion : "+pValue.getClass().getName());
      }
    }
    return ret;
  }

  public static long getLongParameter(ParametersNode node, String name, long defaultValue)
  {
    return getLongParameter(node,name,defaultValue,true);
  }

  public static long getLongParameter(ParametersNode node, String name, long defaultValue, boolean useParent)
  {
    long ret=defaultValue;
    Object pValue=node.getParameter(name,useParent);
    if (pValue!=null)
    {
      if (pValue instanceof Long)
      {
        ret=((Long)pValue).longValue();
      }
      else if (pValue instanceof String)
      {
        try
        {
          ret=Long.parseLong((String)pValue);
        }
        catch(NumberFormatException nfe)
        {
          _logger.error("",nfe);
        }
      }
      else
      {
        _logger.warn("Type not supported for long conversion : "+pValue.getClass().getName());
      }
    }
    return ret;
  }

  public static boolean getBooleanParameter(ParametersNode node, String name, boolean defaultValue)
  {
    boolean ret=defaultValue;
    Object pValue=node.getParameter(name,true);
    if (pValue!=null)
    {
      if (pValue instanceof Boolean)
      {
        ret=((Boolean)pValue).booleanValue();
      }
      else if (pValue instanceof String)
      {
        String value=(String)pValue;
        if (value.equalsIgnoreCase("true"))
          ret=true;
        else if (value.equalsIgnoreCase("false"))
          ret=false;
        if (value.equals("1"))
          ret=true;
        else if (value.equals("0"))
          ret=false;
      }
      else
      {
        _logger.warn("Type not supported for boolean conversion : "+pValue.getClass().getName());
      }
    }
    return ret;
  }

  public static String getStringParameter(ParametersNode node, String name, String defaultValue)
  {
    String ret=defaultValue;
    Object pValue=node.getParameter(name,true);
    if (pValue!=null)
    {
      if (pValue instanceof String)
        ret=(String)pValue;
    }
    return ret;
  }

  public static Object getParameter(ParametersNode node, String name, boolean useParent)
  {
    return node.getParameter(name,useParent);
  }
}
