package delta.common.utils.files.comparator;

import java.io.File;
import java.util.Comparator;

/**
 * Comparator for files, using their name.
 * @author DAM
 */
public class FileNameComparator implements Comparator<File>
{
  @Override
  public int compare(File o1, File o2)
  {
    String name1=o1.getName();
    String name2=o2.getName();
    return name1.compareTo(name2);
  }
}
