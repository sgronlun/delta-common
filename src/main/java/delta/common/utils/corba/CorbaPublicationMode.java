package delta.common.utils.corba;

import java.util.HashMap;

/**
 * Represents a CORBA publication mode (light-weight, shared instances).
 * @author DAM
 */
public class CorbaPublicationMode
{
  private static final HashMap<String,CorbaPublicationMode> _map=new HashMap<String,CorbaPublicationMode>();

  private String _mode;

  /**
   * Constant for the "naming service" publication mode.
   */
  public static final CorbaPublicationMode NAMING_SERVICE=new CorbaPublicationMode("NAMING_SERVICE");

  /**
   * Constant for the "corba loc" publication mode.
   */
  public static final CorbaPublicationMode CORBA_LOC=new CorbaPublicationMode("CORBA_LOC");

  /**
   * Constant for the "local IOR file" publication mode.
   */
  public static final CorbaPublicationMode IOR_FILE=new CorbaPublicationMode("IOR_FILE");

  /**
   * Constant for the "none" publication mode.
   * In this mode, a CORBA object is not retrieved directly, but through another CORBA object.
   */
  public static final CorbaPublicationMode NONE=new CorbaPublicationMode("NONE");

  private CorbaPublicationMode(String mode)
  {
    _mode=mode;
    _map.put(mode,this);
  }

  /**
   * Get the mode key (identifier).
   * @return the mode key (identifier).
   */
  public String getMode()
  {
    return _mode;
  }

  /**
   * Get a CORBA publication mode by name.
   * @param name Name of targeted mode.
   * @return A CORBA publication mode or <code>null</code>.
   */
  public static CorbaPublicationMode getByName(String name)
  {
    CorbaPublicationMode mode=_map.get(name);
    return mode;
  }

  /**
   * Get a readable string that represents this object.
   * @return a readable string that represents this object.
   */
  @Override
  public String toString()
  {
    return _mode;
  }
}
