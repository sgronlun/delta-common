package delta.common.utils.diagnostics;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.Iterator;
import java.util.List;

public class JVMStatistics
{
  public JVMStatistics()
  {
    // Class loading
    System.out.println("CLASS LOADING");
    ClassLoadingMXBean classLoading=ManagementFactory.getClassLoadingMXBean();
    System.out.println("   getLoadedClassCount()="+classLoading.getLoadedClassCount());
    System.out.println("   getTotalLoadedClassCount()="+classLoading.getTotalLoadedClassCount());
    System.out.println("   getUnloadedClassCount()="+classLoading.getUnloadedClassCount());
    System.out.println("   isVerbose()="+classLoading.isVerbose());

    // Compilation
    System.out.println("COMPILATION");
    CompilationMXBean compilation=ManagementFactory.getCompilationMXBean();
    System.out.println("   getName()="+compilation.getName());
    System.out.println("   getTotalCompilationTime()="+compilation.getTotalCompilationTime());

    // Garbage collection
    System.out.println("GARBAGE COLLECTION");
    List<GarbageCollectorMXBean> gcList=ManagementFactory.getGarbageCollectorMXBeans();
    {
      GarbageCollectorMXBean gc;
      for(Iterator<GarbageCollectorMXBean> it=gcList.iterator();it.hasNext();)
      {
        gc=it.next();
        System.out.println("   getName()="+gc.getName());
        System.out.println("   getCollectionCount()="+gc.getCollectionCount());
        System.out.println("   getCollectionTime()="+gc.getCollectionTime());
        String[] memoryPoolNames=gc.getMemoryPoolNames();
        for(int i=0;i<memoryPoolNames.length;i++)
        {
          System.out.println("      getMemoryPoolNames("+i+")="+memoryPoolNames[i]);
        }
      }
    }

    // Memory managers
    System.out.println("MEMORY MANAGERS");
    List<MemoryManagerMXBean> mmList=ManagementFactory.getMemoryManagerMXBeans();
    {
      MemoryManagerMXBean mm;
      for(Iterator<MemoryManagerMXBean> it=mmList.iterator();it.hasNext();)
      {
        mm=it.next();
        System.out.println("   getName()="+mm.getName());
        System.out.println("   isValid()="+mm.isValid());
        String[] memoryPoolNames=mm.getMemoryPoolNames();
        for(int i=0;i<memoryPoolNames.length;i++)
        {
          System.out.println("      getMemoryPoolNames("+i+")="+memoryPoolNames[i]);
        }
      }
    }

    // Memory
    System.out.println("MEMORY");
    MemoryMXBean memory=ManagementFactory.getMemoryMXBean();
    System.out.println("   getObjectPendingFinalizationCount()="+memory.getObjectPendingFinalizationCount());
    System.out.println("   isVerbose()="+memory.isVerbose());
    MemoryUsage heapMemory=memory.getHeapMemoryUsage();
    System.out.println("   heapMemory.getCommitted()="+heapMemory.getCommitted());
    System.out.println("   heapMemory.getInit()="+heapMemory.getInit());
    System.out.println("   heapMemory.getMax()="+heapMemory.getMax());
    System.out.println("   heapMemory.getUsed()="+heapMemory.getUsed());
    MemoryUsage nonHeapMemory=memory.getNonHeapMemoryUsage();
    System.out.println("   nonHeapMemory.getCommitted()="+nonHeapMemory.getCommitted());
    System.out.println("   nonHeapMemory.getInit()="+nonHeapMemory.getInit());
    System.out.println("   nonHeapMemory.getMax()="+nonHeapMemory.getMax());
    System.out.println("   nonHeapMemory.getUsed()="+nonHeapMemory.getUsed());

    // OS
    System.out.println("OS");
    OperatingSystemMXBean os=ManagementFactory.getOperatingSystemMXBean();
    System.out.println("   getArch()="+os.getArch());
    System.out.println("   getAvailableProcessors()="+os.getAvailableProcessors());
    System.out.println("   getName()="+os.getName());
    System.out.println("   getVersion()="+os.getVersion());

    // Runtime
    System.out.println("Runtime");
    RuntimeMXBean bean=ManagementFactory.getRuntimeMXBean();
    System.out.println("   getStartTime()="+bean.getStartTime());
  }

  public static void main(String[] args)
  {
    new JVMStatistics();
  }
}
