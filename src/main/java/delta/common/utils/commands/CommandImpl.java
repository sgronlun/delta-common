package delta.common.utils.commands;

import java.util.List;

/**
 * Interface for a command.
 * @author DAM
 */
public interface CommandImpl
{
  /**
   * Command's implementation.
   * @param context Command context.
   * @return Command's result.
   * @exception Exception
   */
  public int run(CommandContext context) throws Exception;

  /**
   * Get a readable help for a command.
   * @param commandID Command's ID.
   * @return A list of text strings.
   */
  public List<String> help(String commandID);
}
