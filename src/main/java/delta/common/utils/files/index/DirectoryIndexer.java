package delta.common.utils.files.index;

import java.io.File;

import delta.common.utils.files.iterator.AbstractFileIteratorCallback;
import delta.common.utils.files.iterator.FileIterator;
import delta.common.utils.misc.CRC;

/**
 * Directory indexer: builds a directory index from scratch.
 * @author DAM
 */
public class DirectoryIndexer
{
  private DirectoryIndex _index;

  /**
   * Main method.
   * @param args Two arguments:
   * <ul>
   * <li>the root path of the directory to be indexed,
   * <li>the index file.
   * </ul>.
   */
  public static void main(String[] args)
  {
    // Root path, index
    File rootPath=new File(args[0]);
    File indexFile=new File(args[1]);
    new DirectoryIndexer(rootPath,indexFile);
  }

  private DirectoryIndexer(File rootPath, File outFile)
  {
    _index=new DirectoryIndex(rootPath);
    FileIterator fi=new FileIterator(rootPath, true, new Iterator());
    fi.run();
    DirectoryIndexFileIO.writeToFile(outFile,_index);
  }

  private class Iterator extends AbstractFileIteratorCallback
  {
    @Override
    public void handleFile(File absolute, File relative)
    {
      long crc=CRC.computeCRC(absolute);
      _index.addFile(absolute.getAbsolutePath(),absolute.length(),crc);
    }
  }
}
