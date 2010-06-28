package delta.common.framework.application;

import delta.common.services.commands.InternalCommandsHandler;
import delta.common.services.logging.LoggingService;
import delta.common.utils.commands.CommandsRegistry;
import delta.common.utils.traces.LoggersRegistry;

/**
 * @author DAM
 */
public class Application
{
  //private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  /**
   * Reference to the sole instance of this class
   */
  private static Application _instance;
  private LoggingService _loggingService;
  private InternalCommandsHandler _commandsHandler;

  /**
   * Get the sole instance of this class.
   * @return The sole instance of this class.
   */
  public static Application getInstance()
  {
    if (_instance==null)
    {
      _instance=new Application();
    }
    return _instance;
  }

  /**
   * Private constructor.
   */
  private Application()
  {
    _instance=this;
    LoggersRegistry.getInstance();
    _loggingService=new LoggingService();
    _commandsHandler=InternalCommandsHandler.getInstance();
  }

  public void start()
  {
    _loggingService.start();
    CommandsRegistry registry=CommandsRegistry.getInstance();
    QuitCommand quitCommand=new QuitCommand();
    registry.registerCommand(QuitCommand.QUIT_COMMAND,quitCommand);
    _commandsHandler.start();
  }

  public void stop()
  {
    _loggingService.stop();
    _commandsHandler.stop();
  }

  public void quit(int exitCode)
  {
    stop();
    System.exit(exitCode);
  }
}
