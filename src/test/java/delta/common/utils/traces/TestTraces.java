package delta.common.utils.traces;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

/**
 * Traces test.
 * @author DAM
 */
public class TestTraces extends TestCase
{
  private static final Logger _logger=LoggersRegistry.getInstance().getLoggerByName("TEST",true);

  /**
   * Constructor.
   */
  public TestTraces()
  {
    super("Traces test");
  }

  /**
   * Performance test.
   */
  public void testLoggersPerformance()
  {
    final int NB=100000;
    final int GROUP=1000;
    long now1=System.currentTimeMillis();
    for(int i=0;i<NB;i++)
    {
      _logger.info("titi");
    }
    long now2=System.currentTimeMillis();
    long delta=now2-now1;
    System.out.println("Took "+delta+"ms ("+((GROUP*delta)/NB)+"ms/("+GROUP+" traces)");
  }
}
