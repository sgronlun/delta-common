package delta.common.utils.commands;

import java.util.HashMap;
import java.util.Set;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * Runnable used to handle internal commands.
 * @author DAM
 */
public class CommandsRegistry
{
  // todo change it in the future
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  /**
   * Result of command execution (internal error).
   */
  public static final int INTERNAL_ERROR=Short.MIN_VALUE;

  /**
   * Result of command execution (exception).
   */
  public static final int EXCEPTION=Short.MIN_VALUE+1;

  /**
   * Reference to the sole instance of this class
   */
  private static CommandsRegistry _instance;

  /**
   * Maps command IDs to command implementations.
   */
  private HashMap<String,CommandImpl> _commandsMap;

  /**
   * Get the sole instance of this class.
   * @return The sole instance of this class.
   */
  public static CommandsRegistry getInstance()
  {
    if (_instance==null)
    {
      _instance=new CommandsRegistry();
    }
    return _instance;
  }

  /**
   * Private constructor.
   */
  private CommandsRegistry()
  {
    _commandsMap=new HashMap<String,CommandImpl>();
    registerCommand(DiagnosticsCommand.DIAG_COMMAND,new DiagnosticsCommand());
    registerCommand(GCCommand.GC_COMMAND,new GCCommand());
    registerCommand(HelpCommand.HELP_COMMAND,new HelpCommand());
  }

  /**
   * Register a command implementation.
   * @param commandID Command's ID.
   * @param command Command's implementation.
   */
  public void registerCommand(String commandID, CommandImpl command)
  {
    _commandsMap.put(commandID,command);
  }

  /**
   * Unregister a command implementation.
   * @param commandID Command's ID.
   */
  public void unregisterCommand(String commandID)
  {
    _commandsMap.remove(commandID);
  }

  /**
   * Handle a command.
   * @param command Command.
   * @return Command's result.
   */
  public CommandResult handleCommand(Command command)
  {
    if (command==null) return null;
    CommandResult result=new CommandResult();
    String id=command.getID();
    CommandImpl impl=getCommandImpl(id);
    if (impl!=null)
    {
      CommandContext context=new CommandContext(command,result);
      try
      {
        int ret=impl.run(context);
        result.setCode(ret);
        result.setExecResult(CommandExecResult.NORMAL);
      }
      catch(Exception e)
      {
        _logger.error("",e);
        result.setExecResult(CommandExecResult.EXCEPTION);
        result.setThrowable(e);
      }
      finally
      {
        String out=context.getOutMessage();
        result.setOut(out);
        String err=context.getErrMessage();
        result.setErr(err);
        context.close();
      }
    }
    else
    {
      result.setExecResult(CommandExecResult.NOT_FOUND);
      _logger.warn("Unknown command ["+id+"]");
    }
    return result;
  }

  /**
   * Get a set of all known command IDs.
   * @return a set of all known command IDs.
   */
  Set<String> getCommandIDs()
  {
    return _commandsMap.keySet();
  }

  /**
   * Get the command implementation for a command ID.
   * @param commandID Command's ID.
   * @return A command implementation
   */
  CommandImpl getCommandImpl(String commandID)
  {
    return _commandsMap.get(commandID);
  }
}
