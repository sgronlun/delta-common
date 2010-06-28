package delta.common.utils.collections.graph;

/**
 * A graph node that stores some typed user data.
 * @author DAM
 * @param <E> Type of user data.
 */
public class GraphNode<E>
{
  private E _data;

  /**
   * Constructor.
   * @param data User data.
   */
  public GraphNode(E data)
  {
    _data=data;
  }

  /**
   * Get user data.
   * @return the user data.
   */
  public E getData()
  {
    return _data;
  }

  /**
   * Set user data.
   * @param data the user data to store in this node.
   */
  public void setData(E data)
  {
    _data=data;
  }

  /**
   * Get a readable string for this graph node.
   * @return a readable string for this graph node.
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
