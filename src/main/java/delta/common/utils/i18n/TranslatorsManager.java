package delta.common.utils.i18n;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * The <tt>TranslatorsManager</tt> manages a all <tt>Translator</tt>
 * instances.
 * <p>
 * This class manages all <tt>Translator</tt> instances. It is the entry point
 * to create and retrieve translators.
 * <p>
 * This class manages a special translator called "default translator" used as
 * the parent of all other translators.
 * <p>
 * @author DAM
 */
public class TranslatorsManager
{
  private static final Logger _logger=UtilsLoggers.getI18NLogger();

  /**
   * Reference to the sole instance of this class.
   */
  private static TranslatorsManager _instance;

  /**
   * Default translator.
   */
  private Translator _defaultTranslator;
  /**
   * Registered translators.
   */
  private HashMap<String,Translator> _translators;

  /**
   * Get the name of the default translator.
   * @return The name of the default translator.
   */
  private static String getDefaultTranslatorName()
  {
    return TranslatorsManager.class.getName()+"_defaultMessages";
  }

  /**
   * Constructor.
   * <p>
   * It is private to ensure that the sole instance of this class is obtained by
   * a call to <code>getInstance()</code>.
   * <p>
   */
  private TranslatorsManager()
  {
    _translators=new HashMap<String,Translator>();
  }

  /**
   * Create a new translator associated with the given <code>name</code>,
   * with no parent.
   * @param clazz used to find associated <tt>ResourceBundle</tt>.
   * @return Newly created translator.
   */
  public Translator createTranslator(Class<?> clazz)
  {
    return createTranslator(clazz.getName(),null);
  }

  /**
   * Create a new translator associated with the given <code>name</code>,
   * with no parent.
   * @param name used to find associated <tt>ResourceBundle</tt>.
   * @return Newly created translator.
   */
  public Translator createTranslator(String name)
  {
    return createTranslator(name,null);
  }

  /**
   * Create a new translator associated with the given <code>name</code>,
   * with the specified parent.
   * @param name used to find associated <tt>ResourceBundle</tt>.
   * @param parent translator to use as parent.
   * @return Newly created translator.
   */
  public Translator createTranslator(String name, Translator parent)
  {
    Translator ret=_translators.get(name);
    if (ret==null)
    {
      ret=new Translator(name,parent);
      if (ret.isOK())
      {
        _translators.put(name,ret);
      }
      else
      {
        _logger.warn("Cannot build translator : "+name);
        ret=null;
      }
    }
    else
    {
      _logger.warn("Cannot create already existing translator : "+name);
    }
    return ret;
  }

  /**
   * Get a translator by its name.
   * @param name of the translator to find.
   * @return A translator or <code>null</code> if none found.
   */
  public Translator getTranslatorByName(String name)
  {
    return _translators.get(name);
  }

  /**
   * Get a translator by its name.
   * @param name of the translator to find.
   * @param createIfNotFound indicates if the specified translator should be
   * created if it does not exist yet (see createTranslator).
   * @param useDefaultInCaseOfFailure indicates if the default translator should
   * be returned if retrieval and creation both failed.
   * @return A translator or <code>null</code> if none could be found, created
   * and <code>useDefaultInCaseOfFailure</code> is <code>null</code>.
   */
  public Translator getTranslatorByName(String name, boolean createIfNotFound,
      boolean useDefaultInCaseOfFailure)
  {
    Translator ret=_translators.get(name);
    if ((ret==null)&&(createIfNotFound))
    {
      ret=createTranslator(name,null);
      if (ret==null)
      {
        if (useDefaultInCaseOfFailure)
        {
          _logger.warn("Cannot get or create specified translator ("+name+"). Using default one.");
          ret=getDefaultTranslator();
        }
      }
    }
    return ret;
  }

  /**
   * Get the default translator.
   * @return The default translator (not null).
   */
  public Translator getDefaultTranslator()
  {
    if (_defaultTranslator==null)
    {
      _defaultTranslator=createTranslator(getDefaultTranslatorName(),null);
    }
    return _defaultTranslator;
  }

  /**
   * Get the sole instance of this class.
   * @return The sole instance of this class.
   */
  public static TranslatorsManager getInstance()
  {
    if (_instance==null)
    {
      _instance=new TranslatorsManager();
    }
    return _instance;
  }

  /**
   * Dump the contents of this object to the specified text stream.
   * @param ps stream to use.
   */
  public void dump(PrintStream ps)
  {
    ps.println("Number of translators : "+_translators.size());
    Set<String> names=_translators.keySet();
    List<String> sortedNames=new ArrayList<String>(names);
    Collections.sort(sortedNames);
    String name;
    Translator t;
    for(Iterator<String> it=sortedNames.iterator();it.hasNext();)
    {
      name=it.next();
      t=getTranslatorByName(name);
      ps.println(("\tName : "+name+" ("+t.getNbOfMessages()+")"));
    }
  }
}
