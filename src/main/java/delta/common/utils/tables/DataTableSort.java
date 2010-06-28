package delta.common.utils.tables;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.text.StringSplitter;

/**
 * Represents sort criteria for a single table.
 * @author DAM
 */
public class DataTableSort
{
  private static final char SORT_ITEMS_SEPARATOR=',';
  private static final String SORT_INVERSE="~";

  private List<String> _columns;
  private List<Boolean> _orders;

  /**
   * Default constructor.
   */
  public DataTableSort()
  {
    init();
  }

  /**
   * Constructor that represents a sort on a single column.
   * @param columnName Name of column to sort.
   */
  public DataTableSort(String columnName)
  {
    init();
    addSort(columnName,Boolean.TRUE);
  }

  public static DataTableSort buildSortFromString(String sortDefinition)
  {
    String[] sortItems=StringSplitter.split(sortDefinition,SORT_ITEMS_SEPARATOR);
    DataTableSort sort=new DataTableSort();
    String sortItem;
    Boolean order;
    for(int i=0;i<sortItems.length;i++)
    {
      sortItem=sortItems[i];
      if (sortItem.startsWith(SORT_INVERSE))
      {
        order=Boolean.FALSE;
        sortItem=sortItem.substring(SORT_INVERSE.length());
      }
      else
      {
        order=Boolean.TRUE;
      }
      sort.addSort(sortItem,order);
    }
    return sort;
  }

  private void init()
  {
    _columns=new ArrayList<String>();
    _orders=new ArrayList<Boolean>();
  }

  public void addSort(String columnName, Boolean order)
  {
    _columns.add(columnName);
    _orders.add(order);
  }

  public int getNbColumns()
  {
    return _columns.size();
  }

  public Boolean getColumnOrder(int index)
  {
    return _orders.get(index);
  }

  public String getColumnName(int index)
  {
    return _columns.get(index);
  }
}

