package delta.common.utils.tables;

import java.io.Serializable;
import java.util.Comparator;

/**
 * A comparator that uses comparables.
 * @author DAM
 * @param <E> Type of objects to be compared.
 */
public class ComparableComparator<E extends Comparable> implements Comparator<E>, Serializable
{
  private static final long serialVersionUID=1L;

  public int compare(E o1, E o2)
  {
    int ret=o1.compareTo(o2);
    return ret;
  }
}
