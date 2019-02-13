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

  /**
   * Constructor.
   * @param operator Logical operator.
   * @param filters Sub-filters.
   */
  @SafeVarargs
  public CompoundFilter(Operator operator, Filter<T>... filters)
  {
    _operator=operator;
    _filters=new ArrayList<Filter<T>>();
    for(Filter<T> filter : filters)
    {
      _filters.add(filter);
    }
  }

  /**
   * Add a filter.
   * @param filter Filter to add.
   */
  public void addFilter(Filter<T> filter)
  {
    if (!_filters.contains(filter))
    {
      _filters.add(filter);
    }
  }

  /**
   * Get all managed filters.
   * @return a list of all managed filters.
   */
  public List<Filter<T>> getFilters()
  {
    return new ArrayList<Filter<T>>(_filters);
  }

  /**
   * Get the managed operator.
   * @return the managed operator.
   */
  public Operator getOperator()
  {
    return _operator;
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
