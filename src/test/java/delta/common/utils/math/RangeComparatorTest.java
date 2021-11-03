package delta.common.utils.math;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test for class {@link RangeComparator}.
 * @author DAM
 */
public class RangeComparatorTest extends TestCase
{
  /**
   * Test method for {@link delta.common.utils.math.RangeComparator#compare(delta.common.utils.math.Range, delta.common.utils.math.Range)}.
   */
  public void testCompare()
  {
    RangeComparator c=new RangeComparator();
    // Empty range
    Range r1=new Range();
    Assert.assertEquals(c.compare(r1,new Range()),0);
    Assert.assertEquals(c.compare(r1,new Range(null,Integer.valueOf(0))),-1);
    Assert.assertEquals(c.compare(r1,new Range(Integer.valueOf(0),null)),-1);
    Assert.assertEquals(c.compare(r1,new Range(1,2)),-1);
    // Minimum only range
    Range r2=new Range(Integer.valueOf(1),null);
    Assert.assertEquals(c.compare(r2,new Range(Integer.valueOf(1),null)),0);
    Assert.assertEquals(c.compare(r2,new Range(Integer.valueOf(0),null)),1);
    Assert.assertEquals(c.compare(r2,new Range(null,Integer.valueOf(1))),1);
    Assert.assertEquals(c.compare(r2,new Range()),1);
    Assert.assertEquals(c.compare(r2,new Range(1,2)),-1);
    // Maximum only range
    Range r3=new Range(null,Integer.valueOf(1));
    Assert.assertEquals(c.compare(r3,new Range(null,Integer.valueOf(1))),0);
    Assert.assertEquals(c.compare(r3,new Range(null,Integer.valueOf(0))),1);
    Assert.assertEquals(c.compare(r3,new Range(Integer.valueOf(1),null)),-1);
    Assert.assertEquals(c.compare(r3,new Range()),1);
    Assert.assertEquals(c.compare(r3,new Range(1,2)),-1);
    // Standard range
    Range r4=new Range(1,2);
    Assert.assertEquals(c.compare(r4,new Range(1,2)),0);
    Assert.assertEquals(c.compare(r4,new Range(null,Integer.valueOf(2))),1);
    Assert.assertEquals(c.compare(r4,new Range(Integer.valueOf(1),null)),1);
    Assert.assertEquals(c.compare(r4,new Range()),1);
    Assert.assertEquals(c.compare(r4,new Range(2,1)),-1);
  }

}
