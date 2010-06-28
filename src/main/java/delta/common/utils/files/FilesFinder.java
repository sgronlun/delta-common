package delta.common.utils.files;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import delta.common.utils.files.iterator.AbstractFileIteratorCallback;
import delta.common.utils.files.iterator.FileIterator;

public class FilesFinder extends AbstractFileIteratorCallback
{
  public static final int ABSOLUTE_MODE=0;
  public static final int RELATIVE_MODE=1;

  private int _mode;
  private List<File> _files;
  private FileFilter _predicate;

  public FilesFinder()
  {
    _files=null;
  }

  public List<File> find(int mode, File root, FileFilter predicate, boolean recursive)
  {
    _mode=mode;
    _files=new ArrayList<File>();
    _predicate=predicate;
    FileIterator it=new FileIterator(root,recursive,this);
    it.run();
    List<File> files=_files;
    _files=null;
    return files;
  }

  @Override
  public void handleDirectory(File absolute, File relative)
  {
    File toUse=absolute;
    if (_mode==RELATIVE_MODE) toUse=relative;
    if (toUse!=null)
      if (_predicate.accept(absolute)) _files.add(toUse);
  }

  @Override
  public void handleFile(File absolute, File relative)
  {
    File toUse=absolute;
    if (_mode==RELATIVE_MODE) toUse=relative;
    if (_predicate.accept(absolute))
      _files.add(toUse);
  }
}
