package delta.common.utils.graph.gui.swing;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import delta.common.utils.graph.Graph;
import delta.common.utils.graph.GraphConstants;
import delta.common.utils.graph.GraphLink;
import delta.common.utils.graph.GraphNode;

/**
 * Test class for graph-related functions.
 * @author DAM
 */
public class MainTestGraph
{
  /**
   * Constructor.
   */
  public MainTestGraph()
  {
    displayGraph();
  }

  private static void displayGraph()
  {
    JFrame f=new JFrame("test");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Graph<Object,Object> graph=new Graph<Object,Object>();
    GraphNode<Object> n1=new GraphNode<Object>("n1","AAA","BBB",null);
    graph.addNode(n1,new Dimension(0,0),GraphConstants.STD_DIMENSION);
    GraphNode<Object> n2=new GraphNode<Object>("n2","CCC","DDD",null);
    graph.addNode(n2,new Dimension(200,100),GraphConstants.STD_DIMENSION);
    GraphNode<Object> n3=new GraphNode<Object>("n3","EEE","FFF",null);
    graph.addNode(n3,new Dimension(100,200),GraphConstants.STD_DIMENSION);
    GraphNode<Object> n4=new GraphNode<Object>("n4","GGG","HHH",null);
    graph.addNode(n4,new Dimension(300,150),GraphConstants.STD_DIMENSION);
    ArrayList<String> l1text=new ArrayList<String>();
    l1text.add("001");
    GraphLink<Object> l1=new GraphLink<Object>("n1","n2",l1text);
    graph.addLink(l1);
    ArrayList<String> l2text=new ArrayList<String>();
    l2text.add("002");
    l2text.add("002A");
    GraphLink<Object> l2=new GraphLink<Object>("n3","n4",l2text);
    graph.addLink(l2);
    ArrayList<String> l3text=new ArrayList<String>();
    l3text.add("003");
    GraphLink<Object> l3=new GraphLink<Object>("n1","n3",l3text);
    graph.addLink(l3);
    JScrollPane scroller=new JScrollPane();
    GraphCanvas<Object,Object> canvas=new GraphCanvas<Object,Object>(graph,scroller);
    scroller.setViewportView(canvas);
    scroller.setPreferredSize(new Dimension(800,500));
    canvas.changeZoom(1f);
    f.getContentPane().add(scroller);
    f.pack();
    f.setVisible(true);
  }

  /**
   * Main method for this test.
   * @param args Useless.
   */
  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(new Runnable()
    {
      public void run()
      {
        new MainTestGraph();
      }
    });
  }
}
