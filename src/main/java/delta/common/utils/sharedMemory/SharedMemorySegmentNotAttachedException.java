package delta.common.utils.sharedMemory;

public class SharedMemorySegmentNotAttachedException extends SHMException
{
  public SharedMemorySegmentNotAttachedException(SharedMemorySegment shm_p)
  {
    super(shm_p);
  }
}
