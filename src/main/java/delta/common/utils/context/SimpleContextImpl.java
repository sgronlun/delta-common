package delta.common.utils.context;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple context implementation.
 * @author DAM
 */
public class SimpleContextImpl implements Context
{
  private Context _parentContext;
  private Map<String,Object> _values;

  /**
   * Constructor.
   */
  public SimpleContextImpl()
  {
    _values=new HashMap<String,Object>();
  }

  @Override
  public Context getParentContext()
  {
    return _parentContext;
  }

  /**
   * Set the parent context.
   * @param parentContext Parent context.
   */
  public void setParentContext(Context parentContext)
  {
    _parentContext=parentContext;
  }

  @Override
  public <T> T getValue(String key, Class<T> clazz)
  {
    Object value=_values.get(key);
    if ((value!=null) && (clazz.isAssignableFrom(value.getClass())))
    {
      return clazz.cast(value);
    }
    return null;
  }

  @Override
  public void setValue(String key, Object value)
  {
    _values.put(key,value);
  }

  @Override
  public void removeValue(String key)
  {
    _values.remove(key);
  }

  @Override
  public void clear()
  {
    _values.clear();
  }
}
