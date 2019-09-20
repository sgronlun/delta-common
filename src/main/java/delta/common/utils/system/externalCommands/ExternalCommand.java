package delta.common.utils.system.externalCommands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.collections.CollectionTools;
import delta.common.utils.io.StreamTools;
import delta.common.utils.misc.MiscStringConstants;
import delta.common.utils.text.StringSplitter;

/**
 * Represents an external (system) command.
 * @author DAM
 */
public class ExternalCommand
{
  private static final Logger LOGGER=Logger.getLogger(ExternalCommand.class);

  private static int _counter=0;

  /**
   * Name of this command.
   */
  private String _name;
  /**
   * Command.
   */
  private String _command;
  /**
   * Command's args.
   */
  private List<String> _args;
  /**
   * Indicates if stdout should be stored or not.
   */
  private boolean _storeStdout;
  /**
   * StringBuffer used to store stdout output (or <code>null</code> if not needed).
   */
  private StringBuffer _stdOutput;
  /**
   * Indicates if stderr should be stored or not.
   */
  private boolean _storeStderr;
  /**
   * StringBuffer used to store stderr output (or <code>null</code> if not needed).
   */
  private StringBuffer _errOutput;
  /**
   * Start date as a time stamp (<code>null</code> if never started).
   */
  private Long _startDate;
  /**
   * End date as a time stamp (<code>null</code> if never terminated).
   */
  private Long _endDate;
  /**
   * Execution state of this command.
   */
  private ExecutionStatus _executionStatus;
  /**
   * Exit code of the external command (<code>null</code> if not terminated).
   */
  private Integer _exitCode;
  /**
   * Underlying Java ProcessBuilder object.
   */
  private ProcessBuilder _processBuilder;
  /**
   * Underlying Java Process object.
   */
  private Process _process;
  /**
   * Thread used for asynchronous execution (<code>null</code> if synchronous).
   */
  private Thread _asyncExecThread;
  /**
   * Thread used to read standard output.
   */
  private Thread _stdReader;
  /**
   * Thread used to read standard error (<code>null</code> if stderr is redirected to stdout).
   */
  private Thread _errReader;
  /**
   * Indicates if this command was stopped.
   */
  private boolean _stopped;

  /**
   * Build an instance of this class from a command line.
   * The given command line is split into space-separated words, then the first
   * word is the command and the next words are the command's arguments.
   * @param commandLine Command line to parse.
   * @return An instance of this class.
   */
  public static ExternalCommand buildFromCommandLine(String commandLine)
  {
    String command="";
    List<String> args=new ArrayList<String>();
    List<String> words=StringSplitter.splitAsList(commandLine,' ');
    if (words!=null)
    {
      int size=words.size();
      if (size>0)
      {
        command=words.get(0);
      }
      for(int i=1;i<size;i++)
      {
        args.add(words.get(i));
      }
    }
    ExternalCommand cmd=new ExternalCommand("",command,args);
    return cmd;
  }

  /**
   * Build a default name for a command.
   * @return a default name for a command.
   */
  public static String getDefaultName()
  {
    synchronized(ExternalCommand.class)
    {
      String ret="Command #"+String.valueOf(_counter);
      _counter++;
      return ret;
    }
  }

  /**
   * Constructor.
   * @param command Command to execute (no args).
   */
  public ExternalCommand(String command)
  {
    init(getDefaultName(),command,null);
  }

  /**
   * Constructor.
   * @param command Command to execute.
   * @param simpleArg First and only argument.
   */
  public ExternalCommand(String command, String simpleArg)
  {
    List<String> args=new ArrayList<String>();
    args.add(simpleArg);
    init(getDefaultName(),command,args);
  }

  /**
   * Constructor.
   * @param mainAndArgs Main command and args.
   */
  public ExternalCommand(List<String> mainAndArgs)
  {
    List<String> args=new ArrayList<String>();
    args.addAll(mainAndArgs);
    String command=args.remove(0);
    init(getDefaultName(),command,args);
  }

  /**
   * Constructor.
   * @param command Command to execute.
   * @param args Command's arguments.
   */
  public ExternalCommand(String command, List<String> args)
  {
    init(getDefaultName(),command,args);
  }

  /**
   * Constructor.
   * @param name Name of command.
   * @param command Command to execute.
   * @param args Command's arguments.
   */
  public ExternalCommand(String name, String command, List<String> args)
  {
    init(name,command,args);
  }

