package delta.common.utils.system.externalCommands;

import delta.common.utils.misc.SleepManager;

/**
 * Main class to test external commands.
 * @author DAM
 */
public class MainTestPDAG
{
  /**
   * Execute a command asynchronously.
   * @param command Command to execute.
   * @return the execution status.
   */
  public static ExecutionStatus executeCommandAsynchronous(String command)
  {
    ExternalCommand pm=new ExternalCommand(command);
    pm.executeAsynchronously();
    System.out.println("Start date : "+pm.getStartDate());
    SleepManager.sleep(5000);
    pm.stopProcess();
    System.out.println("End date : "+pm.getEndDate());
    long duration_l=pm.getEndDate().longValue()-pm.getStartDate().longValue();
    System.out.println("Duration (ms) = "+duration_l);
    return pm.getExecutionStatus();
  }

  /**
   * Execute a command synchronously.
   * @param command Command to execute.
   * @return the execution status.
   */
  public static Integer executeCommandSynchronous(String command)
  {
    ExternalCommand pm=new ExternalCommand(command);
    Integer ret=pm.executeSynchronously();
    System.out.println("Start date : "+pm.getStartDate());
    System.out.println("End date : "+pm.getEndDate());
    long duration_l=pm.getEndDate().longValue()-pm.getStartDate().longValue();
    System.out.println("Duration (ms) = "+duration_l);
    return ret;
  }

  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    System.out.println(MainTestPDAG.executeCommandAsynchronous("notepad.exe"));
  }
}
