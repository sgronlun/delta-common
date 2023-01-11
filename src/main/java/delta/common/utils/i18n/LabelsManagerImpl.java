package delta.common.utils.i18n;

import java.util.HashMap;
import java.util.Map;

/**
 * Labels manager.
 * @author DAM
 */
public class LabelsManagerImpl
{
  private Map<String,SingleLocaleLabelsManager> _map;

  /**
   * Constructor.
   */
  public LabelsManagerImpl()
  {
    _map=new HashMap<String,SingleLocaleLabelsManager>();
  }

  /**
   * Register a locale.
   * @param key Locale identifier/key.
   * @param mgr Locale manager.
   */
  public void registerLocale(String key, SingleLocaleLabelsManager mgr)
  {
    _map.put(key,mgr);
  }

  /**
   * Get the label for a given key and locale.
   * @param key Key to use.
   * @param locale Locale to use.
   * @return A label or <code>null</code> if not found.
   */
  public String revolve(String key, String locale)
  {
    SingleLocaleLabelsManager mgr=_map.get(locale);
    if (mgr==null)
    {
      return null;
    }
    return mgr.getLabel(key);
  }
}
