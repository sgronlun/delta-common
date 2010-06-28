package delta.common.utils.graph.gui.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import delta.common.utils.graph.Graph;
import delta.common.utils.graph.GraphLink;
import delta.common.utils.graph.GraphNode;

/**
 * Graph panel.
 * @param <N> Node data type
 * @param <L> Link data type
 * @author DAM
 */
public class GraphCanvas<N,L> extends JPanel implements KeyListener
{
  /**
   * Referenced graph.
   */
  private transient Graph<N,L> _graph;

  /**
   * Zoom manager for this graph representation.
   */
  private transient ZoomTransformer _transformer;

  /**
   * Font used for normal/reference zoom level.
   */
  private Font _stdFont;

  /**
   * Font used for the current zoom level.
   */
  private Font _currentFont;

  /**
   * Scrollable view for the graph.
   */
  private JScrollPane _scroller;

  /**
   * Constructor.
   * @param graph HF network graph to manage.
   * @param scroller
   */
  public GraphCanvas(Graph<N,L> graph, JScrollPane scroller)
  {
    super();
    setBackground(Color.WHITE);
    _graph=graph;
    _transformer=new ZoomTransformer(1,1);
    _stdFont=new Font("Dialog",Font.PLAIN,12);
    _scroller=scroller;
    computeSize();
    setFocusable(true);
    addKeyListener(this);
    GraphInteractor<N,L> interactor=new GraphInteractor<N,L>(this);
    addMouseListener(interactor);
    addMouseMotionListener(interactor);
  }

  /**
   * Get referenced graph.
   * @return the displayed graph.
   */
  public Graph<N,L> getGraph()
  {
    return _graph;
  }

  /**
   * Get the managed zoom manager.
   * @return the zoom manager for this graph representation.
   */
  public ZoomTransformer getTransformer()
  {
    return _transformer;
  }

  /**
   * Change zoom factor.
   * @param newFactor new zoom factor.
   */
  public void changeZoom(float newFactor)
  {
    _transformer.changeFactor(newFactor);
    _currentFont=_stdFont.deriveFont(_stdFont.getSize()*newFactor);
    computeSize();
    revalidate();
    repaint();
  }

  /**
   * Compute and set the optimal size for this graph, depending on the zoom level.
   */
  private void computeSize()
  {
    Rectangle r=_graph.getBoundingBox();
    r=_transformer.transform(r);
    setPreferredSize(new Dimension(r.width,r.height));
  }

