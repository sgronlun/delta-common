package delta.common.utils.files;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.files.filter.ExtensionPredicate;
import delta.common.utils.traces.UtilsLoggers;

/**
 * Search and replace some text in a series of files.
 * @author DAM
 */
public class SearchAndReplace
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  /**
   * Constructor.
   * @param list Files to process.
   * @param what String to replace.
   * @param newWhat String to use as a replacement.
   */
  public SearchAndReplace(List<File> list, String what, String newWhat)
  {
    int nbFiles=list.size();
    File f;
    for (int i=0; i<nbFiles; i++)
    {
      f=list.get(i);
      handleFile(f,what,newWhat);
    }
  }

  private boolean handleFile(File f, String what, String newWhat)
  {
    boolean ret=false;
    File tmp=null;
    try
    {
      tmp=File.createTempFile("SaR",null);
      TextFileReader fp=new TextFileReader(f);
      TextFileWriter writer=new TextFileWriter(tmp);
      if (fp.start())
      {
        if (writer.start())
        {
          String line=null;
          while (true)
          {
            line=fp.getNextLine();
            if (line==null) break;
            line=line.replaceAll(what,newWhat);
            writer.writeNextLine(line);
          }
          writer.terminate();
        }
        fp.terminate();
      }
      ret=true;
      boolean copyOK=FileCopy.copy(tmp,f);
      if (!copyOK)
      {
        _logger.error("Could not copy ["+tmp.getAbsolutePath()+"] to ["+f.getAbsolutePath()+"]");
        ret=false;
      }
      tmp.delete();
    }
    catch (Exception e)
    {
      _logger.error("",e);
      if (tmp!=null) tmp.delete();
      ret=false;
    }
    return ret;
  }

  /**
   * Main method for this tool.
   * @param args Four arguments:
   * <ul>
   * <li>Directory to use,
   * <li>extension of files to process,
   * <li>string to replace,
   * <li>string to use as replacement.
   * </ul>
   */
  public static void main(String[] args)
  {
    if (args.length<4) return;
    String where=args[0];
    String extension=args[1];
    String what=args[2];
    String newWhat=args[3];
    FilesFinder finder=new FilesFinder();
    FileFilter filter=new ExtensionPredicate(extension);
    List<File> l=finder.find(FilesFinder.ABSOLUTE_MODE,new File(where),filter,true);
    new SearchAndReplace(l,what,newWhat);
  }
}
