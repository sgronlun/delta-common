package delta.common.utils.files.iterator;

import java.io.File;

/**
 * Interface of a file iterator "callback" object.
 * @author DAM
 */
public interface FileIteratorCallback
{
  /**
   * Method called when a file must be handled.
   * @param absolute File to handle (absolute path).
   * @param relative File to handle (relative path).
   */
  public void handleFile(File absolute, File relative);

  /**
   * Method called when a directory must be handled.
   * @param absolute Directory to handle (absolute path).
   * @param relative Directory to handle (relative path).
   */
  public void handleDirectory(File absolute, File relative);

  /**
   * Method called when the iterator starts handling (enters) a directory.
   * @param absolute Directory to handle (absolute path).
   * @param relative Directory to handle (relative path).
   * @return <code>false</code> to skip directory's handling.
   */
  public boolean enterDirectory(File absolute, File relative);

  /**
   * Method called when the iterator has finished handling (leaves) a directory.
   * @param absolute Directory to handle (absolute path).
   * @param relative Directory to handle (relative path).
   */
  public void leaveDirectory(File absolute, File relative);
}
