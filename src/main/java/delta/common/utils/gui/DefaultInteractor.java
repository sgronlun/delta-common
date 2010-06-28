package delta.common.utils.gui;

import java.awt.event.MouseEvent;

/**
 * @author DAM
 */
public class DefaultInteractor implements Interactor
{
  /**
   *
   * @see delta.common.utils.gui.Interactor#handleMousePressed(java.awt.event.MouseEvent)
   */
  public boolean handleMousePressed(MouseEvent event)
  {
    return false;
  }

  /**
   *
   * @see delta.common.utils.gui.Interactor#handleMouseDragged(java.awt.event.MouseEvent)
   */
  public boolean handleMouseDragged(MouseEvent event)
  {
    return false;
  }

  /**
   * @see delta.common.utils.gui.Interactor#handleMouseReleased(java.awt.event.MouseEvent)
   */
  public boolean handleMouseReleased(MouseEvent event)
  {
    return false;
  }
}
