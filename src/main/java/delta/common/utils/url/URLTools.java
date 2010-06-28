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
   * Build an URL from a resource name and a package name.
   * @param name Resource name.
   * @param packageName Name of package to use.
   * @return Found URL or <code>null</code> if not found.
   */
  public static URL getFromClassPath(String name, String packageName)
  {
    String resourceToFind=packageName;
    resourceToFind=resourceToFind.replace('.','/');
    resourceToFind=resourceToFind+"/"+name;
    return getFromClassPath(resourceToFind);
  }

  /**
   * Build an URL from a resource name and an object.
   * Resource is searched using the package of object's class as root.
   * @param name Resource name.
   * @param instance Object to use.
   * @return Found URL or <code>null</code> if not found.
   */
  public static URL getFromClassPath(String name, Object instance)
  {
    Package p=instance.getClass().getPackage();
    return getFromClassPath(name,p);
  }

  /**
   * Build an URL from a resource name and a package start point.
   * @param name Resource name.
   * @param startPoint Package that serves as a start point.
   * @return Found URL or <code>null</code> if not found.
   */
  public static URL getFromClassPath(String name, Package startPoint)
  {
    String resourceToFind=startPoint.getName();
    resourceToFind=resourceToFind.replace('.','/');
    resourceToFind=resourceToFind+"/"+name;
    return getFromClassPath(resourceToFind);
  }

  /**
   * Look for a resource in the CLASSPATH and return it as an URL.
   * @param name Resource to find.
   * @return Found URL or <code>null</code> if not found.
   */
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
