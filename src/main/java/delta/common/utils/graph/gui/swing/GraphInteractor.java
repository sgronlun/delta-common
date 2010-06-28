package delta.common.utils.graph.gui.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

import delta.common.utils.graph.Graph;
import delta.common.utils.graph.GraphNode;

/**
 * Mouse interactor used to move graph nodes.
 * @author DAM
 * @param <N> Node data type
 * @param <L> Link data type
 */
public class GraphInteractor<N,L> extends MouseInputAdapter
{
  private static final Color COLOR=Color.WHITE;

  /**
   * Associated graph canvas.
   */
  private GraphCanvas<N,L> _canvas;

	/**
   * Interactor's ghost for the moving node (not <code>null</code> when moving a node, <code>null</code> otherwise.
	 */
  private Rectangle _currentGhost;

  /**
   *
   */
  private Dimension _delta;

  /**
   * Currently selected node or <code>null</code>.
   */
	private GraphNode<N> _selectedNode;

	/**
   * Constructor.
   * @param canvas associated graph panel.
	 */
  GraphInteractor(GraphCanvas<N,L> canvas)
	{
		_canvas=canvas;
    _currentGhost=null;
    _delta=null;
    _selectedNode=null;
	}

  /**
   * Handle a mouse dragged event.
   * @param event event to handle.
   */
  @Override
  public void mouseDragged(MouseEvent event)
  {
    Graphics g=_canvas.getGraphics();
    if (_currentGhost!=null)
    {
      g.setXORMode(COLOR);
      g.drawRect(_currentGhost.x,_currentGhost.y,_currentGhost.width,_currentGhost.height);
      _currentGhost.x=event.getX()-_delta.width;
      _currentGhost.y=event.getY()-_delta.height;
      //g.setXORMode(Color.GRAY);
      g.drawRect(_currentGhost.x,_currentGhost.y,_currentGhost.width,_currentGhost.height);
    }
    else
    {
      Graph<N,L> graph=_canvas.getGraph();
      Dimension eventPosition=new Dimension(event.getX(),event.getY());
      eventPosition=_canvas.getTransformer().inverseTransform(eventPosition);
      _selectedNode=graph.getNodeFromPoint(eventPosition.width,eventPosition.height);
    	if (_selectedNode!=null)
    	{
    	  Rectangle nodeNox=graph.getNodeBox(_selectedNode.getId());
    	  nodeNox=_canvas.getTransformer().transform(nodeNox);
    		_currentGhost=new Rectangle(nodeNox);
    		_delta=new Dimension(event.getX()-_currentGhost.x,event.getY()-_currentGhost.y);
        g.setXORMode(COLOR);
    		g.drawRect(_currentGhost.x,_currentGhost.y,_currentGhost.width,_currentGhost.height);
    	}
    }
  }

  /**
   * Handle a mouse released event.
   * @param event event to handle.
   */
  @Override
  public void mouseReleased(MouseEvent event)
  {
    Graph<N,L> graph=_canvas.getGraph();
    if (_currentGhost!=null)
    {
      Graphics g=_canvas.getGraphics();
      g.setXORMode(COLOR);
      g.drawRect(_currentGhost.x,_currentGhost.y,_currentGhost.width,_currentGhost.height);
      graph.selectNode(_selectedNode.getId());
      Dimension d=new Dimension(_currentGhost.x,_currentGhost.y);
      d=_canvas.getTransformer().inverseTransform(d);
      graph.setNodePosition(_selectedNode.getId(),d);
    	graph.selectNode(_selectedNode.getId());
      _currentGhost=null;
      _delta=null;
      _selectedNode=null;
    	_canvas.repaint();
    }
    else
    {
	    GraphNode<N> node=_canvas.getNodeFromPoint(event.getX(),event.getY());
	    if (node!=null)
	    {
	    	graph.selectNode(node.getId());
	    }
	    else
	    {
	    	graph.resetSelection();
	    }
    	_canvas.repaint();
    }
  }
}
