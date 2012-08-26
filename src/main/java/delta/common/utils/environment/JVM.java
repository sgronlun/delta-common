package delta.common.utils.environment;

/**
 * Represents the Java Virtual Machine we're presently running in.
 * @author DAM
 */
public final class JVM
{
  private static final String JAVA_VERSION="java.version";

  private static JVM _localJVM=null;

  private String _javaVersion;

  /**
   * Private constructor.
   * Builds an instance that represents the local JVM (the JVM that runs this class).
   */
  private JVM()
  {
    _javaVersion=System.getProperty(JAVA_VERSION);
  }

  /**
   * Get the supported Java version.
   * @return the supported Java version.
   */
  public String getJavaVersion()
  {
    return _javaVersion;
  }

  /**
   * Get the OS that runs this process.
   * @return the OS that runs this process.
   */
  public static JVM getLocalJVM()
  {
    if (_localJVM==null)
    {
      _localJVM=new JVM();
    }
    return _localJVM;
  }

  /*
  java.vendor   Java Runtime Environment vendor
  java.vendor.url   Java vendor URL
  java.home   Java installation directory
  java.vm.specification.version   Java Virtual Machine specification version
  java.vm.specification.vendor  Java Virtual Machine specification vendor
  java.vm.specification.name  Java Virtual Machine specification name
  java.vm.version   Java Virtual Machine implementation version
  java.vm.vendor  Java Virtual Machine implementation vendor
  java.vm.name  Java Virtual Machine implementation name
  java.specification.version  Java Runtime Environment specification version
  java.specification.vendor   Java Runtime Environment specification vendor
  java.specification.name   Java Runtime Environment specification name
  java.class.version  Java class format version number
  java.class.path   Java class path
  java.library.path   List of paths to search when loading libraries
  java.compiler   Name of JIT compiler to use
  java.ext.dirs   Path of extension directory or directories
*/
}
