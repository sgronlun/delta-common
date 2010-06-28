package delta.common.utils.eventQueue;

import java.util.Date;

public class Event
{
  private long _time;
  public Event()
  {
    _time=System.currentTimeMillis();
  }

  public Event(long time)
  {
    _time=time;
  }

  @Override
  public String toString()
  {
    return "Event ("+new Date(_time)+")";
  }
}
