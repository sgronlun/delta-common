package delta.common.utils.environment;

import java.util.HashMap;

/**
 * Represents an OS type.
 * @author DAM
 */
public final class OSType
{
  private static final HashMap<String,OSType> _instancesMap=new HashMap<String,OSType>();

  private static final String UNKNOWN="Unknown";

  /**
   * Symbolic constant for the Windows operating system.
   */
  public static final OSType WINDOWS=new OSType("WINDOWS");
  /**
   * Symbolic constant for the Linux operating system.
   */
  public static final OSType LINUX=new OSType("Linux");
  /**
   * Symbolic constant for unknown operating systems.
   */
  public static final OSType UNKNOWN_OS=new OSType(null);

  private String _name;

  /**
   * Private constructor.
   * @param name OS name.
   */
  private OSType(String name)
  {
    if (name!=null)
    {
      _name=name;
      _instancesMap.put(name,this);
    }
    else
    {
      _name=UNKNOWN;
    }
  }

  /**
   * Get the OS name.
   * @return the OS name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get an OS type by name.
   * @param name Name of the OS type to get.
   * @return An OS type or the instance for the unknown OS type.
   */
  public static OSType getOSTypeByName(String name)
  {
    OSType type=_instancesMap.get(name);
    if (type==null)
    {
      type=UNKNOWN_OS;
    }
    return type;
  }
}
