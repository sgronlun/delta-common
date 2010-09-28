package delta.common.utils;

import java.util.HashMap;

/**
 * Simple parameters node that uses a hash map.
 * @author DAM
 */
public class Parameters implements ParametersNode
{
  private ParametersNode _parent;
  private HashMap<String,Object> _infos;

  /**
   * Default constructor.
   */
  public Parameters()
  {
    this(null);
  }

  /**
   * Full constructor.
   * @param parentParameters Parent parameters node.
   */
  public Parameters(ParametersNode parentParameters)
  {
    _parent=parentParameters;
    _infos=new HashMap<String,Object>();
  }

  /**
   * Store the value of an integer parameter.
   * @param name Parameter name.
   * @param value Value to store.
   */
  public void putIntParameter(String name, int value)
  {
    _infos.put(name,Integer.valueOf(value));
  }

  /**
   * Store the value of a long parameter.
   * @param name Parameter name.
   * @param value Value to store.
   */
  public void putLongParameter(String name, long value)
  {
    _infos.put(name,Long.valueOf(value));
  }

  /**
   * Store the value of a string parameter.
   * @param name Parameter name.
   * @param value Value to store.
   */
  public void putStringParameter(String name, String value)
  {
    _infos.put(name,value);
  }

  public Object getParameter(String name)
  {
    return _infos.get(name);
  }

  /**
   * Store the value of a parameter.
   * @param name Parameter name.
   * @param value Value to store.
   */
  public void putParameter(String name, Object value)
  {
    _infos.put(name,value);
  }

  public ParametersNode getParent()
  {
    return _parent;
  }
}
