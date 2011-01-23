package delta.common.utils.files.index;

import java.io.File;

import delta.common.utils.files.iterator.AbstractFileIteratorCallback;
import delta.common.utils.files.iterator.FileIterator;
import delta.common.utils.misc.CRC;

public class DirectoryIndexer extends AbstractFileIteratorCallback
{
  private DirectoryIndex _index;

  public static void main(String[] args)
  {
    // Root path, index
    File rootPath=new File(args[0]);
    File indexFile=new File(args[1]);
    new DirectoryIndexer(rootPath,indexFile);
  }

  @Override
  public void handleFile(File absolute, File relative)
  {
    long crc=CRC.computeCRC(absolute);
    _index.addFile(absolute.getAbsolutePath(),absolute.length(),crc);
  }

  private DirectoryIndexer(File rootPath, File outFile)
  {
    _index=new DirectoryIndex(rootPath);
    FileIterator fi=new FileIterator(rootPath, true, this);
    fi.run();
    DirectoryIndexFileIO.writeToFile(outFile,_index);
  }
}
