package delta.common.utils.network;

import junit.framework.TestCase;
import delta.common.utils.network.services.NetworkServices;
import delta.common.utils.network.services.ServiceInfo;

/**
 * Services test.
 * @author DAM
 */
public class TestServices extends TestCase
{
  /**
   * Constructor.
   */
  public TestServices()
  {
    super("Services test");
  }

  /**
   * Test services parsing.
   */
  public void testServicesParsing()
  {
    ServiceInfo sl=NetworkServices.getInstance().getServiceByName("testService");
    System.out.println("Service : "+sl);
  }
}
