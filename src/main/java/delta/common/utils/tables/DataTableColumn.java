package delta.common.utils.tables;

import java.util.Comparator;

/**
 * @author DAM
 */
public class DataTableColumn
{
  private int _index;
  private String _name;
  private Comparator<Object> _comparator;

  DataTableColumn(int index, String name, Comparator<Object> comparator)
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

  public Comparator<Object> getComparator()
  {
    return _comparator;
  }

  public void setComparator(Comparator<Object> comparator)
  {
    _comparator=comparator;
  }
}
