package delta.common.utils.modelview;

/**
 * Observer.
 * @param <V> Tyoe of managed values.
 * @author DAM
 */
public interface Observer<V>
{
  /**
   * Called when a value changed on the given observable.
   * @param source Source observable.
   * @param oldValue Old value.
   * @param newValue New value.
   */
  public void valueChanged(Observable<V> source, V oldValue, V newValue);
}
