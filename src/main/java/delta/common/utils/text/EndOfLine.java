package delta.common.utils.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Represents an end-of-line.
 * @author DAM
 */
public final class EndOfLine
{
  private static final List<EndOfLine> ALL=new ArrayList<EndOfLine>();
  private static final HashMap<String,EndOfLine> _instancesMap=new HashMap<String,EndOfLine>();

  /**
   * Native end-of-line string.
   */
  public static final String NATIVE_EOL=System.getProperty("line.separator");

  /**
   * Native end-of-line.
   */
  public static final EndOfLine NATIVE=new EndOfLine("NATIVE",NATIVE_EOL);

  /**
   * Un*x end-of-line.
   */
  public static final EndOfLine UNIX=new EndOfLine("UNIX","\n");

  /**
   * Windows end-of-line.
   */
  public static final EndOfLine WINDOWS=new EndOfLine("WINDOWS","\r\n");

  /**
   * MacOS end-of-line.
   */
  public static final EndOfLine MACOS=new EndOfLine("MACOS","\r");

  /**
   * A list that contains all end of lines.
   */
  public static final List<EndOfLine> ALL_EOL=Collections.unmodifiableList(ALL);

  /**
   * ID.
   */
  private String _id;

  /**
   * Value of this end of line.
   */
  private String _value;

  /**
   * Full constructor.
   * @param id ID of this end of line.
   * @param value Value of this end of line.
   */
  private EndOfLine(String id, String value)
  {
    _id=id;
    _value=value;
    ALL.add(this);
    _instancesMap.put(_id,this);
  }

  /**
   * Get the ID of this end of line.
   * @return the ID of this end of line.
   */
  public String getID()
  {
    return _id;
  }

  /**
   * Get the value of this end of line.
   * @return the value of this end of line.
   */
  public String getValue()
  {
    return _value;
  }

  @Override
  public String toString()
  {
    return _value;
  }

  /**
   * Get an end of line type by it's ID.
   * @param id ID of the end of line.
   * @return found end of line.
   */
  public static EndOfLine getByName(String id)
  {
    EndOfLine ret=_instancesMap.get(id);
    return ret;
  }

  /**
   * Get a list of all EndOfLine IDs.
   * @return a list of all EndOfLine IDs.
   */
  public static List<String> getAllEOLIDs()
  {
    List<String> ret=new ArrayList<String>();
    EndOfLine eol;
    for(Iterator<EndOfLine> it=ALL_EOL.iterator();it.hasNext();)
    {
      eol=it.next();
      ret.add(eol.getID());
    }
    return ret;
  }
}
