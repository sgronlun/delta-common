package delta.common.utils.commands;

import java.io.PrintStream;

import delta.common.utils.BaseTypesFormatter;

/**
 * Represents the result of a command.
 * @author DAM
 */
public class CommandResult
{
  /**
   * Command execution result.
   */
  private CommandExecResult _execResult;
  /**
   * Exit code for the command.
   */
  private int _code;
  /**
   * Standard output of command.
   */
  private String _out;
  /**
   * Error output of command.
   */
  private String _err;
  /**
   * Byte output buffer of this command (used to return serialized objects).
   */
  private byte[] _bytesBuffer;
  /**
   * Throwable raised by the execution of the command.
   */
  private Throwable _throwable;

  /**
   * Constructor.
   */
  public CommandResult()
  {
    _execResult=CommandExecResult.NORMAL;
    _code=0;
    _out="";
    _err="";
    _bytesBuffer=new byte[0];
    _throwable=null;
  }

  /**
   * Get the execution result of the command.
   * @return the execution result of the command.
   */
  public CommandExecResult getExecResult()
  {
    return _execResult;
  }

  /**
   * Set the execution result of the command
   * @param execResult Execution result to set.
   */
  public void setExecResult(CommandExecResult execResult)
  {
    if (execResult==null) execResult=CommandExecResult.UNKNOWN;
    _execResult=execResult;
  }

  /**
   * Get the exit code of the command.
   * @return the exit code of the command.
   */
  public int getCode()
  {
    return _code;
  }

  /**
   * Set the exit code of the command.
   * @param code Exit code to set.
   */
  public void setCode(int code)
  {
    _code=code;
  }

  /**
   * Get the standard output of the command.
   * @return the standard output of the command.
   */
  public String getOut()
  {
    return _out;
  }

  /**
   * Set the standard output of the command.
   * @param message Standard output to set.
   */
  public void setOut(String message)
  {
    if (message==null) message="";
    _out=message;
  }

  /**
   * Get the error output of the command.
   * @return the error output of the command.
   */
  public String getErr()
  {
    return _err;
  }

  /**
   * Set the error output of the command.
   * @param message Error output to set.
   */
  public void setErr(String message)
  {
    if (message==null) message="";
    _err=message;
  }

  /**
   * Get the bytes buffer output for the command.
   * @return the bytes buffer output for the command.
   */
  public byte[] getBytes()
  {
    return _bytesBuffer;
  }

  /**
   * Set the bytes buffer output of the command.
   * @param bytesBuffer The buffer to set.
   */
  public void setBytes(byte[] bytesBuffer)
  {
    if (bytesBuffer==null) bytesBuffer=new byte[0];
    _bytesBuffer=bytesBuffer;
  }

  /**
   * Get the throwable raised by the execution of the command.
   * @return the throwable raised by the execution of the command.
   */
  public Throwable getThrowable()
  {
    return _throwable;
  }

  /**
   * Set the throwable raised by the execution of the command.
   * @param throwable Throwable to set.
   */
  public void setThrowable(Throwable throwable)
  {
    _throwable=throwable;
  }

  /**
   * Dump the contents of this object to the specified text stream.
   * @param ps stream to use.
   */
  public void dump(PrintStream ps)
  {
    CommandExecResult execResult=getExecResult();
    ps.println("Exec result = "+execResult.getName());
    ps.println("Exit code = "+getCode());
    ps.println("Out = ["+getOut()+"]");
    ps.println("Err = ["+getErr()+"]");
    if (_bytesBuffer.length>0)
    {
      String bytesStr=BaseTypesFormatter.format(_bytesBuffer);
      ps.println("Bytes = ["+bytesStr+"]");
    }
    if (_throwable!=null)
    {
      ps.print("Exception = [");
      _throwable.printStackTrace(ps);
      ps.println("]");
    }
  }
}
