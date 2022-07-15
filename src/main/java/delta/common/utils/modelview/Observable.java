package delta.common.utils.modelview;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;

/**
 * Observable.
 * <p>
 * An observable stores a value and accepts observers.
 * It notifies observers when the managed value changes.
 * @param <V> Type of managed value.
 * @author DAM
 */
public class Observable<V>
{
  private static final Logger LOGGER=Logger.getLogger(Observable.class);

  private V _value;
  private List<Observer<V>> _observers;

  /**
   * Constructor.
   */
  public Observable()
  {
    _observers=new ArrayList<Observer<V>>();
  }

  /**
   * Get the current value.
   * @return the current value.
   */
  public V getValue()
  {
    return _value;
  }

  /**
   * Set a value.
   * @param value Value to set.
   */
  public void setValue(V value)
  {
    boolean same=Objects.equals(_value,value);
    if (!same)
    {
      V oldValue=_value;
      _value=value;
      if (!_observers.isEmpty())
      {
        callObservers(oldValue,value);
      }
    }
  }

  /**
   * Add an observer.
   * @param observer Observer to add.
   */
  public void addObserver(Observer<V> observer)
  {
    _observers.add(observer);
  }

  /**
   * Call observers.
   * @param oldValue Old value.
   * @param newValue New value.
   */
  private void callObservers(V oldValue, V newValue)
  {
    @SuppressWarnings("unchecked")
    Observer<V>[] observers=_observers.toArray(new Observer[_observers.size()]);
    for(Observer<V> observer : observers)
    {
      try
      {
        observer.valueChanged(this,oldValue,newValue);
      }
      catch(Exception e)
      {
        LOGGER.warn("Caught exception during an observer call!", e);
      }
    }
  }
}
