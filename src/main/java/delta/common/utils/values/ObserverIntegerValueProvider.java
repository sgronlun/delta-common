package delta.common.utils.values;

import delta.common.utils.modelview.Observable;

/**
 * Integer value provider that uses an observable.
 * @author DAM
 */
public class ObserverIntegerValueProvider implements IntegerValueProvider
{
  private Observable<Integer> _observable;

  /**
   * Constructor.
   * @param observable Observable to wrap.
   */
  public ObserverIntegerValueProvider(Observable<Integer> observable)
  {
    _observable=observable;
  }

  @Override
  public int get()
  {
    return _observable.getValue().intValue();
  }
}
