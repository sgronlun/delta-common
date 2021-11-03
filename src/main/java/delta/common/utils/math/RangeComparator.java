package delta.common.utils.math;

import java.util.Comparator;

/**
 * A comparator for ranges, that sorts by min value, then by max value.
 * @author DAM
 */
public class RangeComparator implements Comparator<Range>
{
  public int compare(Range o1, Range o2)
  {
    Integer min1=o1.getMin();
    Integer min2=o2.getMin();
    int comp=compare(min1,min2);
    if (comp!=0)
    {
      return comp;
    }
    Integer max1=o1.getMax();
    Integer max2=o2.getMax();
    return compare(max1,max2);
  }

  private static int compare(Integer min1, Integer min2)
  {
    if (min1==null)
    {
      return (min2==null)?0:-1;
    }
    return (min2==null)?1:min1.compareTo(min2);
  }
}
