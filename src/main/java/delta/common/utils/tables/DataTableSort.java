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
    addSort(columnName,true);
  }

  /**
   * Build a data table sort from a sort definition given as a string.
   * @param sortDefinition Sort definition.
   * @return A data table sort.
   */
  public static DataTableSort buildSortFromString(String sortDefinition)
  {
    String[] sortItems=StringSplitter.split(sortDefinition,SORT_ITEMS_SEPARATOR);
    DataTableSort sort=new DataTableSort();
    String sortItem;
    boolean order;
    for(int i=0;i<sortItems.length;i++)
    {
      sortItem=sortItems[i];
      if (sortItem.startsWith(SORT_INVERSE))
      {
        order=false;
        sortItem=sortItem.substring(SORT_INVERSE.length());
      }
      else
      {
        order=true;
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

  /**
   * Add a sort item.
   * @param columnName Name of the column to use.
   * @param order 
   */
  public void addSort(String columnName, boolean order)
  {
    _columns.add(columnName);
    _orders.add(Boolean.valueOf(order));
  }

  /**
   * Get the number of sort items in this sort definition.
   * @return A number of columns.
   */
  public int getNbColumns()
  {
    return _columns.size();
  }

  /**
   * Get the sort order for a given sort item.
   * @param index Index of the targeted sort item.
   * @return <code>true</code> for natural order, <code>false</code> for reverse order.
   */
  public boolean getColumnOrder(int index)
  {
    return _orders.get(index).booleanValue();
  }

  /**
   * Get the name of the column for a given sort item.
   * @param index Index of the targeted sort item.
   * @return A column name.
   */
  public String getColumnName(int index)
  {
    return _columns.get(index);
  }
}
