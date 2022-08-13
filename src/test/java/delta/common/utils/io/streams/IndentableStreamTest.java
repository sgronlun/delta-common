package delta.common.utils.io.streams;

import junit.framework.TestCase;

/**
 * Simple test class for the IndentableStream.
 * @author DAM
 */
public class IndentableStreamTest extends TestCase
{
  /**
   * Some tests.
   */
  public void test()
  {
    IndentableStream s=new IndentableStream(System.out);
    System.out.println("****");
    s.println("Coucou");
    System.out.println("****");
    s.setIndentationLevel(1);
    s.println("Coucou");
    System.out.println("****");
    s.println("\nCoucou");
    System.out.println("****");
    s.println("\r\nCoucou\nTiti");
  }
}
