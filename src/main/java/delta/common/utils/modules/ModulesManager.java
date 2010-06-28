package delta.common.utils.modules;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager for all modules.
 * @author DAM
 */
public final class ModulesManager
{
  private static ModulesManager _instance;

  private Map<String,Module> _modules;

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static ModulesManager getInstance()
  {
    synchronized (ModulesManager.class)
    {
      if (_instance==null)
      {
        _instance=new ModulesManager();
      }
      return _instance;
    }
  }

  /**
   * Private constructor.
   */
  private ModulesManager()
  {
    _modules=new HashMap<String,Module>();
  }

  /**
   * Get a module by name. Build it if not found.
   * @param name Module name.
   * @return A module.
   */
  public Module getModuleByName(String name)
  {
    Module module=_modules.get(name);
    if (module==null)
    {
      module=new Module(name);
      _modules.put(name,module);
    }
    return module;
  }
}
