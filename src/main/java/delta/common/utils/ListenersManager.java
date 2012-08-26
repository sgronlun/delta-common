package delta.common.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manages a list of listener instances.
 * @author DAM
 * @param <L> Type of listeners to manage.
 */
public class ListenersManager<L> implements Iterable<L>
{
  /**
   * Get an iterator over the managed listeners.
   * @return a new iterator.
   */
  public Iterator<L> iterator()
  {
    return _listeners.iterator();
  }

  private List<L> _listeners;
  
  /**
   * Constructor.
   */
  public ListenersManager()
  {
    _listeners=new ArrayList<L>();
  }

  /**
   * Register a new listener.
   * @param listener Listener to register.
   */
  public void addListener(L listener)
  {
    _listeners.add(listener);
  }
  
  /**
   * Remove a listener.
   * @param listener Listener to remove.
   */
  public void removeListener(L listener)
  {
    _listeners.remove(listener);
  }
  
  /**
   * Remove all listeners.
   */
  public void removeAllListeners()
  {
    _listeners.clear();
  }
}
