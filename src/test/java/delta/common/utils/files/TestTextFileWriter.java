package delta.common.utils.files;

import java.io.File;

import delta.common.utils.environment.User;
import junit.framework.TestCase;

/**
 * Unit test class for the text file writer.
 * @author DAM
 */
public class TestTextFileWriter extends TestCase
{
  /**
   * Constructor.
   */
  public TestTextFileWriter()
  {
    super("Text file writer test");
  }

  /**
   * Test a simple basic usage of the text file writer.
   */
  public void testWriteSimpleFile()
  {
    User user=User.getLocalUser();
    File userHome=user.getHomeDir();
    File f=new File(userHome,"testTextFileWriter.txt");
    TextFileWriter t=new TextFileWriter(f);
    t.start();
    t.writeNextLine("My line");
    t.terminate();
    f.delete();
  }
}
