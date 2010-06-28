package delta.common.utils.collections;

/**
 * Represents a node of a binary tree.
 * @author DAM
 * @param <E> Type of data managed by each node.
 */
public class BinaryTreeNode<E>
{
  /**
   * Parent node (if any).
   */
  private BinaryTreeNode<E> _superNode;
  /**
   * Left child (if any).
   */
  private BinaryTreeNode<E> _leftNode;
  /**
   * Right child (if any).
   */
  private BinaryTreeNode<E> _rightNode;
  private E _data;

  /**
   * Constructor.
   * @param data Data associated with this node.
   */
  public BinaryTreeNode(E data)
  {
    _superNode=null;
    _data=data;
  }

  /**
   * Full constructor.
   * @param superNode Parent node.
   * @param data Data associated with this node.
   */
  public BinaryTreeNode(BinaryTreeNode<E> superNode, E data)
  {
    _superNode=superNode;
    _data=data;
  }

  /**
   * Get the left child of this node.
   * @return the left child of this node.
   */
  public BinaryTreeNode<E> getLeftNode()
  {
    return _leftNode;
  }

  /**
   * Get the data associated to the left child of this node (if any).
   * @return Some data or <code>null</code> if it has no left child.
   */
  public E getLeftData()
  {
    if(_leftNode!=null)
    {
      return _leftNode.getData();
    }
    return null;
  }

  /**
   * Get the right child of this node.
   * @return the right child of this node.
   */
  public BinaryTreeNode<E> getRightNode()
  {
    return _rightNode;
  }

  /**
   * Get the data associated to the right child of this node (if any).
   * @return Some data or <code>null</code> if it has no right child.
   */
  public E getRightData()
  {
    if(_rightNode!=null)
    {
      return _rightNode.getData();
    }
    return null;
  }

  /**
   * Get the data associated with this node.
   * @return the data associated with this node.
   */
  public E getData()
  {
    return _data;
  }

  /**
   * Set the data associated with this node.
   * @param data Data to set.
   */
  public void setData(E data)
  {
    _data=data;
  }

  /**
   * Get the parent node.
   * @return the parent node.
   */
  public BinaryTreeNode<E> getSuperNode()
  {
    return _superNode;
  }

  /**
   * Indicates if this node has a parent or not.
   * @return <code>true</code> if it has a parent, <code>false</code> otherwise.
   */
  public boolean isRoot()
  {
    return(_superNode==null);
  }

  /**
   * Indicates if the node has at least one child.
   * @return <code>true</code> if it has, <code>false</code> otherwise.
   */
  public boolean hasChild()
  {
    return((_leftNode!=null)&&(_rightNode!=null));
  }

  /**
   * Get the number of children of this node.
   * @return the number of children of this node.
   */
  public int getNumberOfChildren()
  {
    int ret=0;
    if(_leftNode!=null)
    {
      ret++;
    }
    if(_rightNode!=null)
    {
      ret++;
    }
    return ret;
  }

  /**
   * Get the number of descendants of this node.
   * @return the number of descendants of this node.
   */
  public int getNumberOfDescendants()
  {
    int ret=0;
    if(_leftNode!=null)
    {
      ret++;
      ret+=_leftNode.getNumberOfDescendants();
    }
    if(_rightNode!=null)
    {
      ret++;
      ret+=_rightNode.getNumberOfDescendants();
    }
    return ret;
  }

  /**
   * Detach this node from his parent node.
   */
  public void detachFromSuperNode()
  {
    if(_superNode!=null)
    {
      if(_superNode._leftNode==this)
      {
        _superNode._leftNode=null;
      }
      if(_superNode._rightNode==this)
      {
        _superNode._rightNode=null;
      }
      _superNode=null;
    }
  }

  /**
   * Set the data for the left child node (create it if needed).
   * @param data Data to be associated to the left child.
   */
  public void setLeftData(E data)
  {
    if(_leftNode==null)
    {
      _leftNode=new BinaryTreeNode<E>(this, data);
    }
    else
    {
      _leftNode._data=data;
    }
  }

  /**
   * Set the data for the right child node (create it if needed).
   * @param data Data to be associated to the right child.
   */
  public void setRightData(E data)
  {
    if(_rightNode==null)
    {
      _rightNode=new BinaryTreeNode<E>(this, data);
    }
    else
    {
      _rightNode._data=data;
    }
  }

  /**
   * Get a stringified representation of this node's associated data.
   * @return An empty string if no data is associated to this node, or the stringified
   * representation of the associated data.
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
