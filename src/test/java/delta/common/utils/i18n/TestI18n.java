package delta.common.utils.i18n;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * Internationalization test.
 * @author DAM
 */
public class TestI18n extends TestCase
{
  private static final Logger _logger=UtilsLoggers.getI18NLogger();
  private static final Translator _translator=TranslatorsManager.getInstance().createTranslator(TestI18n.class);

  /**
   * Constructor.
   */
  public TestI18n()
  {
    super("I18N test");
  }

  /**
   * Test basic i18n usage.
   */
  public void testTranslation()
  {
    String simpleMsg=_translator.translate("test");
    _logger.info("Translation for 'test' : '"+simpleMsg+"'");
    Object[] params=new String[] {"1", "2", "3"};
    String complexMsg=_translator.translate("test_args",params);
    _logger.info("Translation for 'test_args' : '"+complexMsg+"'");
  }
}
