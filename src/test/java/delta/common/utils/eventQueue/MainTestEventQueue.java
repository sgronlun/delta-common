package delta.common.utils.eventQueue;

import java.util.Random;

import delta.common.utils.Tools;

public class MainTestEventQueue implements Runnable
{
  private EventQueue _myEventQueue;

  public MainTestEventQueue()
  {
    _myEventQueue=new EventQueue();
    Thread postThread=new Thread(this, "Post event thread");
    postThread.start();
    _myEventQueue.loop();
  }

  public static void main(String[] args)
  {
    new MainTestEventQueue();
  }

  public void run()
  {
    Random r=new Random();
    for(int i=10;i>0;i--)
    {
      int msToWait=r.nextInt(2000);
      Tools.sleep(msToWait);
      Event newEvent=new Event();
      _myEventQueue.postEvent(newEvent);
    }
  }
}
