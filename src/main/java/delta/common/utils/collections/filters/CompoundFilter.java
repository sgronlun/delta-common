package delta.common.utils.collections.filters;

import java.util.ArrayList;
import java.util.List;

/**
 * Compound filter.
 * @param <T> Filtered type.
 * @author DAM
 */
public class CompoundFilter<T> implements Filter<T>
{
  /**
   * Sub-filters.
   */
  private List<Filter<T>> _filters;
  /**
   * Logical operator.
   */
  private Operator _operator;

  /**
   * Constructor.
   * @param operator Logical operator.
   * @param filters Sub-filters.
   */
  public CompoundFilter(Operator operator, List<Filter<T>> filters)
  {
    _operator=operator;
    _filters=new ArrayList<Filter<T>>();
    _filters.addAll(filters);
  }

  public boolean accept(T item)
  {
    if (_operator==Operator.AND)
    {
      for(Filter<T> filter : _filters)
      {
        boolean ok=filter.accept(item);
        if (!ok) return false;
      }
      return true;
    }
    if (_operator==Operator.OR)
    {
      for(Filter<T> filter : _filters)
      {
        boolean ok=filter.accept(item);
        if (ok) return true;
      }
      return false;
    }
    return false;
  }
}
