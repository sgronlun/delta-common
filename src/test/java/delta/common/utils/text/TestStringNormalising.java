package delta.common.utils.text;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test for the normalizing strings.
 * @author sgronlun
 */
public class TestStringNormalising extends TestCase
{
  /**
   * Constructor.
   */
  public TestStringNormalising()
  {
    super("String normalizing test");
  }

  /**
   * Test string normalizing
   */
  public void testStringNormalizing()
  {
    // Chars obtained from quests.xml plus a few more
    String utf8string="!\"#$%&'()*+,-./0123456789:;<=>?"
        +"ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]_"
        +"abcdefghijklmnopqrstuvwxyz{|}~"
        +"ÁÂÉÊËÍÎÓÔÖÚÛÅÄÖàáâäéêëíîòóôõöùúû"
        +"–—‘’“”•…‰";
    String expected="!\"#$%&'()*+,-./0123456789:;<=>?"
        +"ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]_"
        +"abcdefghijklmnopqrstuvwxyz{|}~"
        +"AAEEEIIOOOUUAAOaaaaeeeiiooooouuu"
        +"–—‘’“”•...‰";

    String norm=StringFilter.normalize(utf8string);
    // System.out.println(expected);
    // System.out.println(norm);
    Assert.assertEquals(norm,expected);
  }
}
