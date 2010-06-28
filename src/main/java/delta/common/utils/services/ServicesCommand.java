package delta.common.utils.services;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import delta.common.utils.commands.Command;
import delta.common.utils.commands.CommandContext;
import delta.common.utils.commands.CommandImpl;

/**
 * Code for the 'services' command.
 * @author DAM
 */
public class ServicesCommand implements CommandImpl
{
  /**
   * Diagnostic command.
   */
  public static final String SERVICES_COMMAND="SERVICES";

  /**
   * Dump services manager infos.
   */
  public static final String DUMP="DUMP";

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
      if (DUMP.equals(subCmd))
      {
        ServicesManager manager=ServicesManager.getInstance();
        PrintStream ps=context.getOutStream();
        manager.dump(ps);
        ret=0;
      }
      else
      {
        PrintStream psErr=context.getErrStream();
        psErr.println("Unknown SERVICES subcommand ["+subCmd+"]");
        ret=-1;
      }
    }
    else
    {
      PrintStream psErr=context.getErrStream();
      psErr.println("Bad args for command (nbArgs="+nbArgs+") Expected at least 1.");
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
    ret.add("\t\tDUMP : list services.");
    ret.add("\t\tExamples :");
    ret.add("\t\t\tSERVICES(DUMP)");
    return ret;
  }
}
