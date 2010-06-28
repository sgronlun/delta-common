package delta.common.utils.services;

import delta.common.utils.i18n.Translator;
import delta.common.utils.i18n.TranslatorsManager;

/**
 * Provides light-weight objects for the state of a service.
 * @author DAM
 */
public final class ServiceState
{
  private static Translator _babel;

  private String _state;

  /**
   * 'Not running' state.
   */
  public static final ServiceState NOT_RUNNING=new ServiceState("NOT_RUNNING");

  /**
   * 'Running' state.
   */
  public static final ServiceState RUNNING=new ServiceState("RUNNING");

  /**
   * 'Running' state.
   */
  public static final ServiceState OUT_OF_ORDER=new ServiceState("OUT_OF_ORDER");

  /**
   * Private constructor.
   * @param state State key.
   */
  private ServiceState(String state)
  {
    _state=state;
  }

  /**
   * Get state key.
   * @return State key.
   */
  public String getState()
  {
    return _state;
  }

  /**
   * Standard toString() method.
   * @return Stringified representation of this object.
   */
  @Override
  public String toString()
  {
    return getBabel().translate(_state);
  }

  /**
   * Get the <tt>Translator</tt> used for process state translations.
   * @return A translator.
   */
  public static Translator getBabel()
  {
    if (_babel==null)
    {
      _babel=TranslatorsManager.getInstance().createTranslator(ServiceState.class.getName());
    }
    return _babel;
  }
}
