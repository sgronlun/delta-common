package delta.common.utils.files;

import java.io.File;

import delta.common.utils.files.iterator.AbstractFileIteratorCallback;
import delta.common.utils.files.iterator.FileIterator;

/**
 * @author DAM
 */
public class DirectoryCopy
{
  public boolean copy(File fromDir, File toDir)
  {
    if (fromDir==null)
      throw new IllegalArgumentException("fromDir is null");
    if (toDir==null)
      throw new IllegalArgumentException("toDir is null");
    toDir.mkdirs();
    if (!toDir.exists())
    {
      return false;
    }

    Callback c=new Callback(toDir);
    FileIterator it=new FileIterator(fromDir,true,c);
    it.run();
    return true;
  }

  static class Callback extends AbstractFileIteratorCallback
  {
    private File _toDir;

    Callback(File toDir)
    {
      _toDir=toDir;
    }

    /**
     * Handle a directory.
     * Creates directory <code>toDir</code>/<code>relative</code>.
     * @param absolute Absolute path of directory to handle.
     * @param relative Relative path of directory to handle.
     */
    @Override
    public void handleDirectory(File absolute, File relative)
    {
      File toCreate=new File(_toDir,relative.getPath());
      toCreate.mkdirs();
    }

    /**
     * Handle a file.
     * Copies file <code>absolute</code> to file <code>toDir</code>/<code>relative</code>.
     * @param absolute Absolute path of file to handle.
     * @param relative Relative path of file to handle.
     */
    @Override
    public void handleFile(File absolute, File relative)
    {
      File toCreate=new File(_toDir,relative.getPath());
      FileCopy.copy(absolute,toCreate);
    }
  }
}
