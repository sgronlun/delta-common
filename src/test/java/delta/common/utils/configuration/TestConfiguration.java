package delta.common.utils.configuration;

import java.io.File;

import junit.framework.Assert;
import junit.framework.TestCase;
import delta.common.utils.environment.FileSystem;
import delta.common.utils.files.TextFileWriter;

/**
 * Tests for the configuration service.
 * @author DAM
 */
public class TestConfiguration extends TestCase
{
  private static final String IF_EXISTS_SECTION="TEST_IF_EXISTS";
  private static final String FLAG_VAR="FLAG";
  private static final String OTHER_FLAG_VAR="OTHER_FLAG";
  private static final String VALUE1_VAR="VALUE1";
  private static final String VALUE1="v1";
  private static final String VALUE2_VAR="VALUE2";
  private static final String VALUE2="vé";

  /**
   * Constructor.
   */
  public TestConfiguration()
	{
		super("Configuration test");
	}

  /**
   * Test the "get user configuration" feature.
   */
  public void testGetUserConfiguration()
	{
		Configuration c=Configurations.getUserConfiguration();
		Assert.assertNotNull(c);
	}

	/**
   * Tests the "if exists" feature.
	 */
  public void testIfExists()
  {
    File cfgFile=buildIfExistsTestFile();
    Configuration cfg=new Configuration();
    ConfigurationFileIO.loadFile(cfgFile,cfg);
    boolean resolution=cfg.resolveValues();
    Assert.assertEquals(true,resolution);
    String v1=cfg.getStringValue(IF_EXISTS_SECTION, VALUE1_VAR, null);
    Assert.assertNotNull(v1);
    Assert.assertEquals(VALUE1,v1);
    String v2=cfg.getStringValue(IF_EXISTS_SECTION, VALUE2_VAR, null);
    Assert.assertNotNull(v2);
    Assert.assertEquals(VALUE2,v2);
    cfgFile.delete();
  }

  /**
   * Build a test configuration file for the "if exists" test.
   * @return The path of the newly built file.
   */
  private File buildIfExistsTestFile()
  {
    File tmp=FileSystem.getTmpDir();
    File cfgFile=new File(tmp,"ifExists.cfg");
    TextFileWriter writer=new TextFileWriter(cfgFile);
    if (writer.start())
    {
      writer.writeNextLine("["+IF_EXISTS_SECTION+"]");
      writer.writeNextLine(FLAG_VAR+"=true");
      writer.writeNextLine(VALUE1_VAR+"=${"+Configuration.IF_EXISTS+"|"+IF_EXISTS_SECTION+","+FLAG_VAR+","+VALUE1+","+VALUE2+"}");
      writer.writeNextLine(VALUE2_VAR+"=${"+Configuration.IF_EXISTS+"|"+IF_EXISTS_SECTION+","+OTHER_FLAG_VAR+","+VALUE1+","+VALUE2+"}");
      writer.terminate();
    }
    return cfgFile;
  }

  /**
   * Test the "dump configuration" feature.
   */
  public void testDump()
	{
		Configurations.getUserConfiguration().dump(System.out);
	}
}
