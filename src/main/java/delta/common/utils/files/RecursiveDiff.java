package delta.common.utils.files;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.log4j.Logger;

import delta.common.utils.misc.CRC;

/**
 * Recursive diff tool.
 * @author DAM
 */
public class RecursiveDiff
{
  private static final Logger LOGGER=Logger.getLogger(RecursiveDiff.class);

  private File _f1;
  private File _f2;
  private boolean _useCRC;

  /**
   * Constructor.
   * @param rootPath1 Root directory.
   * @param rootPath2 Another root directory.
   */
  private RecursiveDiff(String rootPath1, String rootPath2)
  {
    // Checkings
    if (!checkPath(rootPath1))
    {
      LOGGER.error("Cannot read directory ["+rootPath1+"]");
    }
    if (!checkPath(rootPath2))
    {
      LOGGER.error("Cannot read directory ["+rootPath2+"]");
    }

    _f1=new File(rootPath1);
    _f2=new File(rootPath2);
    _useCRC=false;
  }

  /**
   * Do the recursive diff.
   * @return <code>true</code> if both directory contain the same data,
   * <code>false</code> otherwise.
   */
  private boolean go()
  {
    FileComparator fc=new FileComparator();
    return makeDiff(_f1,_f2,fc);
  }

  private boolean makeDiff(File f1, File f2, FileComparator fc)
  {
    boolean ret=true;
    File[] f1Childs=f1.listFiles();
    Arrays.sort(f1Childs,fc);
    File[] f2Childs=f2.listFiles();
    Arrays.sort(f2Childs,fc);

    int index1=0;
    int index2=0;
    int maxIndex1=f1Childs.length;
    int maxIndex2=f2Childs.length;
    while ((index1<maxIndex1)&&(index2<maxIndex2))
    {
      File child1=f1Childs[index1];
      File child2=f2Childs[index2];
      int comp=child1.getName().compareTo(child2.getName());
      if (comp==0)
      {
        if (child1.isDirectory()&&child2.isDirectory())
        {
          boolean subRet=makeDiff(child1,child2,fc);
          if (!subRet) ret=false;
        }
        else
        {
          if (child1.isFile()&&child2.isFile())
          {
            long size1=child1.length();
            long size2=child2.length();
            if (size1!=size2)
            {
              System.out.println("["+child1.getName()+"] and ["+child2.getName()+"] are different size ("+size1+"!="+size2+")");
              ret=false;
            }
            else if (_useCRC)
            {
              LOGGER.info("CRC for "+child1.getAbsolutePath());
              long crc1=CRC.computeCRC(child1);
              long crc2=CRC.computeCRC(child2);
              if (crc1!=crc2)
              {
                System.out.println("["+child1.getName()+"] and ["+child2.getName()+"] are different (CRC : "+crc1+"!="+crc2+")");
                ret=false;
              }
            }
          }
          else
          {
            LOGGER.warn("Incompatible file types : file/directory");
            ret=false;
          }
        }
        index1++;
        index2++;
      }
      else
      {
        if (comp<0)
        {
          System.out.println("File ["+child1.getName()+"] does not exist in ["+f2.getAbsolutePath()+"]");
          index1++;
          ret=false;
        }
        else
        // (comp>0)
        {
          System.out.println("File ["+child2.getName()+"] does not exist in ["+f1.getAbsolutePath()+"]");
          index2++;
          ret=false;
        }
      }
    }
    return ret;
  }

  private boolean checkPath(String path)
  {
    File f=new File(path);
    return (f.exists()&&f.canRead()&&f.isDirectory());
  }

  /**
   * Main method of this tool.
   * @param args Two arguments:
   * <ul>
   * <li>Directory 1,
   * <li>Directory 2.
   * </ul>
   */
  public static void main(String[] args)
  {
    if (args.length==2)
    {
      new RecursiveDiff(args[0],args[1]).go();
    }
    else
    {
      System.err.println("Need 2 arguments!");
    }
  }

  static class FileComparator implements Comparator<File>,Serializable
  {
    public int compare(File o1, File o2)
    {
      return o1.getName().compareTo(o2.getName());
    }
  }
}
