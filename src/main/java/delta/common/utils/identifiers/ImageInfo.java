package delta.common.utils.identifiers;

/**
 * @author DAM
 */
public class ImageInfo extends TypeInfo
{
  private int _width;
  private int _height;
  private String _imageType;

  public ImageInfo(int width, int height, String imageType)
  {
    _width=width;
    _height=height;
    _imageType=imageType;
  }

  /**
   * @return Returns the width.
   */
  public int getWidth()
  {
    return _width;
  }

  /**
   * @return Returns the height.
   */
  public int getHeight()
  {
    return _height;
  }

  /**
   * @return Returns the imageType.
   */
  public String getImageType()
  {
    return _imageType;
  }
}
