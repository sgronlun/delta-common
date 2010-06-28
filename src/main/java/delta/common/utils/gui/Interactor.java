package delta.common.utils.gui;

import java.awt.event.MouseEvent;

/**
 * @author DAM
 */
public interface Interactor
{
  public boolean handleMousePressed(MouseEvent event);
  public boolean handleMouseDragged(MouseEvent event);
  public boolean handleMouseReleased(MouseEvent event);
}
