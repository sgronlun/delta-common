package delta.common.utils.text;

import java.io.File;
import java.io.FileFilter;

import delta.common.utils.files.filter.CompoundPredicate;
import delta.common.utils.files.filter.ExtensionPredicate;
import delta.common.utils.files.filter.WildcardPredicate;
import delta.common.utils.files.iterator.AbstractFileIteratorCallback;
import delta.common.utils.files.iterator.FileIterator;

/**
 * @author DAM
 */
public class MainEncodingFinder extends AbstractFileIteratorCallback
{
  private int _count=0;

  private FileFilter _filter;
  @Override
  public void handleFile(File absolute, File relative)
  {
    //if (_filter.accept(relative))
    {
      TextEncodingFinder finder=new TextEncodingFinder(null);
      TextEncodingFinder.EncodingStats stats=finder.findEncoding(absolute);
      if (stats!=null)
      {
        String encoding=stats.getEncoding();
        if (EncodingNames.UTF_8.equals(encoding))
        {
          _count++;
          System.out.println(relative);
          if (true)
          {
            TextFileConverter converter=new TextFileConverter(null,EncodingNames.ISO8859_1,null);
            File to=new File("file.txt");
            converter.convert(absolute,to);
            to.renameTo(absolute);
          }
        }
      }
    }
  }

  public MainEncodingFinder()
  {
    //UtilsLoggers.getUtilsLogger().setLevel(Level.INFO);
    File root=new File("/home/dm/data/dev/src/exportSVN/delta/data/cibles");
    FileFilter f1=new ExtensionPredicate("el",false);
    FileFilter f2=new WildcardPredicate("TestBitmapX11.cxx.*");
    _filter=new CompoundPredicate(f1,f2,CompoundPredicate.MODE_OR);
    //_filter=new ExtensionPredicate("txt",false);
    FileIterator it=new FileIterator(root,true,this);
    it.run();
    System.out.println("Nb files : "+_count);
  }

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    new MainEncodingFinder();
  }
}
