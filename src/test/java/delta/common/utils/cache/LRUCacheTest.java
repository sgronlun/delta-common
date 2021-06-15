package delta.common.utils.cache;

import junit.framework.TestCase;

/**
 * Test class for the LRU cache.
 * @author DAM
 */
public class LRUCacheTest extends TestCase
{
  /**
   * Simple usage test.
   */
  public void test()
  {
    LRUCache<Integer,String> cache=new LRUCache<Integer,String>(4);
    cache.registerObject(Integer.valueOf(1),"Value_1");
    cache.registerObject(Integer.valueOf(2),"Value_2");
    cache.registerObject(Integer.valueOf(3),"Value_3");
    cache.registerObject(Integer.valueOf(4),"Value_4");
    System.out.println(cache.getObject(Integer.valueOf(2)));
    System.out.println(cache.getKeys());
    System.out.println(cache.getObject(Integer.valueOf(5)));
    cache.registerObject(Integer.valueOf(5),"Value_5");
    System.out.println(cache.getKeys());
  }

}
