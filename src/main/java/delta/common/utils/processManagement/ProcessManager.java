package delta.common.utils.processManagement;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

public class ProcessManager
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  private final String _command;
  private long _startDate;
  private long _endDate;
  private int _executionStatus;
  private int _exitCode;
  private Process _process;

  public ProcessManager(String command)
  {
    _executionStatus=ExecutionStatus.NOT_STARTED;
    _command=command;
  }

  public boolean executeAsynchronously()
  {
    boolean startOk=startProcess();
    if (!startOk) return false;
    Runnable r=new Runnable()
    {
      public void run()
      {
        waitForCompletion();
        _logger.info("Thread [Monitoring "+_command+"] died !");
      }
    };
    new Thread(r).start();
    return true;
  }

  private synchronized boolean startProcess()
  {
    _startDate=System.currentTimeMillis();
    try
    {
      _process=Runtime.getRuntime().exec(_command);
      _executionStatus=ExecutionStatus.RUNNING;
    }
    catch (IOException ioe)
    {
      _logger.error("",ioe);
      _executionStatus=ExecutionStatus.EXECUTION_FAILED;
      return false;
    }
    return true;
  }

  public int executeSynchronously()
  {
    boolean startOk=startProcess();
    if (!startOk) return _executionStatus;
    return waitForCompletion();
  }

  public int waitForCompletion()
  {
    Thread stdReader=null;
    Thread errReader=null;
    stdReader=new PipeReader(_process.getInputStream());
    stdReader.setName("Pipe reader (STD) for ["+_command+"]");
    stdReader.start();
    errReader=new PipeReader(_process.getErrorStream());
    errReader.setName("Pipe reader (ERR) for ["+_command+"]");
    errReader.start();
    int execStatus;
    try
    {
      stdReader.join();
      stdReader=null;
      errReader.join();
      errReader=null;
      _exitCode=_process.waitFor();

      execStatus=ExecutionStatus.EXECUTION_COMPLETED;
    }
    catch (InterruptedException ie)
    {
      _logger.error("",ie);
      execStatus=ExecutionStatus.EXECUTION_INTERRUPTED;
    }
    synchronized (this)
    {
      _executionStatus=execStatus;
      _process=null;
      _endDate=System.currentTimeMillis();
    }
    return _executionStatus;
  }

  public int getExecutionStatus()
  {
    return _executionStatus;
  }

  public int getExitCode()
  {
    return _exitCode;
  }

  public synchronized void stopProcess()
  {
    if (_process!=null)
    {
      _process.destroy();
      try
      {
        InputStream is=_process.getInputStream();
        if (is!=null) is.close();
      }
      catch (IOException ioe)
      {
        _logger.error("",ioe);
      }
      try
      {
        InputStream is=_process.getErrorStream();
        if (is!=null) is.close();
      }
      catch (IOException ioe)
      {
        _logger.error("",ioe);
      }
    }
  }

  public synchronized long getStartDate()
  {
    return _startDate;
  }

  public synchronized long getEndDate()
  {
    return _endDate;
  }
}
