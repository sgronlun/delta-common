package delta.common.utils.files.index;

import java.io.File;

import delta.common.utils.files.iterator.AbstractFileIteratorCallback;
import delta.common.utils.files.iterator.FileIterator;
import delta.common.utils.misc.CRC;

/**
 * Directory index updater: updates an existing directory index.
 * @author DAM
 */
public class DirectoryIndexUpdater
{
  private StructuredDirectoryIndex _sindex;
  private DirectoryIndex _index;

  /**
   * Main method.
   * @param args One argument: the index file to update.
   */
  public static void main(String[] args)
  {
    // Index file
    File indexFile=new File(args[0]);
    new DirectoryIndexUpdater(indexFile);
  }

  /**
   * Constructor.
   * @param indexFile Index file to update.
   */
  private DirectoryIndexUpdater(File indexFile)
  {
    _sindex=StructuredDirectoryIndexFileIO.loadFromFile(indexFile);
    System.out.println("Size : "+_sindex.getTotalSize());
    System.out.println("Nb items : "+_sindex.getNbItems());
    File rootPath=_sindex.getRootDir();
    _index=new DirectoryIndex(rootPath);
    FileIterator fi=new FileIterator(rootPath, true, new Iterator());
    fi.run();
    DirectoryIndexFileIO.writeToFile(indexFile,_index);
    System.out.println("New size : "+_index.getTotalSize());
    System.out.println("New nb items : "+_index.getNbFiles());
  }

  private class Iterator extends AbstractFileIteratorCallback
  {
    @Override
    public void handleFile(File absolute, File relative)
    {
      FileData fd=_sindex.getRelativeFile(relative);
      if (fd!=null)
      {
        if (fd.getSize()!=absolute.length())
        {
          long crc=CRC.computeCRC(absolute);
          _index.addFile(absolute.getAbsolutePath(),absolute.length(),crc);
        }
        else
        {
          _index.addFile(absolute.getAbsolutePath(),absolute.length(),fd.getCRC());
        }
      }
      else
      {
        long crc=CRC.computeCRC(absolute);
        _index.addFile(absolute.getAbsolutePath(),absolute.length(),crc);
      }
    }
  }
}
