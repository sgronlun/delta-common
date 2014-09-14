package delta.common.utils.tables;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Compaator for data table rows.
 * @author DAM
 */
public class DataTableComparator implements Comparator<DataTableRow>,Serializable
{
  private static final long serialVersionUID=1L;

  private int _columnIndex;
  private Comparator<Object> _comparator;
  private boolean _reverse;

  /**
   * Constructor.
   * @param table Associated table.
   * @param columnName Name of the column to use.
   * @param reverse Sort order (<code>true</code> to reverse natural order).
   */
  @SuppressWarnings("unchecked")
  public DataTableComparator(DataTable table, String columnName, boolean reverse)
  {
    _columnIndex=-1;
    _comparator=null;
    DataTableColumn<?> column=table.getColumnByName(columnName);
    if (column!=null)
    {
      _columnIndex=column.getIndex();
      _comparator=(Comparator<Object>)column.getComparator();
    }
    _reverse=reverse;
  }

  public int compare(DataTableRow row1, DataTableRow row2)
  {
    int factor=1;
    if (_reverse) factor=-1;
    if (_columnIndex==-1) return 0;
    if (row1!=null)
    {
      if (row2==null) return -factor;
      Object o1=row1.getData(_columnIndex);
      Object o2=row2.getData(_columnIndex);
      if (o1!=null)
      {
        if (o2==null) return -factor;
        if (_comparator!=null)
        {
          int ret=_comparator.compare(o1,o2);
          return ret*factor;
        }
        return 0;
      }
      if (o2==null) return 0;
      return factor;
    }
    if (row2==null) return 0;
    return factor;
  }
}
