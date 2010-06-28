package delta.common.services.logging;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import delta.common.utils.io.ByteArraySerializer;
import delta.common.utils.io.SerializationTools;
import delta.common.utils.io.SerializeRunnable;
import delta.common.utils.io.StreamTools;
import delta.common.utils.traces.LoggersRegistry;
import delta.common.utils.traces.LoggingConstants;
import delta.common.utils.traces.UtilsLoggers;

/**
 * Represents the traces configuration for an application.
 * @author DAM
 */
public class LoggingConfiguration implements Serializable
{
  // todo change it in the future
  private static final Logger _logger=UtilsLoggers.getCfgLogger();

  private HashMap<String,Level> _levels;

  /**
   * Constructor.
   */
  public LoggingConfiguration()
  {
    _levels=new HashMap<String,Level>();
  }

  /**
   * Clear all levels.
   */
  public void reset()
  {
    _levels.clear();
  }

  /**
   * Set the contents of this object so that it reflects the current log4j configuration.
   */
  public void readLog4jCurrentConfiguration()
  {
    _levels.clear();
    LoggersRegistry registry=LoggersRegistry.getInstance();
    Set<String> loggerNames=registry.getLoggerNames();
    String loggerName;
    Logger logger;
    Level level;
    for(Iterator<String> it=loggerNames.iterator();it.hasNext();)
    {
      loggerName=it.next();
      logger=registry.getLoggerByName(loggerName,false);
      level=logger.getLevel();
      if (level!=null)
      {
        setLevel(loggerName,level);
      }
    }
    Level rootLoggerLevel=Logger.getRootLogger().getEffectiveLevel();
    setLevel(LoggingConstants.ROOT_LOGGER_NAME,rootLoggerLevel);
  }

  private void resetLog4jConfiguration()
  {
    LoggersRegistry registry=LoggersRegistry.getInstance();
    Set<String> loggerNames=registry.getLoggerNames();
    String loggerName;
    Logger logger;
    for(Iterator<String> it=loggerNames.iterator();it.hasNext();)
    {
      loggerName=it.next();
      logger=registry.getLoggerByName(loggerName,false);
      logger.setLevel(null);
    }
  }

  /**
   * Set the log4j configuration to reflect the contents of this object.
   */
  public void setLog4jCurrentConfiguration()
  {
    resetLog4jConfiguration();

    // Build a sorted list of logger names
    Set<String> names=_levels.keySet();
    List<String> loggers=new ArrayList<String>(names);
    Collections.sort(loggers);

    LoggersRegistry registry=LoggersRegistry.getInstance();
    String loggerName;
    Logger logger;
    Level level;
    for(Iterator<String> it=loggers.iterator();it.hasNext();)
    {
      loggerName=it.next();
      if (LoggingConstants.ROOT_LOGGER_NAME.equals(loggerName))
      {
        logger=Logger.getRootLogger();
      }
      else
      {
        logger=registry.getLoggerByName(loggerName,false);
      }
      level=getLevel(loggerName);
      logger.setLevel(level);
    }
  }

  /**
   * Get the level configured for the specified logger.
   * @param loggerName Targeted logger.
   * @return A level or <code>null</code> if the logger is not configured.
   */
  public Level getLevel(String loggerName)
  {
    Level level=_levels.get(loggerName);
    return level;
  }

  /**
   * Get the effective level of a logger.
   * @param loggerName Targeted logger.
   * @return A level or <code>null</code> if the logger is not configured.
   */
  public Level getEffectiveLevel(String loggerName)
  {
    Level level=getLevel(loggerName);
    if (level==null)
    {
      int indexOfPoint=loggerName.lastIndexOf('.');
      if (indexOfPoint!=-1)
      {
        loggerName=loggerName.substring(0,indexOfPoint);
        level=getEffectiveLevel(loggerName);
      }
      else
      {
        level=getLevel(LoggingConstants.ROOT_LOGGER_NAME);
      }
    }
    return level;
  }

  /**
   * Set the level for a logger.
   * @param loggerName Targeted logger.
   * @param level Level to configure.
   */
  public void setLevel(String loggerName, Level level)
  {
    if (level==null)
    {
      _levels.remove(loggerName);
    }
    else
    {
      _levels.put(loggerName,level);
    }
  }

  /**
   * Set the level for a logger.
   * @param loggerName Targeted logger.
   * @param levelName Name of level to configure.
   */
  public void setLevel(String loggerName, String levelName)
  {
    Level level=Level.toLevel(levelName,null);
    setLevel(loggerName,level);
  }

  /**
   * Get the number of set loggers.
   * @return the number of set loggers.
   */
  public int getNbSetLoggers()
  {
    return _levels.size();
  }

  /**
   * Get the list of configured loggers.
   * @return the list of configured loggers.
   */
  public List<String> getSetLoggers()
  {
    Set<String> keys=_levels.keySet();
    List<String> ret=new ArrayList<String>();
    ret.addAll(keys);
    Collections.sort(ret);
    return ret;
  }

  /**
   * Get the byte array representation for a full task history.
   * @return a byte array.
   */
  public synchronized byte[] getBytes()
  {
    ByteArraySerializer serializer=new ByteArraySerializer();
    SerializeRunnable r=new SerializeRunnable()
    {
      public void serialize(DataOutputStream stream) throws IOException
      {
        List<String> setLoggers=getSetLoggers();
        String loggerName;
        Level level;
        String levelName;
        int nbLevels=setLoggers.size();
        stream.writeInt(nbLevels);
        for(Iterator<String> it=setLoggers.iterator();it.hasNext();)
        {
          loggerName=it.next();
          level=getLevel(loggerName);
          levelName=level.toString();
          stream.writeUTF(loggerName);
          stream.writeUTF(levelName);
        }
      }
    };
    byte[] ret=serializer.doIt(r);
    return ret;
  }

  /**
   * Initializes this traces configuration from the contents of a byte buffer.
   * @param buffer serialized traces configuration.
   */
  public void build(byte[] buffer)
  {
    _levels.clear();
    DataInputStream dis=null;
    try
    {
      dis=SerializationTools.openDataInputStream(buffer);
      int nbLevels=dis.readInt();
      String loggerName;
      Level level;
      String levelName;
      _levels=new HashMap<String,Level>();
      for(int i=0;i<nbLevels;i++)
      {
        loggerName=dis.readUTF();
        levelName=dis.readUTF();
        level=Level.toLevel(levelName);
        setLevel(loggerName,level);
      }
    }
    catch(IOException ioe)
    {
      _logger.error("",ioe);
    }
    finally
    {
      StreamTools.close(dis);
    }
  }

  /**
   * Dump the contents of this object to the specified text stream.
   * @param ps stream to use.
   */
  public void dump(PrintStream ps)
  {
    List<String> setLoggers=getSetLoggers();
    String loggerName;
    Level level;
    for(Iterator<String> it=setLoggers.iterator();it.hasNext();)
    {
      loggerName=it.next();
      level=getLevel(loggerName);
      ps.println(loggerName+"="+level);
    }
  }
}
