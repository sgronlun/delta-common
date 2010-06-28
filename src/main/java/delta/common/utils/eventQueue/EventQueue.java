package delta.common.utils.eventQueue;

import delta.common.utils.Tools;

public class EventQueue
{
  private EventQueueItem _head;
  private EventQueueItem _tail;

  public EventQueue()
  {
    _head=null;
    _tail=null;
  }

  public synchronized Event getNextEvent(long maxTimeToWait)
  {
    if(_head==null)
    {
      Tools.startWaiting(this,maxTimeToWait);
    }
    Event e=null;
    if(_head!=null)
    {
      e=_head.getEvent();
      _head=_head.getNext();
      if(_head==null)
      {
        _tail=null;
      }
    }

    return e;
  }

  public Event getNextEvent()
  {
    return getNextEvent(0);
  }

  public void loop()
  {
    while(true)
    {
      Event e=getNextEvent();
      if(e!=null)
      {
        handleEvent(e);
      }
    }
  }

  public synchronized void postEvent(Event e)
  {
    System.out.println("Thread : "+Thread.currentThread()+" posting event : "+e);
    EventQueueItem newTail=new EventQueueItem(e, null);
    if(_tail!=null)
    {
      _tail.setNext(newTail);
    }
    _tail=newTail;
    if(_head==null)
    {
      _head=_tail;
      notifyAll();
    }
  }

  public synchronized boolean hasEvents()
  {
    return(_head==null);
  }

  public void handleEvent(Event e)
  {
    System.out.println("Thread : "+Thread.currentThread()+" handling event : "+e);
  }

  public synchronized void dumpQueue()
  {
    System.out.println("Head="+_head);
    System.out.println("Tail="+_tail);
    EventQueueItem item=_head;
    while(item!=null)
    {
      System.out.println("   Item : "+item+" (event="+item.getEvent()+", next="+item.getNext()+")");
      item=item.getNext();
    }
  }
}
