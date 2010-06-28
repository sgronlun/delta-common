package delta.common.utils.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * @author dm
 */
public class GraphicalRectangle extends GraphicalObject
{
  private Rectangle _rectangle;

  public GraphicalRectangle(Rectangle r)
  {
    _rectangle=new Rectangle(r);
  }

  @Override
  public void moveTo(int x, int y)
  {
    _rectangle.setLocation(x,y);
  }

  @Override
  public void draw(Graphics g)
  {
    g.drawRect(_rectangle.x,_rectangle.y,_rectangle.width,_rectangle.height);
  }

  @Override
  public Point getOrigin()
  {
    return new Point(_rectangle.x,_rectangle.y);
  }

  @Override
  public Rectangle getBoundingBox()
  {
    return new Rectangle(_rectangle);
  }
}
