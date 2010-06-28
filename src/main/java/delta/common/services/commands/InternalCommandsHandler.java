package delta.common.services.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import delta.common.utils.commands.Command;
import delta.common.utils.commands.CommandResult;
import delta.common.utils.commands.CommandsConstants;
import delta.common.utils.commands.CommandsRegistry;
import delta.common.utils.io.PrintStreamToStringBridge;
import delta.common.utils.services.Service;
import delta.common.utils.services.ServiceState;
import delta.common.utils.services.ServicesManager;
import delta.common.utils.traces.UtilsLoggers;

/**
 * Runnable used to handle internal commands.
 * @author DAM
 */
public class InternalCommandsHandler extends Service
{
  // todo change it in the future
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  /**
   * Environment variable used to indicate that the commands handler
   * should be used.
   */
  public static final String ENV_USE_COMMANDS_HANDLER="USE_COMMANDS_HANDLER";

  /**
   * Reference to the sole instance of this class
   */
  private static InternalCommandsHandler _instance;

  /**
   * Key used to tell the thread to quit.
   */
  private static final String QUIT="<QUIT>";

  /**
   * Thread that reads and dispatches commands.
   */
  private Thread _thread;

  private CommandsRegistry _registry;

  /**
   * Get the sole instance of this class.
   * @return The sole instance of this class.
   */
  public static InternalCommandsHandler getInstance()
  {
    if (_instance==null)
    {
      _instance=new InternalCommandsHandler();
    }
    return _instance;
  }

  /**
   * Private constructor.
   */
  private InternalCommandsHandler()
  {
    super("Commands Handler");
    _registry=CommandsRegistry.getInstance();
    ServicesManager.getInstance().registerService(_instance);
  }

  /**
   * Runnable core.
   * Read for standard input and handle received commands.
   */
  public void mainLoop()
  {
    InputStreamReader stdin=new InputStreamReader(System.in);
    BufferedReader in=new BufferedReader(stdin);
    String line;
    try
    {
      while (true)
      {
        line=in.readLine();
        if (line==null) break;
        if (line.startsWith(QUIT)) break;

        if (line.startsWith(CommandsConstants.INTERNAL_COMMAND))
        {
          line=line.substring(CommandsConstants.INTERNAL_COMMAND.length());
          parseAndExecute(line);
        }
      }
    }
    catch (IOException ioe)
    {
      _logger.error("",ioe);
    }
  }

  private void parseAndExecute(String commandLine)
  {
    try
    {
      Command c=Command.parse(commandLine);
      CommandResult result=_registry.handleCommand(c);
      if (_logger.isInfoEnabled())
      {
        PrintStreamToStringBridge bridge=new PrintStreamToStringBridge();
        result.dump(bridge.getPrintStream());
        String resultAsText=bridge.getText();
        _logger.info(resultAsText);
        bridge.close();
      }
    }
    catch(Exception e)
    {
      _logger.error("",e);
    }
  }

  /**
   * Start the internal commands handler service.
   */
  @Override
  public void start()
  {
    // Already started !
    if (_thread!=null) return;

    _thread=new Thread("Commands Handler thread")
    {
      @Override
      public void run()
      {
        mainLoop();
      }
    };
    // DAM - 11/08/2006 - Leo apps did not quit as a result of the quit action
    _thread.setDaemon(true);
    // END DAM
    _thread.start();
    setState(ServiceState.RUNNING);
  }

  /**
   * Stop the service.
   */
  @Override
  public void stop()
  {
    // Not started !
    if (_thread==null) return;
    // DAM - Don't know how to stop a thread blocked in a "readLine" (see mainLoop()).
    _thread=null;
    setState(ServiceState.NOT_RUNNING);
  }
}
