package delta.common.utils.i18n;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * A <tt>Translator</tt> is used to translate keys into messages in a given
 * language.
 * <p>
 * This class manages a properties file in a given language.
 * <p>
 * It is used to translate messages.
 * @author DAM
 */
public class Translator
{
  private static final Logger _logger=UtilsLoggers.getI18NLogger();

  /**
   * Parent translator. Used to forward translation requests if they're not
   * handled by this instance.
   */
  private Translator _parent;
  /**
   * Underlying <tt>ResourceBundle</tt> used to build translated strings.
   */
  private ResourceBundle _messages;
  /**
   * Instance of <tt>MessageFormat</tt> used to build parametered messages.
   */
  private MessageFormat _messageBuilder;

  /**
   * Constructs a new translator using the <tt>ResourceBundle</tt> designated
   * by <tt>name</tt>, in the default locale, with no parent translator.
   * @param name of the <tt>ResourceBundle</tt> to use.
   * @deprecated for backward compatibility with C4I.
   */
  @Deprecated
  Translator(String name)
  {
    init(name,null);
  }

  /**
   * Constructs a new translator using the <tt>ResourceBundle</tt> designated
   * by <tt>name</tt>, in the default locale, with the specified
   * <tt>parent</tt> translator.
   * @param name of the <tt>ResourceBundle</tt> to use.
   * @param parent translator.
   */
  Translator(String name, Translator parent)
  {
    init(name,parent);
  }

  /**
   * Initialization routine used by all constructors. Fetches the right
   * <tt>ResourceBundle</tt>.
   * @param baseName name of the <tt>ResourceBundle</tt> to use.
   * @param parent translator.
   */
  private void init(String baseName, Translator parent)
  {
    try
    {
      _parent=parent;
      Locale locale=Locale.getDefault();
      _messages=ResourceBundle.getBundle(baseName,locale);
    }
    catch (Exception exception)
    {
      _logger.error("Translator initialization error",exception);
    }
  }

  /**
   * Builds the instance managed <tt>MessageFormat</tt> instance if necessary
   * and returns it.
   * @return the instance-managed <tt>MessageFormat</tt>.
   */
  private MessageFormat getMessageBuilder()
  {
    if (_messageBuilder==null)
    {
      _messageBuilder=new MessageFormat("",_messages.getLocale());
    }
    return _messageBuilder;
  }

  /**
   * Translates the given <tt>key</tt>. Forwards the translation request to
   * the parent if it has failed and if the parent translator is specified.
   * @param key used to find translated message.
   * @return a translated message or <tt>key</tt> if no translation was
   * possible.
   */
  public String translate(String key)
  {
    String msg=unsafeTranslate(key);
    if (msg==null)
    {
      msg=key;
      _logger.error("Key '"+key+"' not internationalized !");
    }
    return msg;
  }

  /**
   * Translates the given <code>key</code>. Forwards the translation request to
   * the parent if it has failed and if the parent translator is specified.
   * @param key used to find translated message.
   * @return a translated message or <code>null</code> if no translation was
   * possible.
   */
  public String unsafeTranslate(String key)
  {
    if (key==null)
    {
      throw new IllegalArgumentException("Bad key value : null");
    }

    String msg=null;
    try
    {
      if (_messages!=null)
      {
        msg=_messages.getString(key);
      }
    }
    catch (MissingResourceException e1)
    {
      try
      {
        if (_parent!=null)
        {
          msg=_parent.translate(key);
        }
      }
      catch (MissingResourceException e2)
      {
        // Nothing special to do
      }
    }
    return msg;
  }

  /**
   * Translates the given <code>key</code> using the specified <tt>parameter</tt>.
   * See {@link #translate(String)} for details about translation mechanism.
   * @param key used to find translation.
   * @param parameter merged into translated message.
   * @return a translated message or <code>key</code> if no translation was
   * possible.
   */
  public String translate(String key, Object parameter)
  {
    getMessageBuilder().applyPattern(translate(key));
    Object[] objects={parameter};
    return getMessageBuilder().format(objects);
  }

  /**
   * Translates the given <code>key</code> using the specified <tt>parameters</tt>.
   * See {@link #translate(String)} for details about translation mechanism.
   * @param key used to find translation.
   * @param parameters merged into translated message.
   * @return a translated message or <code>key</code> if no translation was
   * possible.
   */
  public String translate(String key, Object[] parameters)
  {
    getMessageBuilder().applyPattern(translate(key));
    return getMessageBuilder().format(parameters);
  }

  /**
   * Translates the given <code>key</code> using the specified <tt>parameters</tt>.
   * See {@link #translate(String)} for details about translation mechanism.
   * @param key used to find translation.
   * @param parameters merged into translated message.
   * @return a translated message or <code>null</code> if no translation was
   * possible.
   */
  public String unsafeTranslate(String key, Object[] parameters)
  {
    String msg=unsafeTranslate(key);
    if (msg==null) return null;
    getMessageBuilder().applyPattern(msg);
    return getMessageBuilder().format(parameters);
  }

  /**
   * Computes and returns the number of messages managed by this translator.
   * @return number of messages.
   */
  public int getNbOfMessages()
  {
    int nb=0;
    for(Enumeration<String> e=_messages.getKeys();e.hasMoreElements();)
    {
      e.nextElement();
      nb++;
    }
    return nb;
  }

  /**
   * Get the set of keys managed by this translator
   * @return a set of strings (keys).
   */
  public Set<String> getKeys()
  {
    Set<String> keys=new HashSet<String>();
    for(Enumeration<String> e=_messages.getKeys();e.hasMoreElements();)
    {
      keys.add(e.nextElement());
    }
    return keys;
  }

  /**
   * Indicates if this translator instance is successfully associated with a
   * valid ResourceBundle.
   * @return true if everything is OK, false otherwise.
   */
  boolean isOK()
  {
    return (_messages!=null);
  }
}
