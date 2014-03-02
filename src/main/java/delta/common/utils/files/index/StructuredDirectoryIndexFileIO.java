package delta.common.utils.files.index;

import java.io.File;

import delta.common.utils.files.TextFileReader;

/**
 * Read/write structured directory indexes.
 * @author DAM
 */
public class StructuredDirectoryIndexFileIO
{
  /**
   * Write a structured directory index to a file.
   * @param f File to write to.
   * @param index Index to write.
   */
  public static void writeToFile(File f, StructuredDirectoryIndex index)
  {
/*
    PrintStream ps=PrintStreamTools.openFile(f);
    ps.println(index.getName());
    ps.println(index.getNbFiles());
    FileData d;
    for(Iterator<FileData> it=index.getEntries();it.hasNext();)
    {
      d=it.next();
      ps.println(d.getName());
      ps.println(d.getSize());
      ps.println(d.getCRC());
    }
    PrintStreamTools.close(ps);
*/
  }

  /**
   * Load an index from a file.
   * @param indexFile Index file to read.
   * @return A directory index.
   */
  public static StructuredDirectoryIndex loadFromFile(File indexFile)
  {
    StructuredDirectoryIndex index=null;
    TextFileReader p=new TextFileReader(indexFile);
    if (p.start())
    {
      String name=p.getNextLine();
      String nbFilesStr=p.getNextLine();
      long nbFiles=Long.parseLong(nbFilesStr);
      File rootDir=new File(name);
      index=new StructuredDirectoryIndex(rootDir);
      String nameStr,sizeStr,crcStr;
      File file;
      for(long i=0;i<nbFiles;i++)
      {
        nameStr=p.getNextLine();
        sizeStr=p.getNextLine();
        crcStr=p.getNextLine();
        long size=Long.parseLong(sizeStr);
        long crc=Long.parseLong(crcStr);
        if (nameStr.startsWith(name))
        {
          nameStr=nameStr.substring(name.length()+1);
        }
        file=new File(nameStr);
        index.addFile(file,size,crc);
      }
      p.terminate();
    }
    return index;
  }
}
