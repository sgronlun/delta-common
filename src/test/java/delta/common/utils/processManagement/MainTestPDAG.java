package delta.common.utils.processManagement;

import delta.common.utils.misc.SleepManager;

public class MainTestPDAG
{
  public static int executeCommandAsynchronous(String command_p)
  {
    ProcessManager pm=new ProcessManager(command_p);
    pm.executeAsynchronously();
    System.out.println("Start date : "+pm.getStartDate());
    SleepManager.sleep(5000);
    pm.stopProcess();
    System.out.println("End date : "+pm.getEndDate());
    long duration_l=pm.getEndDate()-pm.getStartDate();
    System.out.println("Duration (ms) = "+duration_l);
    return pm.getExecutionStatus();
  }

  public static int executeCommandSynchronous(String command_p)
  {
    ProcessManager pm=new ProcessManager(command_p);
    int return_l=pm.executeSynchronously();
    System.out.println("Start date : "+pm.getStartDate());
    System.out.println("End date : "+pm.getEndDate());
    long duration_l=pm.getEndDate()-pm.getStartDate();
    System.out.println("Duration (ms) = "+duration_l);
    return return_l;
  }

  public static void main(String[] args)
  {
    System.out.println(MainTestPDAG.executeCommandAsynchronous("notepad.exe"));
  }
}
