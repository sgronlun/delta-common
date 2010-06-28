package delta.common.utils.text;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;

/**
 * Constants for the name of well-known encodings.
 * @author DAM
 */
public class EncodingNames
{
  private static List<String> FREQUENT_ENCODINGS=null;
  private static List<String> ALL_ENCODINGS=null;

  /**
   * UTF-8 encoding.
   */
  public static final String UTF_8="UTF8";

  /**
   * Latin-1 encoding.
   */
  public static final String LATIN_1="ISO8859_1";

  /**
   * ISO8859-1 encoding.
   */
  public static final String ISO8859_1=LATIN_1;

  /**
   * Windows encoding.
   */
  public static final String WINDOWS="Cp1252";

  /**
   * DOS/West Europe encoding.
   */
  public static final String DOS="IBM850";

  /**
   * Default platform encoding.
   */
  public static final String DEFAULT_ENCODING=Charset.defaultCharset().name();

  /**
   * Get a list of encodings considered as frequent encodings.
   * @return a list of encoding names.
   */
  public static List<String>  getFrequentEncodings()
  {
    if (FREQUENT_ENCODINGS==null)
    {
      List<String> tmp=new ArrayList<String>();
      tmp.add(ISO8859_1);
      tmp.add(UTF_8);
      tmp.add(WINDOWS);
      tmp.add(DOS);
      FREQUENT_ENCODINGS=Collections.unmodifiableList(tmp);
    }
    return FREQUENT_ENCODINGS;
  }

  /**
   * Get the list of all encodings supported by this JVM.
   * @return the list of all encodings supported by this JVM.
   */
  public static List<String> getAllEncodings()
  {
    if (ALL_ENCODINGS==null)
    {
      SortedMap<String,Charset> charsets=Charset.availableCharsets();
      Collection<Charset> charsetsColl=charsets.values();
      List<String> tmp=new ArrayList<String>(charsetsColl.size());
      Charset charset;
      String name;
      for(Iterator<Charset> it=charsetsColl.iterator();it.hasNext();)
      {
        charset=it.next();
        name=charset.name();
        tmp.add(name);
      }
      ALL_ENCODINGS=Collections.unmodifiableList(tmp);
    }
    return ALL_ENCODINGS;
  }
}
