package delta.common.utils.sharedMemory;

import java.util.HashMap;

import delta.common.utils.Tools;

public class SHMFactory
{
  private static SHMFactory _instance=null;

  private HashMap<Integer, SharedMemorySegment> _segments;

  public static SHMFactory getInstance()
  {
    if(_instance==null)
    {
      _instance=new SHMFactory();
    }
    return _instance;
  }

  static
  {
    Tools.loadLibrary("_SHM");
  }

  private SHMFactory()
  {
    _segments=new HashMap<Integer, SharedMemorySegment>();
  }

  public SharedMemorySegment create(int key, int size)
  {
    SharedMemorySegment return_l=null;
    int id=SHMFactory.createSegment(key, size);
    if(id!=-1)
    {
      return_l=new SharedMemorySegment(id, size);
    }
    return return_l;
  }

  public SharedMemorySegment lookup(int key)
  {
    SharedMemorySegment seg=_segments.get(Integer.valueOf(key));
    if(seg==null)
    {
      int id=SHMFactory.getSegment(key);
      if(id!=-1)
      {
        seg=new SharedMemorySegment(id);
        _segments.put(Integer.valueOf(key), seg);
      }
    }

    return seg;
  }

  public int destroy(SharedMemorySegment segment)
  {
    int return_l=SHMFactory.destroySegment(segment.getID());
    return return_l;
  }

  private static native int createSegment(int id, int size);

  private static native int getSegment(int id);

  private static native int destroySegment(int id);
}
