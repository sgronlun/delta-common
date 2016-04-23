package delta.common.utils.collections.filters;

/**
 * Filter.
 * @param <T> Filtered type.
 * @author DAM
 */
public interface Filter<T>
{
  /**
   * Indicates if this filter accepts the given item or not.
   * @param item Item to use.
   * @return <code>true</code> to accept this item, <code>false</code> otherwise.
   */
  public boolean accept(T item);
}
