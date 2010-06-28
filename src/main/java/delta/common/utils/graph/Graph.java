package delta.common.utils.graph;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a graph. Such a graph is made of nodes and links. It manages :
 * <ul>
 * <li>a set of nodes,
 * <li>a position and size for each node,
 * <li>a list of links (each link starts from one node and terminates in
 * another one),
 * <li>a subset of nodes : the selected nodes
 * </ul>
 * @author DAM
 * @param <N> Node data type
 * @param <L> Link data type
 * @see GraphNode
 * @see GraphLink
 */
public class Graph<N,L>
{
  /**
   * Oriented links or not.
   */
  private boolean _oriented;

  /**
   * Map node IDs to nodes.
   */
  private Map<String,GraphNode<N>> _nodes;

  /**
   * Map node IDs to node positions.
   */
  private Map<String,Dimension> _nodePositions;

  /**
   * Map node IDs to node sizes.
   */
  private Map<String,Dimension> _nodeSizes;

  /**
   * List of links between nodes.
   */
  private List<GraphLink<L>> _links;

  /**
   * Bounding box of the graph, that is of all nodes.
   */
  private Rectangle _boundingBox;

  /**
   * Set of selected nodes IDs.
   */
  private Set<String> _selectedNodes;

  /**
   * Default constructor.
   */
  public Graph()
  {
    this(true);
  }

  /**
   * Constructor for en empty graph.
   * @param oriented <code>true</code> for oriented links, <code>false</code>
   * for non-oriented links.
   */
  public Graph(boolean oriented)
  {
    _oriented=oriented;
    _nodes=new HashMap<String,GraphNode<N>>();
    _links=new ArrayList<GraphLink<L>>();
    _nodePositions=new HashMap<String,Dimension>();
    _nodeSizes=new HashMap<String,Dimension>();
    _boundingBox=new Rectangle();
    _selectedNodes=new HashSet<String>();
  }

  /**
   * Clear the graph.
   */
  public void reset()
  {
    resetLinks();
    _nodes.clear();
    _nodePositions.clear();
    _boundingBox.setBounds(0,0,0,0);
    _selectedNodes.clear();
  }

  /**
   * Clear links only.
   */
  public void resetLinks()
  {
    _links.clear();
  }

  /**
   * Indicates if this graph stored oriented links or not.
   * @return <code>true</code> for oriented links, <code>false</code> for
   * non-oriented links.
   */
  public boolean isOriented()
  {
    return _oriented;
  }

  /**
   * Register a node. If a node pre-exists with the same ID, it is previously
   * removed.
   * @param node node to register into this graph.
   * @param position position of this node.
   * @param size size of this node.
   */
  public void addNode(GraphNode<N> node, Dimension position, Dimension size)
  {
    _nodes.put(node.getId(),node);
    _nodePositions.put(node.getId(),new Dimension(position));
    _nodeSizes.put(node.getId(),new Dimension(size));
    Rectangle nodeBox=new Rectangle(position.width,position.height,size.width,size.height);
    _boundingBox=_boundingBox.union(nodeBox);
  }

  /**
   * Get a node's bounding box.
   * @param id node's ID.
   * @return a new rectangle.
   */
  public Rectangle getNodeBox(String id)
  {
    Dimension position=getNodePosition(id);
    Dimension size=getNodeSize(id);
    if ((position!=null)&&(size!=null)) return new Rectangle(position.width,position.height,size.width,size.height);
    return null;
  }

  /**
   * Get a node's position.
   * @param id node's ID.
   * @return node's position.
   */
  public Dimension getNodePosition(String id)
  {
    return _nodePositions.get(id);
  }

  /**
   * Set a node's position.
   * @param id node's ID.
   * @param newPosition new position for this node.
   */
  public void setNodePosition(String id, Dimension newPosition)
  {
    Dimension d=_nodePositions.get(id);
    if (d!=null)
    {
      d.width=newPosition.width;
      d.height=newPosition.height;
    }
  }

