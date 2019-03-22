package delta.common.utils.misc;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * Typed factory.
 * @author DAM
 * @param <T> Base type of objects created by this factory.
 */
public class GenericFactory<T>
{
  private static final Logger LOGGER=Logger.getLogger(GenericFactory.class);

  /**
   * Map of product names to constructors.
   */
  private HashMap<String,Constructor<? extends T>> _constructorsMap;

  /**
   * Base class for all products.
   */
  private Class<? extends T> _productBaseClass;
  private Class<?>[] _constructorParameters;

  /**
   * Constructor with a class name.
   * @param abstractClass class that all the managed products should implement/extend.
   */
  public GenericFactory(Class<T> abstractClass)
  {
    this(abstractClass,new Class<?>[0]);
  }

  /**
   * Constructor with a class name.
   * @param abstractClass class that all the managed products should implement/extend.
   * @param constructorParameters type of parameters used by constructor.
   */
  public GenericFactory(Class<T> abstractClass, Class<?> ... constructorParameters)
  {
    _productBaseClass=abstractClass;
    _constructorsMap=new HashMap<String,Constructor<? extends T>>();
    _constructorParameters=constructorParameters.clone();
  }

  /**
   * Register a product.
   * @param productName
   * @param productClassName fully qualified name of the class to use when building <code>productName</code>
   * products.
   * @return <code>true</code> if registration succeeded, <code>false</code> otherwise.
   */
  @SuppressWarnings("unchecked")
  public boolean registerProduct(String productName, String productClassName)
  {
    boolean ret=false;
    if ((productName!=null) && (productName.length()>0))
    {
      Constructor<? extends T> oldConstructor=_constructorsMap.get(productName);
      if (oldConstructor==null)
      {
        if ((productClassName!=null) && (productClassName.length()>0))
        {
          Class<?> c=null;
          try
          {
            c=Class.forName(productClassName);
          }
          catch(Exception e)
          {
            LOGGER.error("Cannot find class : "+productClassName,e);
          }
          if (c!=null)
          {
            if (_productBaseClass.isAssignableFrom(c))
            {
              Class<? extends T> tClass=(Class<? extends T>)c;
              Constructor<? extends T> constructor=null;
              try
              {
                constructor=tClass.getConstructor(_constructorParameters);
              }
              catch(Exception e)
              {
                LOGGER.error("Cannot find constructor with appropriate parameters for class "+tClass,e);
              }
              if (constructor!=null)
              {
                _constructorsMap.put(productName,constructor);
              }
            }
            else
            {
              LOGGER.error("Cannot use class : "+productClassName+" that is not a subclass of "+_productBaseClass);
            }
          }
        }
        else
        {
          LOGGER.error("Product class name is null or empty !");
        }
      }
      else
      {
        LOGGER.error("Product ["+productName+"] already defined with product class ["+oldConstructor.getClass().getName()+"]");
      }
    }
    else
    {
      LOGGER.error("Product name is null or empty !");
    }
    return ret;
  }

  /**
   * Build a new product instance.
   * @param productName Name of product to build.
   * @param initArgs Constructor arguments.
   * @return newly created instance or <code>null</code>.
   */
  public T build(String productName, Object ... initArgs)
  {
    T ret=null;
    Constructor<? extends T> productConstructor=_constructorsMap.get(productName);
    if (productConstructor!=null)
    {
      try
      {
        ret=productConstructor.newInstance(initArgs);
      }
      catch(Exception e)
      {
        LOGGER.error("Cannot build product ["+productName+"]",e);
      }
    }
    else
    {
      LOGGER.error("Unknown product ["+productName+"]");
    }
    return ret;
  }
}
