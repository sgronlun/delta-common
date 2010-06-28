package delta.common.utils.files.iterator;

import java.io.File;


/**
 * This class provides a skeletal implementation of the <tt>FileIteratorCallback</tt>interface.
 * @author DAM
 */
public class AbstractFileIteratorCallback implements FileIteratorCallback
{
  /**
   * Implementation of the <code>handleFile</code> method of the
   * <tt>FileIteratorCallback</tt> interface.
   * @param absolute Path of the file to handle (absolute).
   * @param relative Path of the file to handle (relative to _rootPath).
   */
  public void handleFile(File absolute, File relative)
  {
    // Nothing to do !!
  }

  /**
   * Implementation of the <code>handleDirectory</code> method of the
   * <tt>FileIteratorCallback</tt> interface.
   * @param absolute Path of the file to handle (absolute).
   * @param relative Path of the file to handle (relative to _rootPath).
   */
  public void handleDirectory(File absolute, File relative)
  {
    // Nothing to do !!
  }

  /**
   * Implementation of the <code>enterDirectory</code> method of the
   * <tt>FileIteratorCallback</tt> interface.
   * @param absolute Path of the file to handle (absolute).
   * @param relative Path of the file to handle (relative to _rootPath).
   * @return <code>false</code> to skip directory's handling.
   */
  public boolean enterDirectory(File absolute, File relative)
  {
    return true;
  }

  /**
   * Implementation of the <code>leaveDirectory</code> method of the
   * <tt>FileIteratorCallback</tt> interface.
   * @param absolute Path of the file to handle (absolute).
   * @param relative Path of the file to handle (relative to _rootPath).
   */
  public void leaveDirectory(File absolute, File relative)
  {
    // Nothing to do !!
  }
}
