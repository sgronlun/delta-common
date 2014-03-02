package delta.common.utils.files.index;

/**
 * Stores data about a single file:
 * <ul>
 * - parent directory data,
 * - file name,
 * - size,
 * - CRC.
 * </ul>
 * @author DAM
 */
public class FileData extends FSComponentData
{
  private long _size;
  private long _crc;

  /**
   * Constructor.
   * @param parent Parent directory data (or <code>null</code>).
   * @param name Name of this file.
   * @param size File size.
   * @param crc CRC of file contents.
   */
  public FileData(DirectoryData parent, String name, long size, long crc)
  {
    super(parent,name);
    _size=size;
    _crc=crc;
  }

  @Override
  public long getSize()
  {
    return _size;
  }

  /**
   * Get the CRC for this file.
   * @return A CRC value.
   */
  public long getCRC()
  {
    return _crc;
  }
}
