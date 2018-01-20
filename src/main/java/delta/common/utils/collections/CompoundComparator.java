package delta.common.utils.collections;

import java.util.Comparator;
import java.util.List;

/**
 * Compound comparator: a comparator that uses a series of comparator.
 * @param <T> Type of compared objects.
 * @author DAM
 */
public class CompoundComparator<T> implements Comparator<T>
{
  private List<Comparator<T>> _comparators;

  /**
   * Constructor.
   * @param comparators Comparators to use.
   */
  public CompoundComparator(List<Comparator<T>> comparators)
  {
    _comparators=comparators;
  }

  public int compare(T o1, T o2)
  {
    for(Comparator<T> comparator : _comparators)
    {
      int ret=comparator.compare(o1,o2);
      if (ret!=0)
      {
        return ret;
      }
    }
    return 0;
  }
}
