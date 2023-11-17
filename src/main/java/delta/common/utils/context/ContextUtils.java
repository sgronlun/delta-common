package delta.common.utils.context;

/**
 * Utility methods related to contexts.
 * @author DAM
 */
public class ContextUtils
{
  /**
   * Get the value of a given key.
   * <p>Note: this will not use the parent context if any.
   * @param <T> Type of expected value.
   * @param context Context to use.
   * @param key Key to use.
   * @param clazz Typed of data to get.
   * @return A value or <code>null</code> if not found.
   */
  public static <T> T getValue(Context context, String key, Class<T> clazz)
  {
    Context current=context;
    while (current!=null)
    {
      if (current.hasValue(key))
      {
        return current.getValue(key,clazz);
      }
      current=current.getParentContext();
    }
    return null;
  }
}
