package delta.common.utils.time.chronometers;

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

  public static ChronometerManager getInstance()
  {
    return _chronoManagers.get();
  }

  public ChronometerManager()
  {
    _parentForAll=new Chronometer(ROOT_CHRONOMETER,null);
    _parentForAll.start();
    _lastStarted=_parentForAll;
  }

  public void dump()
  {
    _parentForAll.recursiveDump(0,System.out);
  }

  public void stop(Chronometer c)
  {
    c.stop();
    _lastStarted=c.getParent();
  }

  public void stopRemoveAndDump(Chronometer c)
  {
    stop(c);
    c.removeFromParent();
    c.dump();
  }

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
