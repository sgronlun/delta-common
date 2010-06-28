package delta.common.utils.files.index;

import java.io.File;
import java.util.Iterator;

import delta.common.utils.files.TextFileReader;
import delta.common.utils.files.TextFileWriter;

public class DirectoryIndexFileIO
{
  public static void writeToFile(File f, DirectoryIndex index)
  {
    TextFileWriter writer=new TextFileWriter(f);
    if (writer.start())
    {
      writer.writeNextLine(index.getRootDir().getAbsolutePath());
      writer.writeNextLine(index.getNbFiles());
      FileData d;
      for(Iterator<FileData> it=index.getEntries();it.hasNext();)
      {
        d=it.next();
        writer.writeNextLine(d.getName());
        writer.writeNextLine(d.getSize());
        writer.writeNextLine(d.getCRC());
      }
      writer.terminate();
    }
    else
    {
      // todo Warn
    }
  }

  public static DirectoryIndex loadFromFile(File indexFile)
  {
    TextFileReader p=new TextFileReader(indexFile);
    p.start();
    String rootDirStr=p.getNextLine();
    String nbFilesStr=p.getNextLine();
    long nbFiles=Long.parseLong(nbFilesStr);
    File rootDir=new File(rootDirStr);
    DirectoryIndex index=new DirectoryIndex(rootDir);
    String nameStr,sizeStr,crcStr;
    for(long i=0;i<nbFiles;i++)
    {
      nameStr=p.getNextLine();
      sizeStr=p.getNextLine();
      crcStr=p.getNextLine();
      long size=Long.parseLong(sizeStr);
      long crc=Long.parseLong(crcStr);
      index.addFile(nameStr,size,crc);
    }
    p.terminate();
    return index;
  }
}