  /**
   * Paint this panel. It includes :
   * <ul>
   * <li>paint background,
   * <li>paint links,
   * <li>paint nodes.
   * </ul>
   * @param g Graphics to draw to.
   */
  @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    paintLinks(g);
    paintNodes(g);
  }

  /**
   * Paint network links.
   * @param graphics drawings destination.
   */
  private void paintLinks(Graphics graphics)
  {
    Graphics2D g=(Graphics2D)graphics;
    Color currentColor;
    Rectangle fromBox, toBox;
    GraphLink<L> currentLink;
    List<String> textList;
    String text;
    g.setFont(_currentFont);
    FontMetrics fm=g.getFontMetrics();
    int ascent=fm.getAscent();
    int descent=fm.getDescent();
    int leading=fm.getLeading();
    int height=ascent+descent;
    for(Iterator<GraphLink<L>> it=_graph.getLinks();it.hasNext();)
    {
      currentLink=it.next();
      fromBox=_graph.getNodeBox(currentLink.getFromId());
      toBox=_graph.getNodeBox(currentLink.getToId());
      if ((fromBox==null)||(toBox==null)) continue;
      // Zoom transformation
      fromBox=_transformer.transform(fromBox);
      toBox=_transformer.transform(toBox);
      // Draw link
      currentColor=Color.RED;
      g.setColor(currentColor);
      int startX=fromBox.x+fromBox.width/2;
      int startY=fromBox.y+fromBox.height/2;
      int endX=toBox.x+toBox.width/2;
      int endY=toBox.y+toBox.height/2;
      g.drawLine(startX,startY,endX,endY);
      // Draw text
      textList=currentLink.getText();
      if ((textList!=null)&&(textList.size()>0))
      {
        int nb=textList.size();
        g.setColor(Color.BLACK);
        // Compute global height
        int globalHeight=nb*(ascent+descent)+(nb-1)*leading;
        int baseY=(startY+endY-globalHeight)/2;
        for(Iterator<String> itText=textList.iterator();itText.hasNext();)
        {
          text=itText.next();
          int width=fm.stringWidth(text);
          int textX=(startX+endX-width)/2;
          int textY=baseY+ascent;
          g.clearRect(textX,textY-ascent,width,height);
          g.drawString(text,textX,textY);
          baseY+=leading+height;
        }
      }
    }
  }

  /**
   * Paint network nodes.
   * @param graphics drawings destination.
   */
  private void paintNodes(Graphics graphics)
  {
    Color currentColor;
    String nodeId;
    GraphNode<N> node;
    Rectangle box;
    graphics.setFont(_currentFont);
    FontMetrics fm=graphics.getFontMetrics();
    int ascent=fm.getAscent();
    int descent=fm.getDescent();
    int width;
    String text;
    for(Iterator<String> it=_graph.getNodeIds().iterator();it.hasNext();)
    {
      nodeId=it.next();
      node=_graph.getNode(nodeId);
      box=_graph.getNodeBox(nodeId);
      box=_transformer.transform(box);
      currentColor=node.getColor();
      graphics.setColor(currentColor);
      graphics.fillOval(box.x,box.y,box.width,box.height);
      graphics.setColor(Color.BLACK);
      graphics.drawOval(box.x,box.y,box.width-1,box.height-1);
      graphics.drawLine(box.x,box.y+box.height/2,box.x+box.width-1,box.y+box.height/2);
      // Draw text
      text=node.getUpperText();
      if ((text!=null)&&(text.length()>0))
      {
        graphics.setColor(Color.BLACK);
        width=fm.stringWidth(text);
        int textX=(2*box.x+box.width-width)/2;
        int textY=1+(4*box.y+box.height+2*(ascent-descent))/4;
        graphics.drawString(text,textX,textY);
      }
      text=node.getLowerText();
      if ((text!=null)&&(text.length()>0))
      {
        graphics.setColor(Color.BLACK);
        width=fm.stringWidth(text);
        int textX=(2*box.x+box.width-width)/2;
        int textY=(4*box.y+3*box.height+2*(ascent-descent))/4-1;
        graphics.drawString(text,textX,textY);
      }
      if (_graph.isNodeSelected(nodeId))
      {
        graphics.setColor(Color.BLACK);
        graphics.drawRect(box.x,box.y,box.width-1,box.height-1);
      }
    }
  }

  /**
   * Get the graph node associated with the given graphical point.
   * @param x horizontal graphical position.
   * @param y vertical graphical position.
   * @return a graph node or <code>null</code> if none found.
   */
  public GraphNode<N> getNodeFromPoint(int x, int y)
  {
    Dimension where=_transformer.inverseTransform(new Dimension(x,y));
    return _graph.getNodeFromPoint(where.width,where.height);
  }

  /**
   * Handle special zoom-related keys.
   * @param event key event to handle.
   */
  public void keyTyped(KeyEvent event)
  {
    if (event.getKeyChar()=='+')
    {
      float factor=_transformer.getXFactor()*2;
      if (factor>4) return;
      // System.out.println("************* Zoom");
      Point viewOrigin=_scroller.getViewport().getViewPosition();
      // System.out.println("View origin : "+viewOrigin);
      Dimension size=_scroller.getViewport().getExtentSize();
      // System.out.println("View size : "+size);
      Dimension d=new Dimension(viewOrigin.x+size.width/2,viewOrigin.y
          +size.height/2);
      // System.out.println("View position : "+d);
      d=_transformer.inverseTransform(d);
      // System.out.println("Model position : "+d);
      changeZoom(factor);
      d=_transformer.transform(d);
      // System.out.println("View position (transformed) : "+d);
      Rectangle toView=new Rectangle(d.width-viewOrigin.x-size.width/2,d.height
          -viewOrigin.y-size.height/2,size.width,size.height);
      // System.out.println("To view : "+toView);
      _scroller.getViewport().scrollRectToVisible(toView);
      // Rectangle after=_scroller.getViewport().getViewRect();
      // System.out.println("Viewport AFTER : "+after);
    }
    else if (event.getKeyChar()=='-')
    {
      float factor=_transformer.getXFactor()/2;
      if (factor<0.25) return;
      // System.out.println("************* Dezoom");
      Point viewOrigin=_scroller.getViewport().getViewPosition();
      // System.out.println("View origin : "+viewOrigin);
      Dimension size=_scroller.getViewport().getExtentSize();
      // System.out.println("View size : "+size);
      Dimension d=new Dimension(viewOrigin.x+size.width/2,viewOrigin.y
          +size.height/2);
      // System.out.println("View position : "+d);
      d=_transformer.inverseTransform(d);
      // System.out.println("Model position : "+d);
      changeZoom(factor);
      d=_transformer.transform(d);
      // System.out.println("View position (transformed) : "+d);
      _scroller.getViewport().setViewPosition(
          new Point(d.width-size.width/2,d.height-size.height/2));
      // Rectangle after=_scroller.getViewport().getViewRect();
      // System.out.println("Viewport AFTER : "+after);
    }
    else
    {
      System.out.println(event);
    }
  }

  /**
   * Part of <tt>KeyListener</tt>'s implementation. Not used.
   * @param e Source event.
   */
  public void keyPressed(KeyEvent e)
  {
    // Nothing to do here
  }

  /**
   * Part of <tt>KeyListener</tt>'s implementation. Not used.
   * @param e Source event.
   */
  public void keyReleased(KeyEvent e)
  {
    // Nothing to do here
  }
}
