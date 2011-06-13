package delta.common.utils.tables;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DAM
 */
public class DataTable
{
  private Map<String,DataTableColumn<?>> _columnsMap;
  private List<DataTableColumn<?>> _columns;
  private List<DataTableRow> _rows;

  public DataTable()
  {
    _columnsMap=new HashMap<String,DataTableColumn<?>>();
    _columns=new ArrayList<DataTableColumn<?>>();
    _rows=new ArrayList<DataTableRow>();
  }

  public <E extends Comparable<E>> DataTableColumn<E> addColumn(String name, Class<E> classOfObjects)
  {
    DataTableColumn<E> column=getColumnByName(name);
    if (column==null)
    {
      int index=_columnsMap.size();
      Comparator<E> comparator=new ComparableComparator<E>();
      column=new DataTableColumn<E>(index,name,comparator);
      _columnsMap.put(name,column);
      _columns.add(column);
    }
    else
    {
      //todo warning Column already exists
    }
    return column;
  }

  public <E> DataTableColumn<E> addColumn(String name, Class<E> classOfObjects, Comparator<E> comparator)
  {
    DataTableColumn<E> column=getColumnByName(name);
    if (column==null)
    {
      int index=_columnsMap.size();
      column=new DataTableColumn<E>(index,name,comparator);
      _columnsMap.put(name,column);
      _columns.add(column);
    }
    else
    {
      //todo warning Column already exists
    }
    return column;
  }

  public DataTableColumn getColumn(int index)
  {
    return _columns.get(index);
  }

  public DataTableColumn getColumnByName(String columnName)
  {
    return _columnsMap.get(columnName);
  }

  public int getNbColumns()
  {
    return _columns.size();
  }

  public int getNbRows()
  {
    return _rows.size();
  }

  public boolean setData(int rowIndex, int columnIndex, Object data)
  {
    DataTableRow row=getRow(rowIndex);
    if (row!=null)
    {
      int nbColumns=_columns.size();
      if ((columnIndex<0) || (columnIndex>=nbColumns))
      {
        return false;
      }
      row.setData(columnIndex,data);
    }
    return false;
  }

  public DataTableRow addRow()
  {
    int nbColumns=_columns.size();
    DataTableRow row=new DataTableRow(nbColumns);
    _rows.add(row);
    return row;
  }

  public Object getData(int rowIndex, int columnIndex)
  {
    DataTableRow row=getRow(rowIndex);
    if (row!=null)
    {
      Object ret=row.getData(columnIndex);
      return ret;
    }
    return null;
  }

  public DataTableRow getRow(int rowIndex)
  {
    if ((rowIndex<0) || (rowIndex>=_rows.size()))
    {
      return null;
    }
    DataTableRow row=_rows.get(rowIndex);
    return row;
  }

  public void sort(String name, boolean reverse)
  {
    DataTableComparator comparator=new DataTableComparator(this,name,reverse);
    Collections.sort(_rows,comparator);
  }

  public void sort(DataTableSort sort)
  {
    int nbColumns=sort.getNbColumns();
    String name;
    Boolean order;
    boolean reverse;
    for(int i=nbColumns-1;i>=0;i--)
    {
      name=sort.getColumnName(i);
      order=sort.getColumnOrder(i);
      reverse=!order.booleanValue();
      sort(name,reverse);
    }
  }

  public void dump(PrintStream ps)
  {
    int nbRows=getNbRows();
    DataTableRow row;
    for(int i=0;i<nbRows;i++)
    {
      row=getRow(i);
      row.dump(ps);
    }
  }
}
