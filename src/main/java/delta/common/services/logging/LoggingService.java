package delta.common.services.logging;

import delta.common.utils.commands.CommandsRegistry;
import delta.common.utils.environment.JavaProcess;
import delta.common.utils.services.Service;
import delta.common.utils.services.ServiceState;

/**
 * Service used to manage logging functions.
 * @author DAM
 */
public class LoggingService extends Service
{
  /**
   * Constructor.
   */
  public LoggingService()
  {
    super("Logging");
  }

  /**
   * Start this service.
   * <p>
   * <ul>
   * <li>Loads the logging configuration from the logging configuration file dedidated
   * to this process if it exists,
   * <li>Registers a command that allows remote logging configuration.
   * </ul>
   */
  @Override
  public void start()
  {
    JavaProcess localJavaProcess=JavaProcess.getLocalJavaProcess();
    String name=localJavaProcess.getName();
    LoggingConfiguration cfg=LoggingConfigurationIO.loadLoggingConfiguration(name);
    if (cfg!=null)
    {
      cfg.setLog4jCurrentConfiguration();
    }
    LoggingCommand cfgCommand=new LoggingCommand();
    CommandsRegistry cmdRegistry=CommandsRegistry.getInstance();
    cmdRegistry.registerCommand(LoggingCommand.LOG4J_COMMAND,cfgCommand);
    setState(ServiceState.RUNNING);
  }

  /**
   * Stops this service.
   * <p>
   * <ul>
   * <li>Unregisters the previously registered command.
   * </ul>
   */
  @Override
  public void stop()
  {
    CommandsRegistry cmdRegistry=CommandsRegistry.getInstance();
    cmdRegistry.unregisterCommand(LoggingCommand.LOG4J_COMMAND);
    setState(ServiceState.NOT_RUNNING);
  }
}
