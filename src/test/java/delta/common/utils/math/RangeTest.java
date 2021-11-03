package delta.common.utils.math;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test for class {@link Range}.
 * @author DAM
 */
public class RangeTest extends TestCase
{
  /**
   * Test method for {@link delta.common.utils.math.Range#getMin()}.
   */
  public void testGetMin()
  {
    // Empty range
    Range r1=new Range();
    Assert.assertEquals(r1.getMin(),null);
    // Minimum only range
    Range r2=new Range(Integer.valueOf(1),null);
    Assert.assertEquals(r2.getMin(),Integer.valueOf(1));
    // Maximum only range
    Range r3=new Range(null,Integer.valueOf(1));
    Assert.assertEquals(r3.getMin(),null);
    // Standard range
    Range r4=new Range(1,2);
    Assert.assertEquals(r4.getMin(),Integer.valueOf(1));
  }

  /**
   * Test method for {@link delta.common.utils.math.Range#getMax()}.
   */
  public void testGetMax()
  {
    // Empty range
    Range r1=new Range();
    Assert.assertEquals(r1.getMax(),null);
    // Minimum only range
    Range r2=new Range(Integer.valueOf(1),null);
    Assert.assertEquals(r2.getMax(),null);
    // Maximum only range
    Range r3=new Range(null,Integer.valueOf(1));
    Assert.assertEquals(r3.getMax(),Integer.valueOf(1));
    // Standard range
    Range r4=new Range(1,2);
    Assert.assertEquals(r4.getMax(),Integer.valueOf(2));
  }

  /**
   * Test method for {@link delta.common.utils.math.Range#contains(int)}.
   */
  public void testContains()
  {
    // Empty range
    Range r1=new Range();
    Assert.assertTrue(r1.contains(1));
    // Minimum only range
    Range r2=new Range(Integer.valueOf(1),null);
    Assert.assertTrue(r2.contains(1));
    Assert.assertTrue(r2.contains(2));
    Assert.assertFalse(r2.contains(0));
    // Maximum only range
    Range r3=new Range(null,Integer.valueOf(1));
    Assert.assertTrue(r3.contains(1));
    Assert.assertTrue(r3.contains(0));
    Assert.assertFalse(r3.contains(2));
    // Standard range
    Range r4=new Range(1,2);
    Assert.assertTrue(r4.contains(1));
    Assert.assertTrue(r4.contains(2));
    Assert.assertFalse(r4.contains(0));
    Assert.assertFalse(r4.contains(3));
  }

  /**
   * Test method for {@link delta.common.utils.math.Range#equals(java.lang.Object)}.
   */
  public void testEqualsObject()
  {
    // Empty range
    Range r1=new Range();
    Assert.assertEquals(r1,new Range());
    Assert.assertFalse(r1.equals(new Range(null,Integer.valueOf(0))));
    Assert.assertFalse(r1.equals(new Range(Integer.valueOf(0),null)));
    Assert.assertFalse(r1.equals(new Range(1,2)));
    // Minimum only range
    Range r2=new Range(Integer.valueOf(1),null);
    Assert.assertEquals(r2,new Range(Integer.valueOf(1),null));
    Assert.assertFalse(r2.equals(new Range(Integer.valueOf(0),null)));
    Assert.assertFalse(r2.equals(new Range(null,Integer.valueOf(1))));
    Assert.assertFalse(r2.equals(new Range()));
    Assert.assertFalse(r2.equals(new Range(1,2)));
    // Maximum only range
    Range r3=new Range(null,Integer.valueOf(1));
    Assert.assertEquals(r3,new Range(null,Integer.valueOf(1)));
    Assert.assertFalse(r3.equals(new Range(null,Integer.valueOf(0))));
    Assert.assertFalse(r3.equals(new Range(Integer.valueOf(1),null)));
    Assert.assertFalse(r3.equals(new Range()));
    Assert.assertFalse(r3.equals(new Range(1,2)));
    // Standard range
    Range r4=new Range(1,2);
    Assert.assertEquals(r4,new Range(1,2));
    Assert.assertFalse(r4.equals(new Range(null,Integer.valueOf(2))));
    Assert.assertFalse(r4.equals(new Range(Integer.valueOf(1),null)));
    Assert.assertFalse(r4.equals(new Range()));
    Assert.assertFalse(r4.equals(new Range(2,1)));
    // Null object
    Assert.assertFalse(new Range().equals(null));
    // Other object
    Assert.assertFalse(new Range().equals(new Object()));
  }

  /**
   * Test method for {@link delta.common.utils.math.Range#hashCode()}.
   */
  public void testHashCode()
  {
    Assert.assertEquals(new Range().hashCode(),new Range().hashCode());
    Assert.assertEquals(new Range(1,2).hashCode(),new Range(1,2).hashCode());
    Assert.assertEquals(new Range(null,Integer.valueOf(2)).hashCode(),new Range(null,Integer.valueOf(2)).hashCode());
    Assert.assertEquals(new Range(Integer.valueOf(2),null).hashCode(),new Range(Integer.valueOf(2),null).hashCode());
  }

  /**
   * Test method for {@link delta.common.utils.math.Range#toString()}.
   */
  public void testToString()
  {
    Assert.assertEquals(new Range().toString(),":");
    Assert.assertEquals(new Range(1,2).toString(),"1:2");
    Assert.assertEquals(new Range(null,Integer.valueOf(2)).toString(),":2");
    Assert.assertEquals(new Range(Integer.valueOf(2),null).toString(),"2:");
  }
}