  /**
   * Get a node's size.
   * @param id node's ID.
   * @return the node's size.
   */
  public Dimension getNodeSize(String id)
  {
    return _nodeSizes.get(id);
  }

  /**
   * Register a new link.
   * @param link to register.
   */
  public void addLink(GraphLink<L> link)
  {
    _links.add(link);
  }

  /**
   * Get an iterator over registered links.
   * @return an iterator over registered links.
   */
  public Iterator<GraphLink<L>> getLinks()
  {
    return _links.iterator();
  }

  /**
   * Get a link described by two node IDs.
   * @param id1 a node ID.
   * @param id2 another nodeID.
   * @return found link or <code>null</code>.
   */
  public GraphLink<L> getLink(String id1, String id2)
  {
    int nbLinks=_links.size();
    GraphLink<L> link;
    for(int i=0;i<nbLinks;i++)
    {
      link=_links.get(i);
      if (((link.getFromId().equals(id1))&&(link.getToId().equals(id2)))
          ||((link.getFromId().equals(id2))&&(link.getToId().equals(id1)))) return link;
    }
    return null;
  }

  /**
   * Remove all the links that use a specific node.
   * @param nodeId node's ID.
   */
  private void removeLinksUsingNode(String nodeId)
  {
    GraphLink<L> link;
    for(Iterator<GraphLink<L>> it=_links.iterator();it.hasNext();)
    {
      link=it.next();
      if ((link.getFromId().equals(nodeId))||(link.getToId().equals(nodeId))) it.remove();
    }
  }

  /**
   * Get a node by it's ID.
   * @param id node's ID.
   * @return found node or <code>null</code>.
   */
  public GraphNode<N> getNode(String id)
  {
    return _nodes.get(id);
  }

  /**
   * Get node at point.
   * @param x horizontal coordinate.
   * @param y vertical coordinate.
   * @return found node or <code>null</code>.
   */
  public GraphNode<N> getNodeFromPoint(int x, int y)
  {
    Set<String> nodeIds=_nodes.keySet();
    String id;
    Dimension where;
    Dimension size;
    for(Iterator<String> it=nodeIds.iterator();it.hasNext();)
    {
      id=it.next();
      where=_nodePositions.get(id);
      size=_nodeSizes.get(id);
      if (((x>=where.width)&&(x<where.width+size.width))&&((y>=where.height)&&(y<where.height+size.height)))
      {
        return _nodes.get(id);
      }
    }
    return null;
  }

  /**
   * Get a set of all node IDs.
   * @return a set of all node IDs.
   */
  public Set<String> getNodeIds()
  {
    return _nodes.keySet();
  }

  /**
   * Remove a node. All links using this node are also removed.
   * @param nodeId ID of node to remove.
   */
  public void removeNode(String nodeId)
  {
    GraphNode<N> node=_nodes.remove(nodeId);
    if (node!=null)
    {
      _nodePositions.remove(nodeId);
      _nodeSizes.remove(nodeId);
      removeLinksUsingNode(nodeId);
    }
  }

  /**
   * Get the bounding box for the graph. It includes the bounding box for all
   * the nodes.
   * @return a new rectangle.
   */
  public Rectangle getBoundingBox()
  {
    return _boundingBox;
  }

  /**
   * Reset selection to an empty set.
   */
  public void resetSelection()
  {
    _selectedNodes.clear();
  }

  /**
   * Indicates if a node is selected or not.
   * @param nodeId node's ID
   * @return <code>true</code> if selected, <code>false</code> otherwise.
   */
  public boolean isNodeSelected(String nodeId)
  {
    return _selectedNodes.contains(nodeId);
  }

  /**
   * Select a node.
   * @param nodeId ID of the node to select.
   */
  public void selectNode(String nodeId)
  {
    if ((_selectedNodes.size()==1)&&(_selectedNodes.contains(nodeId)))
    {
      // Nothing to do !
    }
    else
    {
      _selectedNodes.clear();
      _selectedNodes.add(nodeId);
    }
  }
}
