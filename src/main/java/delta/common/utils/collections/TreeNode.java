package delta.common.utils.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a node of a tree.
 * @author DAM
 * @param <E> Type of data managed by each node.
 */
public class TreeNode<E>
{
  private E _data;
  private TreeNode<E> _superNode;
  private TreeNode<E> _firstChild;
  private TreeNode<E> _previousBrother;
  private TreeNode<E> _nextBrother;

  /**
   * Constructor.
   * @param data Data associated with this node.
   */
  public TreeNode(E data)
  {
    _data=data;
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
   * Add a new child to this node.
   * @param data Data for the newly created node.
   * @return The newly created node.
   */
  public TreeNode<E> addChild(E data)
  {
    TreeNode<E> ret=new TreeNode<E>(data);
    addChildNode(ret);
    return ret;
  }

  /**
   * Register a new child node.
   * @param child Child node to register.
   */
  private void addChildNode(TreeNode<E> child)
  {
    child._superNode=this;
    if(_firstChild==null)
    {
      _firstChild=child;
    }
    else
    {
      TreeNode<E> lastChild=_firstChild;
      while(lastChild._nextBrother!=null)
      {
        lastChild=lastChild._nextBrother;
      }
      lastChild._nextBrother=child;
      child._previousBrother=lastChild;
    }
  }

  /**
   * Add a new brother node to this one. The newly created node has the same parent node
   * and is just before this one in the parent's list of childs.
   * @param data Data associated to the new node.
   * @return The newly created node.
   */
  public TreeNode<E> addOlderBrother(E data)
  {
    TreeNode<E> newNode=new TreeNode<E>(data);
    newNode._superNode=_superNode;
    newNode._previousBrother=_previousBrother;
    newNode._nextBrother=this;
    if(_previousBrother!=null)
    {
      _previousBrother._nextBrother=newNode;
    }
    else
    {
      _superNode._firstChild=newNode;
    }
    _previousBrother=newNode;
    return newNode;
  }

  /**
   * Add a new brother node to this one. The newly created node has the same parent node
   * and is just after this one in the parent's list of childs.
   * @param data Data associated to the new node.
   * @return The newly created node.
   */
  public TreeNode<E> addYoungerBrother(E data)
  {
    TreeNode<E> newNode=new TreeNode<E>(data);
    newNode._superNode=_superNode;
    newNode._nextBrother=_nextBrother;
    newNode._previousBrother=this;
    if(_nextBrother!=null)
    {
      _nextBrother._previousBrother=newNode;
    }
    _nextBrother=newNode;
    return newNode;
  }

  /**
   * Reparent this node. This node is removed from his parent's list of child,
   * and added in the specified node's child list.
   * @param newSuperNode New parent.
   */
  public void changeSuperNode(TreeNode<E> newSuperNode)
  {
    detachFromSuperNode();
    newSuperNode.addChildNode(this);
  }

  /**
   * Get the number of children of this node.
   * @return the number of children of this node.
   */
  public int getNumberOfChildren()
  {
    int ret=0;
    TreeNode<E> child=_firstChild;
    while(child!=null)
    {
      ret++;
      child=child._nextBrother;
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
    TreeNode<E> child=_firstChild;
    while(child!=null)
    {
      ret++;
      ret+=child.getNumberOfDescendants();
      child=child._nextBrother;
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
      if(_superNode._firstChild==this)
      {
        _superNode._firstChild=_nextBrother;
      }
    }
    _superNode=null;
    if(_previousBrother!=null)
    {
      _previousBrother._nextBrother=_nextBrother;
    }
    if(_nextBrother!=null)
    {
      _nextBrother._previousBrother=_previousBrother;
    }
    _previousBrother=null;
    _nextBrother=null;
  }

  /**
   * Get the parent node.
   * @return the parent node.
   */
  public TreeNode<E> getSuperNode()
  {
    return _superNode;
  }

  /**
   * Get the first child of this node.
   * @return the first child of this node.
   */
  public TreeNode<E> getFirstChild()
  {
    return _firstChild;
  }

  /**
   * Get a child of this node.
   * @param index Index of the desired child in this node's childs list.
   * @return A node or <code>null</code>.
   */
  public TreeNode<E> getChild(int index)
  {
    TreeNode<E> tmp=_firstChild;
    for(int i=0;i<index;i++)
    {
      if (tmp!=null)
      {
        tmp=tmp._nextBrother;
      }
      else
      {
        tmp=null;
        break;
      }
    }
    return tmp;
  }

  /**
   * Get the index of specified child in this node's childs list.
   * @param node Targeted node.
   * @return A node index (starting at 0) or <code>-1</code> if not found.
   */
  public int getChildIndex(TreeNode<E> node)
  {
    int ret=-1;
    TreeNode<E> current=_firstChild;
    int index=0;
    while (current!=null)
    {
      if (current==node)
      {
        ret=index;
        break;
      }
      current=current.getNextBrother();
      index++;
    }
    return ret;
  }

  /**
   * Get the previous brother of this node.
   * @return the previous brother of this node.
   */
  public TreeNode<E> getPreviousBrother()
  {
    return _previousBrother;
  }

  /**
   * Get the next brother of this node.
   * @return the next brother of this node.
   */
  public TreeNode<E> getNextBrother()
  {
    return _nextBrother;
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
    return(_firstChild!=null);
  }

  /**
   * Indicates if this node is the only child of his parent.
   * @return <code>true</code> if it is, <code>false</code> if is has no parent,
   * or if it has at least one brother.
   */
  public boolean isOnlyChild()
  {
    return((_superNode!=null)&&(_superNode._firstChild==this)&&(_nextBrother==null));
  }

  /**
   * Sort the child nodes of this node using the specified comparator.
   * @param comparator Comparator to use.
   * @param recursive <code>true</code> for recursive sort, <code>false</code> otherwise.
   */
  public void sortChildren(Comparator<E> comparator, boolean recursive)
  {
    List<E> children=getChildren();
    int nbChildren=children.size();
    if (nbChildren>0)
    {
      List<TreeNode<E>> childNodes=getChildNodes();
      if (nbChildren>1)
      {
        Collections.sort(children,comparator);
        for(TreeNode<E> childNode : childNodes)
        {
          childNode.detachFromSuperNode();
        }
        for(int i=0;i<nbChildren;i++)
        {
          E data=children.get(i);
          for(TreeNode<E> childNode : childNodes)
          {
            if (childNode._data==data)
            {
              addChildNode(childNode);
              break;
            }
          }
        }
      }
      if (recursive)
      {
        for(TreeNode<E> childNode : childNodes)
        {
          childNode.sortChildren(comparator,true);
        }
      }
    }
  }

  /**
   * Get all children data items.
   * @return a list of data items.
   */
  public List<E> getChildren()
  {
    List<E> ret=new ArrayList<E>();
    TreeNode<E> current=_firstChild;
    while (current!=null)
    {
      ret.add(current._data);
      current=current._nextBrother;
    }
    return ret;
  }

  /**
   * Get all child nodes.
   * @return a list of child nodes.
   */
  public List<TreeNode<E>> getChildNodes()
  {
    List<TreeNode<E>> ret=new ArrayList<TreeNode<E>>();
    TreeNode<E> current=_firstChild;
    while (current!=null)
    {
      ret.add(current);
      current=current._nextBrother;
    }
    return ret;
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
