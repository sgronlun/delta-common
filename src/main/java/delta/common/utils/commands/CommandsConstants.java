package delta.common.utils.commands;

/**
 * Constants used by the commands subsystem.
 * @author DAM
 */
public class CommandsConstants
{
  /**
   * Prefix used for internal commands.
   * (commands sent by the processes manager to its child processes
   * using standard input/output).
   */
  public static final String INTERNAL_COMMAND="<IC>";

  /**
   * Prefix used for replies to internal commands.
   */
  public static final String INTERNAL_REPLY="<IR>";
}
