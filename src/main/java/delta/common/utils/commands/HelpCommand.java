package delta.common.utils.commands;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import delta.common.utils.collections.CollectionTools;

/**
 * Code for the 'help' command.
 * @author DAM
 */
public class HelpCommand implements CommandImpl
{
  /**
   * Help command.
   */
  public static final String HELP_COMMAND="HELP";

  /**
   * Command's implementation.
   * @param context Command's context.
   * @return Command's result.
   * @exception Exception
   */
  public int run(CommandContext context) throws Exception
  {
    PrintStream ps=context.getOutStream();
    CommandsRegistry registry=CommandsRegistry.getInstance();
    Set<String> commandIDsSet=registry.getCommandIDs();
    String[] commandIDs=CollectionTools.stringArrayFromCollection(commandIDsSet);
    Arrays.sort(commandIDs);
    String cmdID;
    List<String> helpText;
    CommandImpl cmd;
    for(int i=0;i<commandIDs.length;i++)
    {
      cmdID=commandIDs[i];
      cmd=registry.getCommandImpl(cmdID);
      if (cmd!=null)
      {
        ps.println("["+cmdID+"]");
        helpText=cmd.help(cmdID);
        if (helpText!=null)
        {
          String helpLine;
          for(Iterator<String> it=helpText.iterator();it.hasNext();)
          {
            helpLine=it.next();
            ps.println("\t"+helpLine);
          }
        }
      }
    }
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
    ret.add("Get some help (dumps the help for all known commands).");
    ret.add("Example :");
    ret.add("\tHELP");
    return ret;
  }
}
