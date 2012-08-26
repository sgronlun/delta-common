package delta.common.utils.system.externalCommands;

import delta.common.utils.misc.SleepManager;

public class MainTestPDAG
{
  public static ExecutionStatus executeCommandAsynchronous(String command_p)
  {
    ExternalCommand pm=new ExternalCommand(command_p);
    pm.executeAsynchronously();
    System.out.println("Start date : "+pm.getStartDate());
    SleepManager.sleep(5000);
    pm.stopProcess();
    System.out.println("End date : "+pm.getEndDate());
    long duration_l=pm.getEndDate().longValue()-pm.getStartDate().longValue();
    System.out.println("Duration (ms) = "+duration_l);
    return pm.getExecutionStatus();
  }

  public static Integer executeCommandSynchronous(String command_p)
  {
    ExternalCommand pm=new ExternalCommand(command_p);
    Integer return_l=pm.executeSynchronously();
    System.out.println("Start date : "+pm.getStartDate());
    System.out.println("End date : "+pm.getEndDate());
    long duration_l=pm.getEndDate().longValue()-pm.getStartDate().longValue();
    System.out.println("Duration (ms) = "+duration_l);
    return return_l;
  }

  public static void main(String[] args)
  {
    System.out.println(MainTestPDAG.executeCommandAsynchronous("notepad.exe"));
  }
}
