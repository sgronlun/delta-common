package delta.common.utils.diagnostics;

class MyClassLoader extends ClassLoader
{
  public MyClassLoader()
  {
    super();
  }
}

public class LoadedClassesStatistics
{
  public LoadedClassesStatistics()
  {
    ClassLoader scl=ClassLoader.getSystemClassLoader();
    System.out.println("Class loader : "+scl);
    Package[] p=Package.getPackages();
    for(int i=0;i<p.length;i++)
    {
      System.out.println("Package ["+p[i].getName()+"]");
    }
    Class<?>[] c=getClass().getDeclaredClasses();
    for(int i=0;i<c.length;i++)
    {
      System.out.println("Class ["+c[i].getName()+"]");
    }
    Class<?> superClass=getClass().getSuperclass();
    System.out.println("SuperClass ["+superClass+"]");
  }

  public static void main(String[] args)
  {
    new LoadedClassesStatistics();
  }
}
