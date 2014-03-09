package delta.common.utils.time.chronometers;

import java.io.PrintStream;
import java.util.Date;

/**
 * Chronometer.
 * @author DAM
 */
public class Chronometer
{
  private String _id;
  private Long _startTime;
  private Long _stopTime;
  private Chronometer _parent;
  private Chronometer _firstChild;
  private Chronometer _nextBrother;

  Chronometer(String id, Chronometer parent)
  {
    _id=id;
    _parent=parent;
    _firstChild=null;
    _nextBrother=null;
  }

  /**
   * Get the identifier for this chronometer.
   * @return a string identifier.
   */
  public String getId()
  {
    return _id;
  }

  /**
   * Get the start time for this chronometer.
   * @return A long value if was started, or <code>null</code>.
   */
  public Long getStartTime()
  {
    return _startTime;
  }

  /**
   * Get the stop time for this chronometer.
   * @return A long value if was stopped, or <code>null</code>.
   */
  public Long getStopTime()
  {
    return _stopTime;
  }

  /**
   * Indicates if this chronometer is running or not.
   * @return <code>true</code> if it is, <code>false</code> otherwise.
   */
  public boolean isRunning()
  {
    return ((_startTime!=null) && (_stopTime==null));
  }

  /**
   * Get the parent chronometer.
   * @return A chronomter or <code>null</code> if it has no parent.
   */
  public Chronometer getParent()
  {
    return _parent;
  }

  Chronometer createNewBrother(String id)
  {
    Chronometer ret=new Chronometer(id,_parent);
    _nextBrother=ret;
    return ret;
  }

  Chronometer createNewChild(String id)
  {
    Chronometer ret=new Chronometer(id,this);
    if (_firstChild==null) _firstChild=ret;
    else
    {
      Chronometer current=_firstChild;
      while (current._nextBrother!=null) current=current._nextBrother;
      current._nextBrother=ret;
    }
    return ret;
  }

  void stopChildren()
  {
     Chronometer current=_firstChild;
     while (current!=null)
     {
       current.stop();
       current=current._nextBrother;
     }
  }

  private void stopElderBrothers()
  {
     Chronometer current=_parent._firstChild;
     while ((current!=null) && (current!=this))
     {
       current.stop();
       current=current._nextBrother;
     }
  }

  void start()
  {
    _startTime=Long.valueOf(System.currentTimeMillis());
    _stopTime=null;
  }

  void stop()
  {
    if (_stopTime==null)
    {
      _stopTime=Long.valueOf(System.currentTimeMillis());
      stopChildren();
      stopElderBrothers();
    }
  }

  /**
   * Detach this chronomter from its parent.
   * @return <code>true</code> if it was successfull, <code>false</code> otherwise.
   */
  public boolean removeFromParent()
  {
    if ((_parent!=null) && (!isRunning()))
    {
      return _parent.removeChild(this);
    }
    return false;
  }

  boolean removeChild(Chronometer c)
  {
    Chronometer current=_firstChild;
    if (current==c)
    {
      _firstChild=c._nextBrother;
      return true;
    }
    while (true)
    {
      if (current!=null)
      {
        if (current._nextBrother==c)
        {
          current._nextBrother=c._nextBrother;
          return true;
        }
        current=current._nextBrother;
      }
      else
        break;
    }
    return false;
  }

  /**
   * Dump this chronometer to standard output.
   */
  public void dump()
  {
    recursiveDump(0,System.out);
  }

  void recursiveDump(int level, PrintStream ps)
  {
    for(int i=0;i<level;i++) ps.print('\t');
    ps.print(_id);
    ps.print(' ');
    if (_stopTime==null)
    {
      if (_startTime==null)
      {
        ps.print("(Not started)");
      }
      else
      {
        ps.print("(Running since ");
        ps.print(new Date(_startTime.longValue()));
        ps.println(')');
      }
    }
    else
    {
      ps.print("(Ran from ");
      ps.print(new Date(_startTime.longValue()));
      ps.print(" to ");
      ps.print(new Date(_stopTime.longValue()));
      ps.print(" (");
      ps.print(_stopTime.longValue()-_startTime.longValue());
      ps.println("ms)");
    }
    Chronometer current=_firstChild;
    while (current!=null)
    {
      current.recursiveDump(level+1,ps);
      current=current._nextBrother;
    }
  }

  @Override
  public String toString()
  {
    return _id;
  }
}
