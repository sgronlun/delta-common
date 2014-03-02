package delta.common.utils.files;

import java.io.File;

import delta.common.utils.files.iterator.AbstractFileIteratorCallback;
import delta.common.utils.files.iterator.FileIterator;

/**
 * Recursively copy directories.
 * @author DAM
 */
public class DirectoryCopy
{
  /**
   * Copy a directory to another one.
   * @param fromDir Directory to copy.
   * @param toDir Target directory.
   * @return <code>true</code> if it was sucessfull, <code>false</code> otherwise.
   */
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

  private static class Callback extends AbstractFileIteratorCallback
  {
    private File _toDir;

    Callback(File toDir)
    {
      _toDir=toDir;
    }

    @Override
    public void handleDirectory(File absolute, File relative)
    {
      File toCreate=new File(_toDir,relative.getPath());
      toCreate.mkdirs();
    }

    @Override
    public void handleFile(File absolute, File relative)
    {
      File toCreate=new File(_toDir,relative.getPath());
      FileCopy.copy(absolute,toCreate);
    }
  }
}
