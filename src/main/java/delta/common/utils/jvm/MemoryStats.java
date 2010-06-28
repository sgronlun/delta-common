package delta.common.utils.jvm;

import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * @author DAM
 */
public class MemoryStats
{
  public static void dumpStats(PrintStream ps)
  {
    ps.println("Memory stats : ");
    {
      MemoryMXBean memory=ManagementFactory.getMemoryMXBean();
      MemoryUsage memUsage=memory.getHeapMemoryUsage();
      long committedMem=memUsage.getCommitted();
      long usedMem=memUsage.getUsed();
      long maxMem=memUsage.getMax();
      long freeMem=committedMem-usedMem;
      ps.println("usedMem="+usedMem);
      ps.println("freeMem="+freeMem);
      ps.println("committedMem="+committedMem);
      ps.println("maxMem="+maxMem);
    }
  }
}
