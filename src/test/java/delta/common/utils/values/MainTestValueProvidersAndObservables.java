package delta.common.utils.values;

import javax.swing.JButton;

import delta.common.utils.modelview.Observable;
import delta.common.utils.modelview.Observer;

/**
 * Test class to play with value providers and observables.
 * @author DAM
 */
public class MainTestValueProvidersAndObservables
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    // TODO Get from table's selection manager
    Observable<Integer> selectedCountObservable=new Observable<Integer>();

    IntegerValueProvider sourceValueProvider=new ObserverIntegerValueProvider(selectedCountObservable);
    ConstantIntegerProvider one=new ConstantIntegerProvider(1);
    SimpleIntegerExpression expr=new SimpleIntegerExpression(sourceValueProvider,ComparisonOperator.EQUAL,one);
    ObservableSimpleIntegerExpression obs=new ObservableSimpleIntegerExpression(expr);
    selectedCountObservable.addObserver(obs);
    Observable<Boolean> buttonStateObservable=obs.getObservable();
    final JButton button=new JButton();
    Observer<Boolean> buttonStateUpdater=new Observer<Boolean>()
    {
      @Override
      public void valueChanged(Observable<Boolean> source, Boolean oldValue, Boolean newValue)
      {
        button.setEnabled(newValue.booleanValue());
        System.out.println("Button enabled: "+newValue);
      }
    };
    buttonStateObservable.addObserver(buttonStateUpdater);
    selectedCountObservable.setValue(Integer.valueOf(1));
    selectedCountObservable.setValue(Integer.valueOf(0));
    selectedCountObservable.setValue(Integer.valueOf(1));
    selectedCountObservable.setValue(Integer.valueOf(2));
  }

}
