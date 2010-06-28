package delta.common.utils.collections.graph;

/**
 * Represents a link between two nodes in a graph.
 * @author DAM
 * @param <E> Type of objects managed by the link.
 */
public class GraphLink<E>
{
  private E _data;

  /**
   * Constructor.
   * @param data Data object associated with this link.
   */
  public GraphLink(E data)
  {
    _data=data;
  }

  /**
   * Get the data object associated with this link.
   * @return the data object associated with this link.
   */
  public E getData()
  {
    return _data;
  }

  /**
   * Set the data object associated with this link.
   * @param data Data object to set.
   */
  public void setData(E data)
  {
    _data=data;
  }

  /**
   * Get a stringified representation of this link.
   * @return a stringified representation of this link.
   */
  @Override
  public String toString()
  {
    if(_data==null)
    {
      return "";
    }
    return _data.toString();
  }
}