  /**
   * Initialization method.
   * @param name Name of command.
   * @param command Command.
   * @param args Command's arguments.
   */
  private void init(String name, String command, List<String> args)
  {
    _name=name;
    _command=command;
    _args=new ArrayList<String>();
    if ((args!=null) && (args.size()>0))
    {
      _args.addAll(args);
    }
    _stdOutput=new StringBuffer();
    _storeStdout=true;
    _errOutput=new StringBuffer();
    _storeStderr=true;
    _startDate=null;
    _endDate=null;
    _executionStatus=ExecutionStatus.NOT_STARTED;
    _exitCode=null;
    // Initialize process builder
    List<String> fullCommand=new ArrayList<String>();
    fullCommand.add(command);
    if ((args!=null) && (args.size()>0))
    {
      fullCommand.addAll(args);
    }
    _processBuilder=new ProcessBuilder(fullCommand);
    _process=null;
    _asyncExecThread=null;
  }

  /**
   * Get the name of the command.
   * @return the name of the command.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the working directory of the launched process.
   * (use this before executing command).
   * @param directory Directory to set.
   */
  public void setWorkingDirectory(File directory)
  {
    if (!isRunning())
    {
      _processBuilder.directory(directory);
    }
    else
    {
      LOGGER.error("Command is running. Cannot change it's working directory.");
    }
  }

  /**
   * Indicate if the standard output should be stored or not.
   * (use this before executing command).
   * @param store <code>true</code> if it should be stored, <code>false</code> otherwise.
   */
  public void storeStdout(boolean store)
  {
    if (!isRunning())
    {
      _storeStdout=store;
    }
    else
    {
      LOGGER.error("Command is running. Cannot change _storeStdout flag.");
    }
  }

  /**
   * Indicate if the error output should be stored or not.
   * (use this before executing command).
   * @param store <code>true</code> if it should be stored, <code>false</code> otherwise.
   */
  public void storeStderr(boolean store)
  {
    if (!isRunning())
    {
      _storeStderr=store;
    }
    else
    {
      LOGGER.error("Command is running. Cannot change _storeStderr flag.");
    }
  }

  /**
   * Empty the environment of the launched process.
   * (use this before executing command).
   */
  public void clearEnvironment()
  {
    if (!isRunning())
    {
      _processBuilder.environment().clear();
    }
    else
    {
      LOGGER.error("Command is running. Cannot change it's environment.");
    }
  }

  /**
   * Add an environment variable in the environment of the process to launch.
   * (use this before executing command).
   * @param name Name of the environment variable.
   * @param value Value of the environment variable.
   */
  public void putenv(String name, String value)
  {
    if (!isRunning())
    {
      _processBuilder.environment().put(name,value);
    }
    else
    {
      LOGGER.error("Command is running. Cannot change it's environment.");
    }
  }

  /**
   * Indicate if standard output and error output should be merged
   * into the standard output or not.
   * (use this before executing command).
   * @param merge <code>true</code> to redirect, <code>false</code> otherwise.
   */
  public void mergeStdoutAndStdErr(boolean merge)
  {
    if (!isRunning())
    {
      _processBuilder.redirectErrorStream(merge);
    }
    else
    {
      LOGGER.error("Command is running. Cannot change stderr redirection.");
    }
  }

  /**
   * Star the asynchronous execution of the command.
   * Launches the external command and returns immediately.
   * @return <code>true</code> if the command was launched, <code>false</code> otherwise.
   */
  public boolean executeAsynchronously()
  {
    boolean startOk=startProcess();
    if (!startOk) return false;
    Runnable r=new Runnable()
    {
      public void run()
      {
        waitForCompletion();
        LOGGER.info("Thread [Monitoring "+_command+"] died !");
        _asyncExecThread=null;
      }
    };
    _asyncExecThread=new Thread(r);
    // DAM - 20/03/2007 - Asynchronous processes execution should
    // not prevent parent process to finish...
    _asyncExecThread.setDaemon(true);
    // END DAM
    _asyncExecThread.start();
    return true;
  }

