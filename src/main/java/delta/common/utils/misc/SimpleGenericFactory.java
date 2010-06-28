package delta.common.utils.misc;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * Typed factory.
 * @author DAM
 * @param <T> Base type of objects created by this factory.
 */
public class SimpleGenericFactory<T>
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  /**
   * Class used to build new instances.
   */
  private Class<? extends T> _realClass;

  /**
   * Constructor with a class name.
   * @param abstractClass class that the real class designated by <code>realClassName</code> should implement/extend.
   * @param realClassName fully qualified name of the class to use as the <code>_realClass</code>.
   */
  @SuppressWarnings("unchecked")
  public SimpleGenericFactory(Class<T> abstractClass, String realClassName)
  {
    if (realClassName!=null)
    {
      Class<?> c=null;
      try
      {
        c=Class.forName(realClassName);
        if (abstractClass.isAssignableFrom(c))
        {
          _realClass=(Class<? extends T>)c;
        }
      }
      catch(Exception e)
      {
        _logger.error("Cannot use class : "+realClassName,e);
      }
    }
  }

  /**
   * Build a new instance.
   * If a <code>_realClass</code> has been defined, creates a new instance of this class. This instance implements/extends <code>T</code>.
   * <p>
   * Otherwise, returns <code>null</code>.
   * @return newly created instance or <code>null</code>.
   */
  public T build()
  {
    if (_realClass!=null)
    {
      try
      {
        return _realClass.newInstance();
      }
      catch(Exception e)
      {
        _logger.error("Cannot build object",e);
      }
    }
    return null;
  }
}
