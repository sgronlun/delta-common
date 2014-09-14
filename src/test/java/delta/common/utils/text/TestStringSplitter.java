package delta.common.utils.text;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test for the string splitter.
 * @author DAM
 */
public class TestStringSplitter extends TestCase
{
  /**
   * Constructor.
   */
  public TestStringSplitter()
  {
    super("String splitter test");
  }

  /**
   * Test string splitting.
   */
  public void testStringSplitter()
  {
    String[] samples= {"",",",",,",",,,",",adsd,bdff,","dfgf","dfgf,dds"};
    char[] separators= {',',',',',',',',',',',',','};
    String[][] expectedResults= {
      {}, {"",""}, {"","",""},{"","","",""},
      {"","adsd","bdff",""},
      { "dfgf" },
      { "dfgf", "dds" }
    };
    Assert.assertEquals(samples.length,separators.length);
    int nb=samples.length;

    for(int i=0;i<nb;i++)
    {
      // Test "array" version
      {
        String[] ret=StringSplitter.split(samples[i],separators[i]);
        if (expectedResults[i]==null)
        {
          Assert.assertNull(ret);
        }
        else
        {
          Assert.assertNotNull(ret);
          int nbParts=ret.length;
          Assert.assertEquals(expectedResults[i].length,nbParts);
          for(int j=0;j<nbParts;j++)
          {
            Assert.assertEquals(expectedResults[i][j],ret[j]);
          }
        }
      }
      // Test "list" version
      {
        List<String> ret=StringSplitter.splitAsList(samples[i],separators[i]);
        if (expectedResults[i]==null)
        {
          Assert.assertNull(ret);
        }
        else
        {
          Assert.assertNotNull(ret);
          int nbParts=ret.size();
          Assert.assertEquals(expectedResults[i].length,nbParts);
          for(int j=0;j<nbParts;j++)
          {
            Assert.assertEquals(expectedResults[i][j],ret.get(j));
          }
        }
      }
    }
  }
}
