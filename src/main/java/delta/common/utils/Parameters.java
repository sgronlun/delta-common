package delta.common.utils;

import java.util.HashMap;

public class Parameters implements ParametersNode
{
  private Parameters _parent;
  private HashMap<String,Object> _infos;

  public Parameters()
  {
    this(null);
  }

  public Parameters(Parameters parentParameters)
  {
    _parent=parentParameters;
    _infos=new HashMap<String,Object>();
  }

  public int getIntParameter(String name, int defaultValue)
  {
    return ParameterFinder.getIntParameter(this,name,defaultValue);
  }

  public void putIntParameter(String name, int value)
  {
    _infos.put(name,Integer.valueOf(value));
  }

  public long getLongParameter(String name, long defaultValue)
  {
    return ParameterFinder.getLongParameter(this,name,defaultValue);
  }

  public void putLongParameter(String name, long value)
  {
    _infos.put(name,Long.valueOf(value));
  }

  public String getStringParameter(String name, String defaultValue)
  {
    return ParameterFinder.getStringParameter(this,name,defaultValue);
  }

  public void putStringParameter(String name, String value)
  {
    _infos.put(name,value);
  }

  public Object getParameter(String name, boolean useParent)
  {
    Object ret=_infos.get(name);
    if ((useParent) && (ret==null) && (_parent!=null))
      ret=_parent.getParameter(name,true);
    return ret;
  }

  public void putParameter(String name, Object value)
  {
    _infos.put(name,value);
  }

  public ParametersNode getParent()
  {
    return _parent;
  }
}
