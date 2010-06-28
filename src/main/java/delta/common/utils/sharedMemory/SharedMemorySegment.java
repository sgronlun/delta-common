package delta.common.utils.sharedMemory;

public class SharedMemorySegment
{
  private int _id;
  private int _address;
  private int _size;
  private boolean _attached;
  private boolean _readOnly;
  private static byte[] _dataByte=new byte[1];

  SharedMemorySegment(int id, int size)
  {
    _id=id;
    _size=size;
    _readOnly=true;
    _attached=false;
    _address=0;
  }

  SharedMemorySegment(int id)
  {
    _id=id;
    _size=-1;
    _readOnly=true;
    _attached=false;
    _address=0;
    retrieveInfos();
  }

  int getID()
  {
    return _id;
  }

  public boolean isReadOnly()
  {
    return _readOnly;
  }

  public boolean isAttached()
  {
    return _attached;
  }

  public int getSize()
  {
    return _size;
  }

  public boolean attach(boolean readOnly)
  {
    boolean ret=false;
    int address=SharedMemorySegment.attachSegment(_id, readOnly);
    if(address!=0)
    {
      _attached=true;
      _address=address;
      _readOnly=readOnly;
      ret=true;
    }
    return ret;
  }

  public boolean detach()
  {
    boolean ret=false;
    if(_attached)
    {
      int detachResult=SharedMemorySegment.detachSegment(_id);
      if(detachResult==0)
      {
        _attached=false;
        _size=-1;
        ret=true;
      }
    }
    return ret;
  }

  public int writeBuffer(byte[] data, int offsetSHM, int offsetData, int length)
  {
    int ret=SharedMemorySegment.unsafeWriteBuffer(_address, offsetSHM, data, offsetData, length);
    return ret;
  }

  public int writeString(String data, int offsetSHM, int maxLength)
  {
    return 0;
  }

  public void writeByte(byte data, int offsetSHM)
      throws SHMException
  {
    if(_attached)
    {
      if(!_readOnly)
      {
        _dataByte[0]=data;
        SharedMemorySegment.unsafeWriteBuffer(_address, offsetSHM, _dataByte, 0, 1);
      }
      else
      {
        throw new SharedMemorySegmentNotWritable(this);
      }
    }
    else
    {
      throw new SharedMemorySegmentNotAttachedException(this);
    }
  }

  public void writeInt(int data, int offsetSHM)
      throws SHMException
  {
    if(_attached)
    {
      if(!_readOnly)
      {
        SharedMemorySegment.unsafeWriteInt(_address, offsetSHM, data);
      }
      else
      {
        throw new SharedMemorySegmentNotWritable(this);
      }
    }
    else
    {
      throw new SharedMemorySegmentNotAttachedException(this);
    }
  }

  public int readBuffer(byte[] data, int offsetSHM, int offsetData, int length)
  {
    int ret=SharedMemorySegment.unsafeReadBuffer(_address, offsetSHM, data, offsetData, length);
    return ret;
  }

  public String readString(int offsetSHM, int maxLength)
  {
    return null;
  }

  public byte readByte(int offsetSHM)
      throws SharedMemorySegmentNotAttachedException
  {
    if(_attached)
    {
      SharedMemorySegment.unsafeReadBuffer(_address, offsetSHM, _dataByte, 0, 1);
      return _dataByte[0];
    }
    throw new SharedMemorySegmentNotAttachedException(this);
  }

  public int readInt(int offsetSHM)
      throws SharedMemorySegmentNotAttachedException
  {
    if(_attached)
    {
      int ret=SharedMemorySegment.unsafeReadInt(_address, offsetSHM);
      return ret;
    }
    throw new SharedMemorySegmentNotAttachedException(this);
  }

  private static native int attachSegment(int id, boolean readOnly);

  private static native int detachSegment(int id);

  private static native int unsafeWriteBuffer(int address, int offsetSHM, byte[] data, int offsetData, int length);

  private static native int unsafeReadBuffer(int address, int offsetSHM, byte[] data, int offsetData, int length);

  private static native int unsafeWriteInt(int address, int offsetSHM, int data);

  private static native int unsafeReadInt(int address, int offsetSHM);

  private native int retrieveInfos();
}
