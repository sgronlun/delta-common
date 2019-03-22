package delta.common.utils.tables;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Storage for the data in a table.
 * @author DAM
 */
public class DataTable
{
  private static final Logger LOGGER=Logger.getLogger(DataTable.class);

  private Map<String,DataTableColumn<?>> _columnsMap;
  private List<DataTableColumn<?>> _columns;
  private List<DataTableRow> _rows;

  /**
   * Constructor.
   */
  public DataTable()
  {
    _columnsMap=new HashMap<String,DataTableColumn<?>>();
    _columns=new ArrayList<DataTableColumn<?>>();
    _rows=new ArrayList<DataTableRow>();
  }

  /**
   * Add a column.
   * @param name Column name.
   * @param classOfObjects Class of data items in this column.
   * @return A data column object. 
   */
  public <E extends Comparable<E>> DataTableColumn<E> addColumn(String name, Class<E> classOfObjects)
  {
    return addColumn(name,classOfObjects,new ComparableComparator<E>());
  }

  /**
   * Add a column.
   * @param name Column name.
   * @param classOfObjects Class of data items in this column.
   * @param comparator Comparator to use in this column.
   * @return A data column object. 
   */
  public <E> DataTableColumn<E> addColumn(String name, Class<E> classOfObjects, Comparator<E> comparator)
  {
    DataTableColumn<E> ret=null;
    DataTableColumn<?> column=getColumnByName(name);
    if (column==null)
    {
      int index=_columnsMap.size();
      ret=new DataTableColumn<E>(index,name,comparator);
      _columnsMap.put(name,ret);
      _columns.add(ret);
    }
    else
    {
      LOGGER.warn("Column ["+name+"] already exists in this table!");
    }
    return ret;
  }

  /**
   * Get a column using its index.
   * @param index An index, starting at zero.
   * @return A table column.
   */
  public DataTableColumn<?> getColumn(int index)
  {
    return _columns.get(index);
  }

  /**
   * Get a column using its name.
   * @param columnName Name of the targeted column.
   * @return A table column.
   */
  public DataTableColumn<?> getColumnByName(String columnName)
  {
    return _columnsMap.get(columnName);
  }

  /**
   * Get the number of columns in this table.
   * @return A number of columns.
   */
  public int getNbColumns()
  {
    return _columns.size();
  }

  /**
   * Get the number of rows in this table.
   * @return A number of rows.
   */
  public int getNbRows()
  {
    return _rows.size();
  }

  /**
   * Set data in a cell.
   * @param rowIndex Row index, starting at zero.
   * @param columnIndex Column index, starting at zero.
   * @param data Data to set.
   * @return <code>true</code> if it succeeded, <code>false</code> otherwise.
   */
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

  /**
   * Add a new row to this table.
   * @return the newly added row.
   */
  public DataTableRow addRow()
  {
    int nbColumns=_columns.size();
    DataTableRow row=new DataTableRow(nbColumns);
    _rows.add(row);
    return row;
  }

  /**
   * Get the data in a cell.
   * @param rowIndex Row index, starting at zero.
   * @param columnIndex Column index, starting at zero.
   * @return some data or <code>null</code> if out of bounds.
   */
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

  /**
   * Get a row of data.
   * @param rowIndex Index of the targeted row.
   * @return A table row or <code>null</code> if out of bounds.
   */
  public DataTableRow getRow(int rowIndex)
  {
    if ((rowIndex<0) || (rowIndex>=_rows.size()))
    {
      return null;
    }
    DataTableRow row=_rows.get(rowIndex);
    return row;
  }

  /**
   * Sort this table using the targeted column.
   * @param name Name of the column to use for sorting.
   * @param reverse <code>true</code> to reverse sort order.
   */
  public void sort(String name, boolean reverse)
  {
    DataTableComparator comparator=new DataTableComparator(this,name,reverse);
    Collections.sort(_rows,comparator);
  }

  /**
   * Sort this table using the gien sort specification.
   * @param sort Sort specification.
   */
  public void sort(DataTableSort sort)
  {
    int nbColumns=sort.getNbColumns();
    String name;
    boolean order;
    boolean reverse;
    for(int i=nbColumns-1;i>=0;i--)
    {
      name=sort.getColumnName(i);
      order=sort.getColumnOrder(i);
      reverse=!order;
      sort(name,reverse);
    }
  }

  /**
   * Dump the contents of this table to the given output stream.
   * @param ps Output stream.
   */
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
