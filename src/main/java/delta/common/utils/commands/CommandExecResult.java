package delta.common.utils.commands;

import java.util.HashMap;

/**
 * Constant objects for command execution results.
 * @author DAM
 */
public class CommandExecResult
{
  /**
   * Normal command execution.
   */
  public static final CommandExecResult NORMAL=new CommandExecResult("NORMAL");
  /**
   * Command not found.
   */
  public static final CommandExecResult NOT_FOUND=new CommandExecResult("NOT_FOUND");
  /**
   * Command execution raised an exception.
   */
  public static final CommandExecResult EXCEPTION=new CommandExecResult("EXCEPTION");
  /**
   * Command execution failed (impossible to start remote invocation).
   */
  public static final CommandExecResult COMMUNICATION_FAILURE=new CommandExecResult("COMMUNICATION_FAILURE");
  /**
   * Unknown. Used when no other constant is the good one.
   */
  public static final CommandExecResult UNKNOWN=new CommandExecResult("UNKNOWN");

  private static HashMap<String,CommandExecResult> _map;

  private String _name;

  /**
   * Get the map that stores all the instances of this class.
   * @return the map that stores all the instances of this class.
   */
  private static HashMap<String,CommandExecResult> getMap()
  {
    if (_map==null)
    {
      _map=new HashMap<String,CommandExecResult>();
    }
    return _map;
  }

  /**
   * Private constructor.
   * @param name Identifying name for this object.
   */
  private CommandExecResult(String name)
  {
    _name=name;
    getMap().put(name,this);
  }

  /**
   * Get the identifying name of this object.
   * @return the identifying name of this object.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Get the instance of this class that has the specified name.
   * @param name Identifying name of instance to get.
   * @return An instance of this class
   */
  public static CommandExecResult getByName(String name)
  {
    CommandExecResult ret=UNKNOWN;
    if (name!=null)
    {
      ret=getMap().get(name);
    }
    if (ret==null) ret=UNKNOWN;
    return ret;
  }
}
