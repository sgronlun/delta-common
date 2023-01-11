package delta.common.utils.i18n;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Labels manager for a single locale. 
 * @author DAM
 */
public class SingleLocaleLabelsManager
{
  private String _locale;
  private Map<String,String> _labels;

  /**
   * Constructor.
   * @param locale Identifier/key of the managed locale.
   */
  public SingleLocaleLabelsManager(String locale)
  {
    _locale=locale;
    _labels=new HashMap<String,String>();
  }

  /**
   * Get the identifier/key of the managed locale.
   * @return A locale identifier.
   */
  public String getLocale()
  {
    return _locale;
  }

  /**
   * Get the managed keys.
   * @return a sorted list of keys.
   */
  public List<String> getKeys()
  {
    List<String> ret=new ArrayList<String>(_labels.keySet());
    Collections.sort(ret);
    return ret;
  }

  /**
   * Add a label.
   * @param key Label key.
   * @param value Label value.
   */
  public void addLabel(String key, String value)
  {
    _labels.put(key,value);
  }

  /**
   * Get the label for a given key.
   * @param key Key to use.
   * @return A label or <code>null</code> if not found.
   */
  public String getLabel(String key)
  {
    return _labels.get(key);
  }
}
