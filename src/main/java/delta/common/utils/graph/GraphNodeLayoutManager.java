package delta.common.utils.graph;

import java.awt.Dimension;

/**
 * A simple layout manager for the nodes of a graph.
 * This simple layout manager divides the graph space in a series of square cells.
 * <p>
 * It places a 'main' node on the first line, and all the other nodes on another line, one after another.
 * <p>
 * Such a line cannot contains more than a constant number of nodes. The following nodes are places on the next line.
 * @author DAM
 */
public class GraphNodeLayoutManager
{
  /**
   * Horizontal index of main node's cell.
   */
  private static final int MAIN_NODE_X=2;

  /**
   * Line index of main node's cell.
   */
  private static final int MAIN_NODE_Y=0;

  /**
   * Number of lines between the main node's line and the first line of other nodes.
   */
  private static final int ROW_STEP=2;

  /**
   * Maximum number of nodes in a single line.
   */
  private static final int MAX_NODES_IN_ROW=5;

  /**
   * ID of main node.
   */
  private String _mainNodeId;

  /**
   * Current horizontal index for the next incoming node.
   */
  private int _currentX;

  /**
   * Current vertical index for the next incoming node.
   */
  private int _currentY;

  /**
   * Constructor.
   */
  public GraphNodeLayoutManager()
	{
		reset("");
	}

  /**
   * Reset this layout manager using a new ID for the main node.
   * @param mainNodeId main node's ID.
   */
  public void reset(String mainNodeId)
	{
		if (mainNodeId==null) mainNodeId="";
		_mainNodeId=mainNodeId;
		_currentX=0;
		_currentY=ROW_STEP;
	}

  /**
   * Layout a single node.
   * @param nodeId ID of node to layout.
   * @return node's position.
   */
	public Dimension layoutNode(String nodeId)
	{
		int x=0,y=0;
		if (_mainNodeId.equals(nodeId))
		{
			x=MAIN_NODE_X; y=MAIN_NODE_Y;
		}
		else
		{
			x=_currentX;
			y=_currentY;
			_currentX++;
			if (_currentX==MAX_NODES_IN_ROW)
			{
				_currentY+=ROW_STEP;
				_currentX=0;
			}
		}
		return new Dimension(x*GraphConstants.STD_SIZE,y*GraphConstants.STD_SIZE);
	}
}
