package delta.common.utils.time.chronometers;

/**
 * Manager of chronometers.
 * @author DAM
 */
public class ChronometerManager
{
  private static final String ROOT_CHRONOMETER="ROOT";
  private Chronometer _lastStarted;
  private Chronometer _parentForAll;

  // One ChronometerManager per thread !
  private static ThreadLocal<ChronometerManager> _chronoManagers=new ThreadLocal<ChronometerManager>()
  {
    @Override
    protected synchronized ChronometerManager initialValue()
    {
      return new ChronometerManager();
    }
  };

  /**
   * Get the chronometer manager for the current thread.
   * @return a chronometer manager.
   */
  public static ChronometerManager getInstance()
  {
    return _chronoManagers.get();
  }

  /**
   * Private constructor.
   */
  private ChronometerManager()
  {
    _parentForAll=new Chronometer(ROOT_CHRONOMETER,null);
    _parentForAll.start();
    _lastStarted=_parentForAll;
  }

  /**
   * Dump the chronometer data for this thread.
   */
  public void dump()
  {
    _parentForAll.recursiveDump(0,System.out);
  }

  /**
   * Stop a chronometer.
   * @param c Chronometer to stop.
   */
  public void stop(Chronometer c)
  {
    c.stop();
    _lastStarted=c.getParent();
  }

  /**
   * Stop, remove and dump a chronometer.
   * @param c Chronometer to use.
   */
  public void stopRemoveAndDump(Chronometer c)
  {
    stop(c);
    c.removeFromParent();
    c.dump();
  }

  /**
   * Start a new chronometer.
   * @param id Chronometer identifier.
   * @return A new chronometer (started).
   */
  public Chronometer start(String id)
  {
    Chronometer parent;
    if (_lastStarted.getId().equals(id))
    {
      parent=_lastStarted.getParent();
    }
    else
    {
      parent=_lastStarted;
    }
    Chronometer ret;
    parent.stopChildren();
    ret=parent.createNewChild(id);
    ret.start();
    _lastStarted=ret;
    return ret;
  }
}
