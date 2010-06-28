package delta.common.utils.traces;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.LoggerRepository;

import delta.common.utils.environment.Host;
import delta.common.utils.environment.JavaProcess;
import delta.common.utils.url.URLTools;

/**
 * Registry for loggers.
 * @author DAM
 */
public final class LoggersRegistry
{
  private static LoggersRegistry _instance;

  private Map<String,Logger> _loggers;

  /**
   * Get a logger by its name (create it if necessary).
   * @param name Logger's name.
   * @return A logger.
   */
  public static Logger getLogger(String name)
  {
    LoggersRegistry registry=LoggersRegistry.getInstance();
    Logger logger=registry.getLoggerByName(name,true);
    return logger;
  }

  /**
   * Get the sole instance of this class.
   * @return the sole instance of this class.
   */
  public static LoggersRegistry getInstance()
  {
    synchronized (LoggersRegistry.class)
    {
      if (_instance==null)
      {
        _instance=new LoggersRegistry();
      }
      return _instance;
    }
  }

  /**
   * Private constructor.
   */
  private LoggersRegistry()
  {
    _loggers=new HashMap<String,Logger>();
    init();
  }

  /**
   * Initialize traces sub-system.
   * <ul>
   * <li>Loads the template file,
   * <li>Replace variables with their current value,
   * <li>Set the resulting properties.
   * </ul>
   */
  private void init()
  {
    JavaProcess localJavaProcess=JavaProcess.getLocalJavaProcess();
    String name=localJavaProcess.getName();
    NDC.push(name);
    LoggerRepository repository=Logger.getRootLogger().getLoggerRepository();
    PropertyConfigurator pCfg=new PropertyConfigurator();
    URL url=URLTools.getFromClassPath("template_log4j.properties",this);
    if (url==null) return;

    // 1 - Load templace properties
    Properties props=new Properties();
    try
    {
      props.load(url.openStream());
    }
    catch (IOException ioe)
    {
      ioe.printStackTrace(System.err);
      return;
    }

    // 2 - Replace variables with their value
    Set<Object> keys=props.keySet();
    Object current;
    Object value;
    String strValue;
    String newValue;
    Host localhost=Host.getLocalHost();
    String hostName=localhost.getName();
    for(Iterator<Object> it=keys.iterator();it.hasNext();)
    {
      current=it.next();
      value=props.get(current);
      if (value instanceof String)
      {
        strValue=(String)value;
        newValue=strValue.replace("${APPLICATION_ID}",name);
        newValue=newValue.replace("${HOME}",System.getProperty("user.home"));
        newValue=newValue.replace("${USER}",System.getProperty("user.name"));
        newValue=newValue.replace("${HOST_NAME}",hostName);
        if (!newValue.equals(strValue))
        {
          props.put(current,newValue);
        }
      }
    }

    // 3 - Set resulting properties
    pCfg.doConfigure(props,repository);
  }

  /**
   * Get a logger by name.
   * @param name Logger's name.
   * @param createItIfNecessary Indicates if the desired logger must be created if it does not exist yet.
   * @return A <code>Logger</code> or <code>null</code> if not found.
   */
  public Logger getLoggerByName(String name, boolean createItIfNecessary)
  {
    Logger logger=_loggers.get(name);
    if ((logger==null) && (createItIfNecessary))
    {
      logger=createLogger(name);
      _loggers.put(name,logger);
    }
    return logger;
  }

  private Logger createLogger(String loggerName)
  {
    Logger logger=Logger.getLogger(loggerName);
    configureLogger(logger);
    return logger;
  }

  private void configureLogger(Logger logger)
  {
    if (System.getenv("PM_MANAGED")!=null)
    {
      logger.removeAppender(LoggingConstants.CONSOLE);
    }
  }

  /**
   * Set the level of a logger.
   * @param level to set.
   * @param loggerName name of the logger to modify.
   * @return <code>true</code> if a setting was done, <code>false</code> otherwise.
   */
  public boolean setLevelForLogger(Level level, String loggerName)
  {
    Logger logger=null;
    if (LoggingConstants.ROOT_LOGGER_NAME.equals(loggerName))
    {
      logger=Logger.getRootLogger();
    }
    else
    {
      logger=getLoggerByName(loggerName,false);
    }
    if (logger!=null)
    {
      logger.setLevel(level);
      return true;
    }
    return false;
  }

  /**
   * Get the set of defined logger names.
   * @return the set of defined logger names.
   */
  public Set<String> getLoggerNames()
  {
    HashSet<String> ret=new HashSet<String>();
    Set<String> names=_loggers.keySet();
    ret.addAll(names);
    return ret;
  }

  /**
   * Dump loggers definition to an output stream.
   * @param out stream to write to.
   */
  public void dumpLoggers(PrintStream out)
  {
    String loggerName;
    Logger logger;
    String line;
    final String FORMAT="%1$-25s %2$-10s %3$-10s";
    out.println(String.format(FORMAT,"Logger","Level","Own level"));

    Set<String> loggerNames=_loggers.keySet();
    List<String> sortedList=new ArrayList<String>(loggerNames);
    Collections.sort(sortedList);
    for(Iterator<String> it=sortedList.iterator();it.hasNext();)
    {
      loggerName=it.next();
      logger=getLoggerByName(loggerName,false);
      line=String.format(FORMAT,loggerName,logger.getEffectiveLevel(),logger.getLevel());
      out.println(line);
    }
  }
}
