package delta.common.utils.commands;

import java.io.PrintStream;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryManagerMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import delta.common.utils.configuration.Configuration;
import delta.common.utils.configuration.Configurations;
import delta.common.utils.i18n.TranslatorsManager;

/**
 * Code for the 'diagnostics' command.
 * @author DAM
 */
public class DiagnosticsCommand implements CommandImpl
{
  private CommandContext _context;

  /**
   * Diagnostic command.
   */
  public static final String DIAG_COMMAND="DIAG";

  /**
   * JVM Diagnostic command.
   */
  public static final String JVM_DIAG="JVM";

  /**
   * Configuration Diagnostic command.
   */
  public static final String CFG_DIAG="CFG";

  /**
   * I18n Diagnostic command.
   */
  public static final String I18N_DIAG="I18N";

  /**
   * Threads Diagnostic command.
   */
  public static final String THREADS_DIAG="THREADS";

  /**
   * Command's implementation.
   * @param context Command's context.
   * @return Command's result.
   */
  public int run(CommandContext context)
  {
    _context=context;
    Command command=context.getCommand();
    String type=command.getArg(0);
    int ret=0;
    if (JVM_DIAG.equals(type))
    {
      runJVMDiagnostic();
    }
    else if (CFG_DIAG.equals(type))
    {
      runCfgDiagnostic();
    }
    else if (I18N_DIAG.equals(type))
    {
      runI18NDiagnostic();
    }
    else if (THREADS_DIAG.equals(type))
    {
      runThreadsDiagnostic();
    }
    else
    {
      ret=-1;
    }
    return ret;
  }

  /**
   * JVM diagnostic.
   */
  public void runJVMDiagnostic()
  {
    PrintStream ps=_context.getOutStream();
    // Class loading
    runClassLoadingDiagnostic(ps);
    // Compilation
    runCompilationDiagnostic(ps);
    // Garbage collection
    runGarbageCollectionDiagnostic(ps);
    // Memory managers
    runMemoryManagersDiagnostic(ps);
    // Memory
    runMemoryDiagnostic(ps);
    // OS
    runOSDiagnostic(ps);
  }

  /**
   * Output a class loading diagnostic to the specified stream.
   * @param ps Output stream.
   */
  public void runClassLoadingDiagnostic(PrintStream ps)
  {
    ps.println("CLASS LOADING");
    ClassLoadingMXBean classLoading=ManagementFactory.getClassLoadingMXBean();
    ps.println("   getLoadedClassCount()="+classLoading.getLoadedClassCount());
    ps.println("   getTotalLoadedClassCount()="+classLoading.getTotalLoadedClassCount());
    ps.println("   getUnloadedClassCount()="+classLoading.getUnloadedClassCount());
    ps.println("   isVerbose()="+classLoading.isVerbose());
  }

  /**
   * Output a compilation diagnostic to the specified stream.
   * @param ps Output stream.
   */
  public void runCompilationDiagnostic(PrintStream ps)
  {
    ps.println("COMPILATION");
    CompilationMXBean compilation=ManagementFactory.getCompilationMXBean();
    ps.println("   getName()="+compilation.getName());
    ps.println("   getTotalCompilationTime()="+compilation.getTotalCompilationTime());
  }

  /**
   * Output a garbage collection diagnostic to the specified stream.
   * @param ps Output stream.
   */
  public void runGarbageCollectionDiagnostic(PrintStream ps)
  {
    ps.println("GARBAGE COLLECTION");
    List<GarbageCollectorMXBean> gcList=ManagementFactory.getGarbageCollectorMXBeans();
    {
      GarbageCollectorMXBean gc;
      for(Iterator<GarbageCollectorMXBean> it=gcList.iterator();it.hasNext();)
      {
        gc=it.next();
        ps.println("   getName()="+gc.getName());
        ps.println("      getCollectionCount()="+gc.getCollectionCount());
        ps.println("      getCollectionTime()="+gc.getCollectionTime());
        String[] memoryPoolNames=gc.getMemoryPoolNames();
        for(int i=0;i<memoryPoolNames.length;i++)
        {
          ps.println("      getMemoryPoolNames("+i+")="+memoryPoolNames[i]);
        }
      }
    }
  }

