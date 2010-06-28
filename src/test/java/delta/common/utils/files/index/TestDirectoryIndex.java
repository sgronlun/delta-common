package delta.common.utils.files.index;

import java.io.File;

import junit.framework.TestCase;

/**
 * Unit test class for the text file writer.
 * @author DAM
 */
public class TestDirectoryIndex extends TestCase
{
  /**
   * Constructor.
   */
  public TestDirectoryIndex()
  {
    super("Directory index test");
  }

  /**
   * Test a simple basic usage of the directory indexer.
   */
  public void testLoadIndex()
  {
    long now1=System.currentTimeMillis();
    File indexFile=new File("/home/dm/index.txt");
    StructuredDirectoryIndex index=StructuredDirectoryIndexFileIO.loadFromFile(indexFile);
    if (index!=null)
    {
      DirectoryData rootData=index.getRelativeDirectory(null,false);
      long now2=System.currentTimeMillis();
      long totalSize=rootData.getSize();
      long now3=System.currentTimeMillis();
      long totalFiles=rootData.getNumberOfFiles();
      long now4=System.currentTimeMillis();
      System.out.println("Time to load : "+(now2-now1)+"ms");
      System.out.println("Total size : "+totalSize);
      System.out.println("Time to compute size : "+(now3-now2)+"ms");
      System.out.println("Total nb files : "+totalFiles);
      System.out.println("Time to compute nb files : "+(now4-now3)+"ms");
    }
  }
}
