package delta.common.utils.context;

/**
 * Context.
 * @author DAM
 */
public interface Context
{
  /**
   * Get the parent context.
   * @return A context or <code>null</code> if no parent.
   */
  Context getParentContext();

  /**
   * Get the value of a given key.
   * <p>Note: this will not use the parent context if any.
   * @param <T> Type of expected value.
   * @param key Key to use.
   * @param clazz Typed of data to get.
   * @return A value or <code>null</code> if not found.
   */
  <T> T getValue(String key, Class<T> clazz);

  /**
   * Remove a value in this context.
   * @param key Key to remove.
   */
  void removeValue(String key);

  /**
   * Clear all data in this context.
   */
  void clear();
}
