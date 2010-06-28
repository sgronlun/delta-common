package delta.common.utils.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Code for the 'GC' command.
 * @author DAM
 */
public class GCCommand implements CommandImpl
{
  /**
   * GC command.
   */
  public static final String GC_COMMAND="GC";

  /**
   * Command's implementation.
   * @param context Command's context.
   * @return Command's result.
   */
  public int run(CommandContext context)
  {
    System.gc();
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
    ret.add("Perform a garbage collection.");
    ret.add("Example :");
    ret.add("\tGC");
    return ret;
  }
}
