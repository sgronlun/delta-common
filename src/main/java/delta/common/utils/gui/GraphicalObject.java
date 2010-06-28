package delta.common.utils.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * @author DAM
 */
public abstract class GraphicalObject
{
  public abstract void draw(Graphics g);

  public abstract void moveTo(int x, int y);

  public Point getOrigin()
  {
    Rectangle r=getBoundingBox();
    return new Point(r.x,r.y);
  }

  public abstract Rectangle getBoundingBox();
}
