package delta.common.utils.types.utils;

import org.apache.log4j.Logger;

import delta.common.utils.traces.LoggersRegistry;
import delta.common.utils.traces.LoggingConstants;

/**
 * Management class for all "types management" loggers.
 * @author DAM
 */
public abstract class TypesLoggers
{
  private static final String COMMON="COMMON";
  private static final String COMMON_TYPES=COMMON+LoggingConstants.SEPARATOR+"TYPES";

  private static final Logger _typesLogger=LoggersRegistry.getLogger(COMMON_TYPES);

  /**
   * Get the logger used for types.
   * @return the logger used for types.
   */
  public static Logger getTypesLogger()
  {
    return _typesLogger;
  }
}
