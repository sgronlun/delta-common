package delta.common.framework.application;

import delta.common.services.logging.LoggingCommand;
import delta.common.services.logging.LoggingConfiguration;
import delta.common.utils.commands.Command;
import delta.common.utils.commands.CommandResult;
import delta.common.utils.commands.CommandsRegistry;
import delta.common.utils.environment.JavaProcess;
import delta.common.utils.misc.SleepManager;

/**
 * Main class for a test application.
 * @author DAM
 */
public class MainTestApplication
{
  /**
   * Main method for this test application.
   * @param args Useless.
   */
  public static void main(String[] args)
  {
    System.setProperty(JavaProcess.APP_NAME_PROPERTY,"TEST");
    Application app=Application.getInstance();
    app.start();

    //CommonServicesService remoteControlService=CommonServicesService.getInstance();
    //remoteControlService.startIfNecessary();

    CommandsRegistry cmdRegistry=CommandsRegistry.getInstance();
    Command command=new Command(LoggingCommand.LOG4J_COMMAND,LoggingCommand.GET_CFG);
    CommandResult result=cmdRegistry.handleCommand(command);
    result.dump(System.out);
    byte[] buffer=result.getBytes();
    LoggingConfiguration loggingCfg=new LoggingConfiguration();
    loggingCfg.build(buffer);
    loggingCfg.dump(System.out);
    while(true)
    {
      SleepManager.sleep(10000);
    }
    //app.stop();
  }
}
