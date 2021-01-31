package delta.common.utils.url;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Tool methods related to URLs management.
 * @author DAM
 */
public abstract class URLTools
{
  /**
   * Build an URL from a resource name and an object.
   * Resource is searched using the package of object's class as root.
   * @param name Resource name.
   * @param instance Object to use.
   * @return Found URL or <code>null</code> if not found.
   */
  public static URL getFromClassPath(String name, Object instance)
  {
    Class<?> clazz=instance.getClass();
    return getFromClassPath(name,clazz);
  }

  /**
   * Build an URL from a resource name and a package start point.
   * @param name Resource name.
   * @param startPoint Package that serves as a start point.
   * @return Found URL or <code>null</code> if not found.
   * @deprecated Use version with class instead.
   */
  @Deprecated
  public static URL getFromClassPath(String name, Package startPoint)
  {
    return getFromClassPath(name,startPoint,null);
  }

  /**
   * Build an URL from a resource name and a class start point.
   * @param name Resource name.
   * @param clazz Class that serves as a start point.
   * @return Found URL or <code>null</code> if not found.
   */
  public static URL getFromClassPath(String name, Class<?> clazz)
  {
    Package p=clazz.getPackage();
    ClassLoader classloader=clazz.getClassLoader();
    return getFromClassPath(name,p,classloader);
  }

  /**
   * Build an URL from a resource name and a package start point.
   * @param name Resource name.
   * @param startPoint Package that serves as a start point.
   * @param classloader Class loader to use.
   * @return Found URL or <code>null</code> if not found.
   */
  private static URL getFromClassPath(String name, Package startPoint, ClassLoader classloader)
  {
    String resourceToFind=startPoint.getName();
    resourceToFind=resourceToFind.replace('.','/');
    resourceToFind=resourceToFind+"/"+name;
    if (classloader!=null)
    {
      return getFromClassPath(resourceToFind,classloader);
    }
    return getFromClassPath(resourceToFind);
  }

  /**
   * Look for a resource in the CLASSPATH and return it as an URL.
   * @param name Resource to find.
   * @param classloader Class loader to use.
   * @return Found URL or <code>null</code> if not found.
   */
  public static URL getFromClassPath(String name, ClassLoader classloader)
  {
    return classloader.getResource(name);
  }

  /**
   * Look for a resource in the CLASSPATH and return it as an URL.
   * @param name Resource to find.
   * @return Found URL or <code>null</code> if not found.
   * @deprecated Use version with class or instance: it is classloader aware.
   */
  @Deprecated
  public static URL getFromClassPath(String name)
  {
    ClassLoader loader=URLTools.class.getClassLoader();
    return loader.getResource(name);
  }

  /**
   * Build an URL for a local file.
   * @param file File to use.
   * @return A file URL.
   */
  public static URL buildFileURL(File file)
  {
    URL url=null;
    try
    {
      url=new URL("file",null,file.getPath());
    }
    catch(MalformedURLException e)
    {
      // Should not happen !
    }
    return url;
  }
}
