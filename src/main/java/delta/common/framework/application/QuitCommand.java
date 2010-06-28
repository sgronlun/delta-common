package delta.common.framework.application;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.commands.CommandContext;
import delta.common.utils.commands.CommandImpl;
import delta.common.utils.misc.SleepManager;
import delta.common.utils.traces.UtilsLoggers;

/**
 * Code for the 'quit' command.
 * @author DAM
 */
public class QuitCommand implements CommandImpl
{
  // todo change it in the future
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  /**
   * Quit command.
   */
  public static final String QUIT_COMMAND="QUIT";

  /**
   * Command's implementation.
   * @param context Command's context.
   * @return Command's result.
   */
  public int run(CommandContext context)
  {
    _logger.info("Received a QUIT request !!");
    // DAM - 14/11/2006 - Use a thread to give let the CORBA request finish
    // RuntimeEnvironment.quit(0);
    Runnable quitRunnable=new Runnable()
    {
      public void run()
      {
        SleepManager.sleep(500);
        Application app=Application.getInstance();
        app.quit(0);
      }
    };
    Thread thread=new Thread(quitRunnable);
    thread.setDaemon(false);
    thread.start();
    // END DAM
    return 0;
  }

  /**
   * Get a readable help for a command.
   * @param commandID Command's ID.
   * @return A list of text strings.
   */
  public List<String> help(String commandID)
  {
    List<String> ret=new ArrayList<String>();
    ret.add("Quit the application.");
    ret.add("Example :");
    ret.add("\tQUIT");
    return ret;
  }
}
