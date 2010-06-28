package delta.common.utils.files.iterator;

import java.io.File;


/**
 * A file iterator.
 * A file iterator iterates recursively over directory structures and launches
 * callbacks through a <tt>FileIteratorCallback</tt> instance.
 * @author DAM
 */
public class FileIterator
{
  /**
   * Root of tree to scan.
   */
  private File _root;

  /**
   * Work recursively or not.
   */
  private boolean _recursive;

  /**
   * Callback methods.
   */
  private FileIteratorCallback _handler;

  /**
   * Constructor.
   * @param root Root of tree to scan.
   * @param recursive Recursive mode or not.
   * @param handler Callbacks.
   */
  public FileIterator(File root, boolean recursive, FileIteratorCallback handler)
  {
    _root=root;
    _recursive=recursive;
    _handler=handler;
  }

  /**
   * Do iteration.
   */
  public void run()
  {
    if (_root.isDirectory())
    {
      handleDirectory(_root,null);
    }
  }

  /**
   * Handle a directory.
   * @param absoluteDir Directory to handle (absolute path).
   * @param relativeDir Directory to handle (relative path).
   */
  private void handleDirectory(File absoluteDir, File relativeDir)
  {
    boolean doIt=_handler.enterDirectory(absoluteDir,relativeDir);
    if (!doIt) return;

    File[] files=absoluteDir.listFiles();
    if (files!=null)
    {
      File absCurrent;
      File relCurrent;
      for (int i=0; i<files.length; i++)
      {
        absCurrent=files[i];
        relCurrent=new File(relativeDir,absCurrent.getName());

        if (absCurrent.isDirectory())
        {
          _handler.handleDirectory(absCurrent,relCurrent);
          if (_recursive)
            handleDirectory(absCurrent,relCurrent);
        }
        else
        {
          if (absCurrent.isFile())
          {
            _handler.handleFile(absCurrent,relCurrent);
          }
        }
      }
    }
    _handler.leaveDirectory(absoluteDir,relativeDir);
  }
}
