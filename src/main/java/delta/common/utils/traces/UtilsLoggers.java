package delta.common.utils.traces;

import org.apache.log4j.Logger;

/**
 * Management class for all utils loggers.
 * @author DAM
 */
public abstract class UtilsLoggers
{
  /**
   * Name of the "UTILS" logger.
   */
  public static final String UTILS="COMMON"+LoggingConstants.SEPARATOR+"UTILS";
  /**
   * Name of the configuration logger.
   */
  public static final String UTILS_CFG=UTILS+LoggingConstants.SEPARATOR+"CFG";
  /**
   * Name of the i18n logger.
   */
  public static final String UTILS_I18N=UTILS+LoggingConstants.SEPARATOR+"I18N";
  /**
   * Name of the I/O logger.
   */
  public static final String UTILS_IO=UTILS+LoggingConstants.SEPARATOR+"IO";
  /**
   * Name of the services logger.
   */
  public static final String UTILS_SERVICES=UTILS+LoggingConstants.SEPARATOR+"SERVICES";
  /**
   * Name of the CORBA logger.
   */
  public static final String UTILS_CORBA=UTILS+LoggingConstants.SEPARATOR+"CORBA";

  private static final Logger _utilsLogger=LoggersRegistry.getLogger(UTILS);
  private static final Logger _utilsCfgLogger=LoggersRegistry.getLogger(UTILS_CFG);
  private static final Logger _utilsI18nLogger=LoggersRegistry.getLogger(UTILS_I18N);
  private static final Logger _utilsIOLogger=LoggersRegistry.getLogger(UTILS_IO);
  private static final Logger _utilsServicesLogger=LoggersRegistry.getLogger(UTILS_SERVICES);
  private static final Logger _utilsCorbaLogger=LoggersRegistry.getLogger(UTILS_CORBA);

  /**
   * Get the logger used for utils.
   * @return the logger used for utils.
   */
  public static Logger getUtilsLogger()
  {
    return _utilsLogger;
  }

  /**
   * Get the configuration logger.
   * @return the configuration logger.
   */
  public static Logger getCfgLogger()
  {
    return _utilsCfgLogger;
  }

  /**
   * Get the i18n logger.
   * @return the i18n logger.
   */
  public static Logger getI18NLogger()
  {
    return _utilsI18nLogger;
  }

  /**
   * Get the I/O logger.
   * @return the I/O logger.
   */
  public static Logger getIOLogger()
  {
    return _utilsIOLogger;
  }

  /**
   * Get the services logger.
   * @return the services logger.
   */
  public static Logger getServicesLogger()
  {
    return _utilsServicesLogger;
  }

  /**
   * Get the CORBA logger.
   * @return the CORBA logger.
   */
  public static Logger getCorbaLogger()
  {
    return _utilsCorbaLogger;
  }
}