  /**
   * Output a memory managers diagnostic to the specified stream.
   * @param ps Output stream.
   */
  public void runMemoryManagersDiagnostic(PrintStream ps)
  {
    ps.println("MEMORY MANAGERS");
    List<MemoryManagerMXBean> mmList=ManagementFactory.getMemoryManagerMXBeans();
    {
      MemoryManagerMXBean mm;
      for(Iterator<MemoryManagerMXBean> it=mmList.iterator();it.hasNext();)
      {
        mm=it.next();
        ps.println("   getName()="+mm.getName());
        ps.println("      isValid()="+mm.isValid());
        String[] memoryPoolNames=mm.getMemoryPoolNames();
        for(int i=0;i<memoryPoolNames.length;i++)
        {
          ps.println("      getMemoryPoolNames("+i+")="+memoryPoolNames[i]);
        }
      }
    }
  }

  /**
   * Output a memory diagnostic to the specified stream.
   * @param ps Output stream.
   */
  public void runMemoryDiagnostic(PrintStream ps)
  {
    ps.println("MEMORY");
    MemoryMXBean memory=ManagementFactory.getMemoryMXBean();
    ps.println("   getObjectPendingFinalizationCount()="+memory.getObjectPendingFinalizationCount());
    ps.println("   isVerbose()="+memory.isVerbose());
    MemoryUsage heapMemory=memory.getHeapMemoryUsage();
    ps.println("   heapMemory.getCommitted()="+heapMemory.getCommitted());
    ps.println("   heapMemory.getInit()="+heapMemory.getInit());
    ps.println("   heapMemory.getMax()="+heapMemory.getMax());
    ps.println("   heapMemory.getUsed()="+heapMemory.getUsed());
    MemoryUsage nonHeapMemory=memory.getNonHeapMemoryUsage();
    ps.println("   nonHeapMemory.getCommitted()="+nonHeapMemory.getCommitted());
    ps.println("   nonHeapMemory.getInit()="+nonHeapMemory.getInit());
    ps.println("   nonHeapMemory.getMax()="+nonHeapMemory.getMax());
    ps.println("   nonHeapMemory.getUsed()="+nonHeapMemory.getUsed());
  }

  /**
   * Output an OS diagnostic to the specified stream.
   * @param ps Output stream.
   */
  public void runOSDiagnostic(PrintStream ps)
  {
    ps.println("OS");
    OperatingSystemMXBean os=ManagementFactory.getOperatingSystemMXBean();
    ps.println("   getArch()="+os.getArch());
    ps.println("   getAvailableProcessors()="+os.getAvailableProcessors());
    ps.println("   getName()="+os.getName());
    ps.println("   getVersion()="+os.getVersion());
  }

  /**
   * Configuration diagnostic.
   */
  public void runCfgDiagnostic()
  {
    PrintStream ps=_context.getOutStream();
    ps.println("--- CONFIGURATION ---");
    Configuration cfg=Configurations.getConfiguration();
    cfg.dump(ps);
  }

  /**
   * I18N diagnostic.
   */
  public void runI18NDiagnostic()
  {
    PrintStream ps=_context.getOutStream();
    ps.println("--- TRANSLATORS ---");
    TranslatorsManager.getInstance().dump(ps);
  }

  /**
   * Threads diagnostic.
   */
  public void runThreadsDiagnostic()
  {
    PrintStream ps=_context.getOutStream();
    ps.println("--- THREADS ---");
    Map<Thread,StackTraceElement[]> map=Thread.getAllStackTraces();
    Thread thread;
    StackTraceElement[] stack;
    for(Map.Entry<Thread,StackTraceElement[]> entry : map.entrySet())
    //for(Iterator<Thread> it=threads.iterator();it.hasNext();)
    {
      thread=entry.getKey();
      stack=entry.getValue();
      ps.println(thread);
      for(int i=0;i<stack.length;i++)
      {
        ps.println("\tat "+stack[i]);
      }
    }
  }

  /**
   * Get a readable help for a command.
   * @param commandID Command's ID.
   * @return A list of text strings.
   */
  public List<String> help(String commandID)
  {
    List<String> ret=new ArrayList<String>();
    ret.add("Get some diagnostics about the application.");
    ret.add("Parameters :");
    ret.add("\t<type>");
    ret.add("\t\tJVM : dump some information about the JVM's internals.");
    ret.add("\t\tCFG : dump configuration.");
    ret.add("\t\tI18N : dump I18N sub-system state.");
    ret.add("Examples :");
    ret.add("\tDIAG(JVM)");
    ret.add("\tDIAG(CFG)");
    ret.add("\tDIAG(I18N)");
    ret.add("\tDIAG(THREADS)");
    return ret;
  }
}
