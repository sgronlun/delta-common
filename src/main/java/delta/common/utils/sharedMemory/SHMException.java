package delta.common.utils.sharedMemory;

public class SHMException extends Exception
{
  private SharedMemorySegment _shm;
  public SHMException(SharedMemorySegment shm_p)
  {
    _shm=shm_p;
  }

  public SharedMemorySegment getSegment()
  {
    return _shm;
  }
}
