package delta.common.utils.values;

import delta.common.utils.modelview.Observable;
import delta.common.utils.modelview.Observer;

/**
 * Provides the result of a simple integer expression as an observable. 
 * @author DAM
 */
public class ObservableSimpleIntegerExpression implements Observer<Integer>
{
  private Observable<Boolean> _observable;
  private SimpleIntegerExpression _expression;

  /**
   * Constructor.
   * @param expression Expression to use.
   */
  public ObservableSimpleIntegerExpression(SimpleIntegerExpression expression)
  {
    _observable=new Observable<Boolean>();
    _expression=expression;
  }

  /**
   * Get the managed observable.
   * @return the managed observable.
   */
  public Observable<Boolean> getObservable()
  {
    return _observable;
  }

  @Override
  public void valueChanged(Observable<Integer> source, Integer oldValue, Integer newValue)
  {
    boolean newExpressionValue=_expression.compute();
    _observable.setValue(Boolean.valueOf(newExpressionValue));
  }
}
