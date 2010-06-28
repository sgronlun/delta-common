package delta.common.utils.sharedMemory;

public class SharedMemorySegmentNotWritable extends SHMException
{
  public SharedMemorySegmentNotWritable(SharedMemorySegment shm_p)
  {
    super(shm_p);
  }
}
