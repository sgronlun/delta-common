package delta.common.utils.tables;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author DAM
 */
public class ComparableComparator implements Comparator<Object>,Serializable
{
  private static final long serialVersionUID=1L;

  @SuppressWarnings("unchecked")
  public int compare(Object o1, Object o2)
  {
    Comparable<Object> c1=(Comparable<Object>)o1;
    int ret=c1.compareTo(o2);
    return ret;
  }
}
