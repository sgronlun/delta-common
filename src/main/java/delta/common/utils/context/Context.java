package delta.common.utils.context;

import java.util.Set;

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
   * Indicates if this context has a value for the given key.
   * <p>Note: this will not use the parent context if any.
   * @param key Key to use.
   * @return <code>true</code> if so, <code>false</code> otherwise.
   */
  boolean hasValue(String key);

  /**
   * Set a context value.
   * @param key Key to use.
   * @param value Value to set.
   */
  void setValue(String key, Object value);

  /**
   * Remove a value in this context.
   * @param key Key to remove.
   */
  void removeValue(String key);

  /**
   * Get the managed keys.
   * <p>Note: this will not use the parent context if any.
   * @return A set of keys.
   */
  Set<String> getKeys();

  /**
   * Clear all data in this context.
   */
  void clear();
}
