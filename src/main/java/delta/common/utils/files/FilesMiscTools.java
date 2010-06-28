package delta.common.utils.files;

import java.io.File;

/**
 * Misc files related tools.
 * @author DAM
 */
public class FilesMiscTools
{
  /**
   * Build a <tt>File</tt> from the concatenation of two <tt>File</tt> instances.
   * @param parent Parent file.
   * @param child Child file.
   * @return A new <tt>File</tt> instance.
   */
  public static File build(File parent, File child)
  {
    if (child==null)
    {
      return parent;
    }
    File ret=new File(parent,child.getPath());
    return ret;
  }
}