  private synchronized boolean startProcess()
  {
    boolean ret=true;
    String cmdLine=getCommandAndArgs();
    if (!isRunning())
    {
      _exitCode=null;
      _process=null;
      _startDate=Long.valueOf(System.currentTimeMillis());
      _endDate=null;
      _stdOutput.setLength(0);
      _errOutput.setLength(0);
      // DAM - 09/11/2007 - Optimisation
      // If we don't care about stdout and stderr, they can me merged
      // so that we launch only one output stream reading thread
      if ((!_storeStdout) && (!_storeStderr))
      {
        mergeStdoutAndStdErr(true);
      }
      // END DAM
      try
      {
        _stopped=false;
        _process=_processBuilder.start();
        startStreamReadingThreads();
        _executionStatus=ExecutionStatus.RUNNING;
        if (LOGGER.isInfoEnabled())
        {
          LOGGER.info("Command ["+cmdLine+"] started !");
        }
      }
      catch (IOException ioe)
      {
        LOGGER.error("",ioe);
        _executionStatus=ExecutionStatus.EXECUTION_FAILED;
        _endDate=_startDate;
        destroyProcess();
        ret=false;
      }
    }
    else
    {
      LOGGER.warn("Command is already running !");
      ret=false;
    }
    return ret;
  }

  /**
   * Synchronous execution.
   * Launches the external command and waits for its termination.
   * @return The exit code of the command or <code>null</code> if it was not launched.
   */
  public Integer executeSynchronously()
  {
    boolean startOk=startProcess();
    if (!startOk) return null;
    waitForCompletion();
    return _exitCode;
  }

  /**
   * Indicate if this command is currently running or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isRunning()
  {
    return (_process!=null);
  }

  /**
   * Write some text to standard input.
   * @param text Text to write.
   */
  public void writeToStdin(String text)
  {
    if (_process!=null) {
      try
      {
        OutputStream os=_process.getOutputStream();
        OutputStreamWriter writer=new OutputStreamWriter(os);
        BufferedWriter bWriter=new BufferedWriter(writer);
        bWriter.append(text);
        bWriter.flush();
      }
      catch(Exception e)
      {
        LOGGER.error("Could write text to stdin!",e);
      }
    }
  }

  private void startStreamReadingThreads()
  {
    boolean errIsRedirected=_processBuilder.redirectErrorStream();
    String label="STD OUT";
    if (errIsRedirected)
    {
      label="STD OUT/ERR";
    }
    _stdReader=new InputStreamThread(_process.getInputStream(),_stdOutput,_storeStdout);
    _stdReader.setName("Pipe reader ("+label+") for ["+_command+"]");
    _stdReader.start();
    if (!errIsRedirected)
    {
      _errReader=new InputStreamThread(_process.getErrorStream(),_errOutput,_storeStderr);
      _errReader.setName("Pipe reader (STD ERR) for ["+_command+"]");
      _errReader.start();
    }
  }

  /**
   * Wait for the end of the managed process.
   */
  private void waitForCompletion()
  {
    try
    {
      if (_stdReader!=null)
      {
        _stdReader.join();
        _stdReader=null;
      }
      if (_errReader!=null)
      {
        _errReader.join();
        _errReader=null;
      }
      int exitCode=_process.waitFor();
      if (_stopped)
      {
        _exitCode=null;
        _executionStatus=ExecutionStatus.EXECUTION_INTERRUPTED;
      }
      else
      {
        _exitCode=Integer.valueOf(exitCode);
        _executionStatus=ExecutionStatus.EXECUTION_COMPLETED;
      }
    }
    catch (InterruptedException ie)
    {
      LOGGER.error("",ie);
      _exitCode=null;
      _executionStatus=ExecutionStatus.EXECUTION_INTERRUPTED;
    }
    finally
    {
      _stdReader=null;
      _errReader=null;
      destroyProcess();
      _process=null;
      _endDate=Long.valueOf(System.currentTimeMillis());
      if (LOGGER.isInfoEnabled())
      {
        String cmdLine=getCommandAndArgs();
        LOGGER.info("Command ["+cmdLine+"] ended !");
      }
    }
  }

  /**
   * Destroy the managed process, if any.
   */
  private void destroyProcess()
  {
    if (_process!=null)
    {
      LOGGER.info("Killing process for command ["+_name+"]");
      _process.destroy();
      _process=null;
    }
  }

  /**
   * Wait for the completion of a command.
   * @param maxTimeToWait Maximum time to wait (milliseconds) or 0 for infinite time.
   * @return <code>true</code> if the command terminated before the expiration of the delay,
   * <code>false</code> otherwise.
   */
  public boolean waitForEndOfCommand(long maxTimeToWait)
  {
    boolean ret=false;
    Thread asynchExecThread=_asyncExecThread;
    if (asynchExecThread!=null)
    {
      try
      {
        _asyncExecThread.join(maxTimeToWait);
        if (asynchExecThread.isAlive())
        {
          ret=false;
        }
        else
        {
          ret=true;
        }
      }
      catch(InterruptedException ie)
      {
        LOGGER.error("Interrupted !",ie);
      }
    }
    else
    {
      ret=true;
    }
    return ret;
  }

