package delta.common.utils.environment;


/**
 * Represents a Java process.
 * @author DAM
 */
public final class JavaProcess
{
  /**
   * Default name for a Java Process.
   */
  private static final String DEFAULT_NAME="DefaultApplication";
  /**
   * Name of the system property that stores the name of this Java Process.
   */
  public static final String APP_NAME_PROPERTY="delta.application.name";

  private static JavaProcess _localJavaProcess=null;

  private String _name;

  /**
   * Private constructor.
   * Builds an instance that represents the local Java process (the Java software that runs on this JVM).
   */
  private JavaProcess()
  {
    _name=DEFAULT_NAME;
    String name=null;
    try
    {
      name=System.getProperty(APP_NAME_PROPERTY);
    }
    catch(Exception e)
    {
      // ignored
    }
    if (name!=null)
    {
      _name=name;
    }
  }

  /**
   * Get the name of this Java process.
   * @return the name of this Java process.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the local Java process.
   * @return the local Java process.
   */
  public static JavaProcess getLocalJavaProcess()
  {
    synchronized (JavaProcess.class)
    {
      if (_localJavaProcess==null)
      {
        _localJavaProcess=new JavaProcess();
      }
      return _localJavaProcess;
    }
  }
}
