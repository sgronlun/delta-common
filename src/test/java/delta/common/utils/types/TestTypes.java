package delta.common.utils.types;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Unit test class for the types system.
 * @author DAM
 */
public class TestTypes extends TestCase
{
  /**
   * Constructor.
   */
  public TestTypes()
  {
    super("Types test");
  }

  /**
   * Test the types classes registry.
   */
  public void testTypeClassesRegistry()
  {
    TypeClassesRegistry registry=TypeClassesRegistry.getInstance();
    Assert.assertNotNull(registry);
  }

  /**
   * Test the generic type class retrieval.
   */
  public void testGetTypeClassByName()
  {
    TypeClassesRegistry registry=TypeClassesRegistry.getInstance();
    TypeClass intType=registry.getTypeClassByName(IntegerType.TYPE_NAME);
    Assert.assertNotNull(intType);
  }

  /**
   * Test the generic type retrieval.
   */
  public void testGetTypeByName()
  {
    TypesRegistry registry=TypesRegistry.getInstance();
    Type positiveIntType=registry.getType(BuiltInTypes.POSITIVE_INTEGER);
    Assert.assertNotNull(positiveIntType);
    Type intType=registry.getType(BuiltInTypes.INTEGER);
    Assert.assertNotNull(intType);
  }

  /**
   * Test the integer type.
   */
  public void testIntegerType()
  {
    IntegerType intType=new IntegerType(-4,100);
    Object cinquante=intType.parseFromString("50");
    Assert.assertNotNull(cinquante);
    Assert.assertTrue(cinquante instanceof Integer);
    Integer integer=(Integer)cinquante;
    Assert.assertEquals(50,integer.intValue());

    Object notAnInt=intType.parseFromString("50a");
    Assert.assertNull(notAnInt);
  }
}
