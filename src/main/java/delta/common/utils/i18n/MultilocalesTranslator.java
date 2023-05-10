package delta.common.utils.i18n;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * A translator that supports several locales.
 * @author DAM
 */
public class MultilocalesTranslator
{
  private static final Logger LOGGER=Logger.getLogger(MultilocalesTranslator.class);

  private String _baseName;
  private Map<Locale,Translator> _translators;

  /**
   * Constructor.
   * @param baseName of the <tt>ResourceBundle</tt> to use.
   * @param locales Locales to use.
   */
  public MultilocalesTranslator(String baseName, List<Locale> locales)
  {
    _baseName=baseName;
    _translators=new HashMap<Locale,Translator>();
    for(Locale locale : locales)
    {
      Translator t=new Translator(baseName,null,locale);
      _translators.put(locale,t);
    }
  }

  /**
   * Get the managed base name.
   * @return a base name.
   */
  public String getBaseName()
  {
    return _baseName;
  }

  /**
   * Get the managed locales.
   * @return a set of locales.
   */
  public Set<Locale> getLocales()
  {
    return Collections.unmodifiableSet(_translators.keySet());
  }

  /**
   * Get the translator for the given locale.
   * @param locale Locale to use.
   * @return A translator or <code>null</code> if not found.
   */
  public Translator getTranslator(Locale locale)
  {
    return _translators.get(locale);
  }

  /**
   * Translate a key with parameters.
   * @param key Key to use.
   * @param params Parameters to use.
   * @param locale Locale to use.
   * @return A translated label, or the initial key.
   */
  public String translate(String key, Object[] params, Locale locale)
  {
    Translator t=getTranslator(locale);
    if (t==null)
    {
      LOGGER.error("No translator for locale: "+locale);
      return key;
    }
    return t.translate(key,params);
  }
}
