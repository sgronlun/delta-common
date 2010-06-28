package delta.common.utils.processManagement;

import java.util.ArrayList;

public class ProcessesManager
{
  private ArrayList<ProcessManager> _processes;

  public ProcessesManager()
  {
    _processes=new ArrayList<ProcessManager>();
  }

  public void addProcess(ProcessManager pm)
  {
  	_processes.add(pm);
  }
}
