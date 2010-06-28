package delta.common.utils.commands;

import java.io.PrintStream;

import delta.common.utils.io.PrintStreamToStringBridge;

/**
 * Represents the context of a command.
 * It contains :
 * <ul>
 * <li>the command,
 * <li>the result of the command.
 * </ul>.
 * It provides method to get the command's standard and error output streams.
 * @author DAM
 */
public class CommandContext
{
  private Command _command;
  private CommandResult _result;

  private PrintStreamToStringBridge _bridgeOut;
  private PrintStreamToStringBridge _bridgeErr;

  /**
   * Constructor.
   * @param command Command.
   * @param result Command's result.
   */
  CommandContext(Command command, CommandResult result)
  {
    _command=command;
    _result=result;
  }

  /**
   * Get the command of this context.
   * @return the command of this context.
   */
  public Command getCommand()
  {
    return _command;
  }

  /**
   * Get the command result of this context.
   * @return the command result of this context.
   */
  public CommandResult getResult()
  {
    return _result;
  }

  /**
   * Get the standard output stream for this command (open it if necessary).
   * @return the standard output stream for this command.
   */
  public PrintStream getOutStream()
  {
    if (_bridgeOut==null)
    {
      _bridgeOut=new PrintStreamToStringBridge();
    }
    PrintStream ret=_bridgeOut.getPrintStream();
    return ret;
  }

  /**
   * Get the contents of the standard output stream as a <tt>String</tt>.
   * @return the contents of the standard output stream as a <tt>String</tt>.
   */
  public String getOutMessage()
  {
    String ret="";
    if (_bridgeOut!=null)
    {
      ret=_bridgeOut.getText();
    }
    return ret;
  }

  /**
   * Get the error output stream for this command (open it if necessary).
   * @return the error output stream for this command.
   */
  public PrintStream getErrStream()
  {
    if (_bridgeErr==null)
    {
      _bridgeErr=new PrintStreamToStringBridge();
    }
    PrintStream ret=_bridgeErr.getPrintStream();
    return ret;
  }

  /**
   * Get the contents of the error output stream as a <tt>String</tt>.
   * @return the contents of the error output stream as a <tt>String</tt>.
   */
  public String getErrMessage()
  {
    String ret="";
    if (_bridgeErr!=null)
    {
      ret=_bridgeErr.getText();
    }
    return ret;
  }

  /**
   * Close all opened streams.
   */
  public void close()
  {
    if (_bridgeOut!=null)
    {
      _bridgeOut.close();
    }
    if (_bridgeErr!=null)
    {
      _bridgeErr.close();
    }
  }
}
