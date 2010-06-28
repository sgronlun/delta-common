package delta.common.utils.system.externalCommands;

/**
 * Provides light-weight objects for the state of an external command. An
 * external starts in the 'not started' state. When it is launched, it goes to
 * the 'running' state if launch was successful, or 'execution failed' if it was
 * not.
 * <p>
 * Once in the 'running' state, it goes in the 'execution completed' state when
 * the command terminates, or in the 'execution interrupted' if the command is
 * interrupted.
 * <p>
 * So the initial state is 'not running' and there are three final state
 * 'execution completed','execution failed' and 'execution interrupted'.
 * @author DAM
 */
public final class ExecutionStatus
{
  /**
   * 'not started' external command state.
   */
  public static final ExecutionStatus NOT_STARTED=new ExecutionStatus("NOT_STARTED");
  /**
   * 'running' external command state.
   */
  public static final ExecutionStatus RUNNING=new ExecutionStatus("RUNNING");
  /**
   * 'execution failed' external command state.
   */
  public static final ExecutionStatus EXECUTION_FAILED=new ExecutionStatus("EXECUTION_FAILED");
  /**
   * 'execution completed' external command state.
   */
  public static final ExecutionStatus EXECUTION_COMPLETED=new ExecutionStatus("EXECUTION_COMPLETED");
  /**
   * 'execution interrupted' external command state.
   */
  public static final ExecutionStatus EXECUTION_INTERRUPTED=new ExecutionStatus("EXECUTION_INTERRUPTED");

  private String _id;

  /**
   * Private constructor.
   * @param id Identifier.
   */
  private ExecutionStatus(String id)
  {
    _id=id;
  }

  /**
   * Get the id of this execution status.
   * @return the id of this execution status.
   */
  public String getId()
  {
    return _id;
  }

  /**
   * Get a stringified representation of this object.
   * @return a stringified representation of this object.
   */
  @Override
  public String toString()
  {
    return _id;
  }
}
