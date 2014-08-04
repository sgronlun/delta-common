package delta.common.utils.tables;

import java.util.Comparator;

/**
 * Represents a column in a data table.
 * @param <E> Type of the items in this column. 
 * @author DAM
 */
public class DataTableColumn<E>
{
  private int _index;
  private String _name;
  private Comparator<E> _comparator;

  /**
   * Package protected constructor.
   * @param index Index of this column.
   * @param name Name of this column.
   * @param comparator Comparator to use to sort items in this column.
   */
  DataTableColumn(int index, String name, Comparator<E> comparator)
  {
    _index=index;
    _name=name;
    _comparator=comparator;
  }

  /**
   * Get the index of this column (starting at 0).
   * @return a column index.
   */
  public int getIndex()
  {
    return _index;
  }

  /**
   * Get the name of this column.
   * @return a name.
   */
  public String getName()
  {
    return _name;
  }

  /**
   * Set the name of this column.
   * @param name Name to set.
   */
  public void setName(String name)
  {
    _name=name;
  }

  /**
   * Get the comparator associated to this column.
   * @return A comparator for the managed type.
   */
  public Comparator<E> getComparator()
  {
    return _comparator;
  }

  /**
   * Set the comparator to use in this column.
   * @param comparator Comparator to set.
   */
  public void setComparator(Comparator<E> comparator)
  {
    _comparator=comparator;
  }
}
