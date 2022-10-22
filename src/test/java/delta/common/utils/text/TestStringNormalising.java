package delta.common.utils.text;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test for the normalising strings.
 * 
 * @author sgronlun
 */
public class TestStringNormalising extends TestCase {
	/**
	 * Constructor.
	 */
	public TestStringNormalising() {
		super("String normalizing test");
	}

	/**
	 * Test string normalising
	 */
	public void testStringNormalizing() {
		// Chars obtained from quests.xml plus a few more
		String utf8string = 
				  "!\"#$%&'()*+,-./0123456789:;<=>?"
				+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]_"
				+ "abcdefghijklmnopqrstuvwxyz{|}~" 
				+ "ÁÂÉÊËÍÎÓÔÖÚÛÅÄÖàáâäéêëíîòóôõöùúû" 
				+ "–—‘’“”•…‰";
		String expected = 
				  "!\"#$%&'()*+,-./0123456789:;<=>?" 
				+ "ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]_"
				+ "abcdefghijklmnopqrstuvwxyz{|}~" 
				+ "AAEEEIIOOOUUAAOaaaaeeeiiooooouuu" 
				+ "–—‘’“”•...‰";

		String norm = Normalizer.normalize(utf8string, Normalizer.Form.NFKD);
		norm = norm.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		//System.out.println(expected);
		//System.out.println(norm);
		Assert.assertEquals(norm, expected);	
	}
}