  /**
   * Get the execution status of this command.
   * @return the execution status of this command.
   */
  public ExecutionStatus getExecutionStatus()
  {
    return _executionStatus;
  }

  /**
   * Get the exit code of the command.
   * @return the exit code of the command, or <code>null</code> if it is not terminated.
   */
  public Integer getExitCode()
  {
    return _exitCode;
  }

  /**
   * Get the standard output of this command.
   * @return the standard output of this command.
   */
  public String getStdout()
  {
    String ret=_stdOutput.toString();
    return ret;
  }

  /**
   * Get the error output of this command.
   * @return the error output of this command.
   */
  public String getStderr()
  {
    String ret=_errOutput.toString();
    return ret;
  }

  /**
   * Stop the command (if it was running).
   */
  public synchronized void stopProcess()
  {
    Process p=_process;
    if (p!=null)
    {
      _stopped=true;
      p.destroy();
      StreamTools.close(p.getInputStream());
      StreamTools.close(p.getErrorStream());
      waitForEndOfCommand(1000);
    }
  }

  /**
   * Get the start date of the external command.
   * @return the start date of the external command, or <code>null</code> if the
   * command has not started yet.
   */
  public Long getStartDate()
  {
    return _startDate;
  }

  /**
   * Get the end date of the external command.
   * @return the end date of the external command, or <code>null</code> if the
   * command has not terminated yet.
   */
  public Long getEndDate()
  {
    return _endDate;
  }

  /**
   * Get the command formated as a command line.
   * @return the command formated as a command line.
   */
  public String getCommandAndArgs()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_command);
    String args=CollectionTools.stringListAsString(_args," ",null,null);
    if (args.length()>0)
    {
      sb.append(' ');
      sb.append(args);
    }
    return sb.toString();
  }

  /**
   * Get a stringified representation of this object.
   * @return a stringified representation of this object.
   */
  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("Command [").append(_name).append("]").append(MiscStringConstants.NATIVE_EOL);
    String commandLine=getCommandAndArgs();
    sb.append(commandLine).append(MiscStringConstants.NATIVE_EOL);
    sb.append("Start time : ").append(_startDate).append(MiscStringConstants.NATIVE_EOL);
    sb.append("End time : ").append(_endDate).append(MiscStringConstants.NATIVE_EOL);
    sb.append("Execution status : ").append(_executionStatus).append(MiscStringConstants.NATIVE_EOL);
    sb.append("Exit code : ").append(_exitCode).append(MiscStringConstants.NATIVE_EOL);
    String ret=sb.toString();
    return ret;
  }

  /**
   * Thread used to read and store the output of a stream.
   * @author PJA
   * @author DAM
   */
  private static class InputStreamThread extends Thread
  {
    private InputStream _is;
    private StringBuffer _sb;
    private boolean _doStore;

    /**
     * Constructor.
     * @param is Input stream to manage.
     * @param sb Storage place.
     * @param doStore Indicates if we should store output or not.
     */
    public InputStreamThread(InputStream is, StringBuffer sb, boolean doStore)
    {
      _is=is;
      _sb=sb;
      _doStore=doStore;
      setDaemon(true);
    }

    /**
     * Read (and optionally store) the managed stream until EOF.
     */
    @Override
    public void run()
    {
      InputStreamReader isr=null;
      BufferedReader br=null;
      try
      {
        // Initialization
        try
        {
          isr=new InputStreamReader(_is);
          br=new BufferedReader(isr);
        }
        catch(Exception e)
        {
          LOGGER.warn("Pb while initialising stdout/stderr reader thread",e);
          return;
        }
        // Read stream
        try
        {
          String line;
          while(true)
          {
            line=br.readLine();
            if (line==null) break;
            if (_doStore)
            {
              _sb.append(line);
              _sb.append(MiscStringConstants.NATIVE_EOL);
            }
            Thread.yield();
          }
        }
        catch (Exception e)
        {
          // Not traced because destroy of command will cause an exception.
        }
      }
      finally
      {
        StreamTools.close(br);
        br=null;
        StreamTools.close(isr);
        isr=null;
      }
      if (LOGGER.isInfoEnabled())
      {
        LOGGER.info("Thread ["+getName()+"] died !");
      }
    }
  }
}
