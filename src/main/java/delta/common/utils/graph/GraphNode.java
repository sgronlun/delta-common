package delta.common.utils.graph;

import java.awt.Color;

/**
 * Represents a node in a graph.
 * @author DAM
 * @param <N> Node data type.
 */
public class GraphNode<N>
{
  private N _nodeData;

  /**
   * Default color for a node.
   */
  public static final Color DEFAULT_COLOR=new Color(128,128,128);

  /**
   * Node id.
   */
  private String _id;

  /**
   * Text to display in the upper part of the node.
   */
	private String _upperText;

  /**
   * Text to display in the lower part of the node.
   */
	private String _lowerText;

  /**
   * Node's color.
   */
  private Color _color;

  /**
   * Full constructor.
   * @param id node's id.
   * @param upperText text to display in the upper part of the node.
   * @param lowerText text to display in the lower part of the node.
   * @param color node's color (use <code>null</code> to use the default color).
   */
  public GraphNode(String id, String upperText, String lowerText, Color color)
	{
		_id=id;
    if (upperText==null) upperText="";
		_upperText=upperText;
    if (lowerText==null) lowerText="";
		_lowerText=lowerText;
    if (color==null) color=DEFAULT_COLOR;
    _color=color;
	}

  /**
   * Get the node's ID.
   * @return the node's ID.
   */
  public String getId() { return _id; }

  /**
   * Get the text displayed in the lower part of the node.
   * @return lower text.
   */
	public String getLowerText() { return _lowerText; }

  /**
   * Get the text displayed in the upper part of the node.
   * @return upper text.
   */
	public String getUpperText() { return _upperText; }

  /**
   * Get the node's color.
   * @return the node's color.
   */
  public Color getColor() { return _color; }

  /**
   * Set the lower text.
   * @param text the new lower text.
   */
  public void setLowerText(String text) { _lowerText=text; }

  /**
   * Set the upper text.
   * @param text the new upper text.
   */
  public void setUpperText(String text) { _upperText=text; }

  /**
   * Set the node's color.
   * @param color the new color.
   */
  public void setColor(Color color) { _color=color; }

  /**
   * Get node data.
   * @return node data.
   */
  public N getNodeData()
  {
    return _nodeData;
  }

  /**
   * Set node data.
   * @param data Node data to set.
   */
  public void setNodeData(N data)
  {
    _nodeData=data;
  }
}
