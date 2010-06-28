package delta.common.utils.files.index;

public class FileData extends FSComponentData
{
  private long _size;
  private long _crc;

  public FileData(DirectoryData parent, String name, long size, long crc)
  {
    super(parent,name);
    _size=size;
    _crc=crc;
  }

  @Override
  public long getSize() { return _size; }
  public long getCRC() { return _crc; }
  @Override
  public long getNumberOfFiles() { return 0; }
}
