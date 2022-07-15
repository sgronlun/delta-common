package delta.common.utils.expressions.logical;

import java.util.ArrayList;
import java.util.List;

import delta.common.utils.collections.filters.Operator;

/**
 * Compound element of a boolean logical expression (operator AND/OR).
 * @param <T> Type of managed data.
 * @author DAM
 */
public class CompoundLogicalTreeNode<T> extends LogicalTreeNode<T>
{
  private Operator _operator;
  private List<LogicalTreeNode<T>> _items;

  /**
   * Constructor.
   * @param operator Operator.
   */
  public CompoundLogicalTreeNode(Operator operator)
  {
    super();
    _operator=operator;
    _items=new ArrayList<LogicalTreeNode<T>>();
  }

  /**
   * Get the logical operator.
   * @return a logical operator.
   */
  public Operator getOperator()
  {
    return _operator;
  }

  /**
   * Add a new item.
   * @param item Item to add.
   */
  public void addItem(LogicalTreeNode<T> item)
  {
    _items.add(item);
  }

  /**
   * Replace a child condition.
   * @param oldOne Old one.
   * @param newOne New one.
   */
  public void replace(LogicalTreeNode<T> oldOne, LogicalTreeNode<T> newOne)
  {
    for(int i=0;i<_items.size();i++)
    {
      if (_items.get(i)==oldOne)
      {
        _items.set(i,newOne);
      }
    }
  }

  /**
   * Get the managed items.
   * @return A list of the managed items.
   */
  public List<LogicalTreeNode<T>> getItems()
  {
    return new ArrayList<LogicalTreeNode<T>>(_items);
  }

  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append(_operator).append(' ');
    sb.append(getItems());
    return sb.toString();
  }
}
