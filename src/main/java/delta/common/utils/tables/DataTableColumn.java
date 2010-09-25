package delta.common.utils.tables;

import java.util.Comparator;

/**
 * @author DAM
 */
public class DataTableColumn<E>
{
  private int _index;
  private String _name;
  private Comparator<E> _comparator;

  DataTableColumn(int index, String name, Comparator<E> comparator)
  {
    _index=index;
    _name=name;
    _comparator=comparator;
  }

  public int getIndex()
  {
    return _index;
  }

  public String getName()
  {
    return _name;
  }

  public void setName(String name)
  {
    _name=name;
  }

  public Comparator<E> getComparator()
  {
    return _comparator;
  }

  public void setComparator(Comparator<E> comparator)
  {
    _comparator=comparator;
  }
}
