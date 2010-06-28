package delta.common.utils.eventQueue;

class EventQueueItem
{
  private Event _event;
  private EventQueueItem _next;

  EventQueueItem(Event e, EventQueueItem next)
  {
    _event=e;
    _next=next;
  }

  Event getEvent()
  {
    return _event;
  }

  EventQueueItem getNext()
  {
    return _next;
  }

  void setNext(EventQueueItem next)
  {
    _next=next;
  }
}
