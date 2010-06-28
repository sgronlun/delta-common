package delta.common.services.logging;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;

import delta.common.utils.commands.Command;
import delta.common.utils.commands.CommandContext;
import delta.common.utils.commands.CommandImpl;
import delta.common.utils.commands.CommandResult;
import delta.common.utils.environment.JavaProcess;
import delta.common.utils.traces.LoggersRegistry;

/**
 * Code for the 'log4j' command.
 * @author DAM
 */
public class LoggingCommand implements CommandImpl
{
  /**
   * Diagnostic command.
   */
  public static final String LOG4J_COMMAND="LOG4J";

  /**
   * Sub-command : set a logger's level.
   */
  public static final String SET_LEVEL="SET_LEVEL";

  /**
   * Sub-command : load configuration from file.
   */
  public static final String LOAD_CFG="LOAD_CFG";

  /**
   * Sub-command : save configuration from file.
   */
  public static final String SAVE_CFG="SAVE_CFG";

  /**
   * Sub-command : get current configuration.
   */
  public static final String GET_CFG="GET_CFG";

  /**
   * Sub-command : dump current configuration.
   */
  public static final String DIAG="DIAG";

  /**
   * Command's implementation.
   * @param context Command's context.
   * @return Command's result.
   */
  public int run(CommandContext context)
  {
    int ret=0;
    Command command=context.getCommand();
    int nbArgs=command.getNbArgs();
    if (nbArgs>0)
    {
      String subCmd=command.getArg(0);
      int nbSubArgs=nbArgs-1;
      if (SET_LEVEL.equals(subCmd))
      {
        if (nbSubArgs>=2)
        {
          LoggersRegistry registry=LoggersRegistry.getInstance();
          String logger=command.getArg(1);
          String levelStr=command.getArg(2);
          Level level=Level.toLevel(levelStr,null);
          boolean ok=registry.setLevelForLogger(level,logger);
          if (!ok)
          {
            PrintStream psErr=context.getErrStream();
            psErr.println("Unknown logger ["+logger+"]");
            ret=-1;
          }
          else
          {
            ret=0;
          }
        }
        else
        {
          PrintStream psErr=context.getErrStream();
          psErr.println("Bad args for LOG4J/SETLEVEL subcommand (nbArgs="+nbArgs+").");
          ret=-1;
        }
      }
      else if (LOAD_CFG.equals(subCmd))
      {
        JavaProcess localJavaProcess=JavaProcess.getLocalJavaProcess();
        String name=localJavaProcess.getName();
        LoggingConfiguration cfg=LoggingConfigurationIO.loadLoggingConfiguration(name);
        if (cfg!=null)
        {
          cfg.setLog4jCurrentConfiguration();
          ret=0;
        }
        else
        {
          PrintStream psErr=context.getErrStream();
          psErr.println("Cannot load traces configuration.");
          ret=-1;
        }
      }
      else if (SAVE_CFG.equals(subCmd))
      {
        JavaProcess localJavaProcess=JavaProcess.getLocalJavaProcess();
        String name=localJavaProcess.getName();
        LoggingConfiguration cfg=new LoggingConfiguration();
        cfg.readLog4jCurrentConfiguration();
        LoggingConfigurationIO.saveLoggingConfiguration(name,cfg);
        ret=0;
      }
      else if (GET_CFG.equals(subCmd))
      {
        LoggingConfiguration cfg=new LoggingConfiguration();
        cfg.readLog4jCurrentConfiguration();
        byte[] buffer=cfg.getBytes();
        CommandResult result=context.getResult();
        result.setBytes(buffer);
        ret=0;
      }
      else if (DIAG.equals(subCmd))
      {
        PrintStream ps=context.getErrStream();
        ps.println("--- LOGGERS ---");
        LoggersRegistry.getInstance().dumpLoggers(ps);
      }
      else
      {
        PrintStream psErr=context.getErrStream();
        psErr.println("Unknown LOG4J subcommand ["+subCmd+"].");
        ret=-1;
      }
    }
    else
    {
      PrintStream psErr=context.getErrStream();
      psErr.println("Unspecified LOG4J subcommand.");
      ret=-1;
    }
    return ret;
  }

  /**
   * Get a readable help for a command.
   * @param commandID Command's ID.
   * @return A list of text strings.
   */
  public List<String> help(String commandID)
  {
    List<String> ret=new ArrayList<String>();
    ret.add("Get some diagnostics about the application.");
    ret.add("Parameters :");
    ret.add("\t<subcommand>");
    ret.add("\t\tDIAG : get a readable state for the logging service.");
    ret.add("\t\tSET_LEVEL : set a loggers level.");
    ret.add("\t\t\t<loggerName>targeted logger's name.");
    ret.add("\t\t\t<level>level to set.");
    ret.add("\t\tLOAD_CFG : load traces configuration from a file.");
    ret.add("\t\tSAVE_CFG : save traces configuration to a file.");
    ret.add("\t\tGET_CFG : get current configuration as a TracesConfiguration object.");
    ret.add("\t\tExamples :");
    ret.add("\t\t\tLOG4J(DIAG)");
    ret.add("\t\t\tLOG4J(SET_LEVEL,COMMON,DEBUG)");
    ret.add("\t\t\tLOG4J(SET_LEVEL,MESSAGES.OUT,WARN)");
    ret.add("\t\t\tLOG4J(LOAD_CFG)");
    ret.add("\t\t\tLOG4J(SAVE_CFG)");
    return ret;
  }
}
