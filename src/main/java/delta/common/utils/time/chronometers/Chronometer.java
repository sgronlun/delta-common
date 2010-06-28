package delta.common.utils.time.chronometers;

import java.io.PrintStream;
import java.util.Date;

public class Chronometer
{
  private String _id;
  private long _startTime;
  private long _stopTime;
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

  public String getId()
  {
    return _id;
  }

  public long getStartTime() { return _startTime; }
  public long getStopTime() { return _stopTime; }

  public boolean isRunning()
  {
    return ((_startTime>0) && (_stopTime==0));
  }

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
    _startTime=System.currentTimeMillis();
    _stopTime=0;
  }

  void stop()
  {
    if (_stopTime==0)
    {
      _stopTime=System.currentTimeMillis();
      stopChildren();
      stopElderBrothers();
    }
  }

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

  public void dump()
  {
    recursiveDump(0,System.out);
  }

  void recursiveDump(int level, PrintStream ps)
  {
    for(int i=0;i<level;i++) ps.print('\t');
    ps.print(_id);
    ps.print(' ');
    if (_stopTime==0)
    {
      ps.print("(Running since ");
      ps.print(new Date(_startTime));
      ps.println(')');
    }
    else
    {
      ps.print("(Ran from ");
      ps.print(new Date(_startTime));
      ps.print(" to ");
      ps.print(new Date(_stopTime));
      ps.print(" (");
      ps.print(_stopTime-_startTime);
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
