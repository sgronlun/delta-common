package delta.common.utils.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Provides light-weight instances for string match types.
 * @author DAM
 */
public class MatchType
{
  private static final HashMap<String,MatchType> _instancesMap=new HashMap<String,MatchType>();
  private static final List<MatchType> ALL=new ArrayList<MatchType>();
  /**
   * A list that contains all match types.
   */
  public static final List<MatchType> ALL_TYPES=Collections.unmodifiableList(ALL);

  /**
   * "equals" match type.
   */
  public static final MatchType EQUALS=new MatchType("EQUALS");
  /**
   * "starts with" match type.
   */
  public static final MatchType STARTS_WITH=new MatchType("STARTS_WITH");
  /**
   * "ends with" match type.
   */
  public static final MatchType ENDS_WITH=new MatchType("ENDS_WITH");
  /**
   * "contains" match type.
   */
  public static final MatchType CONTAINS=new MatchType("CONTAINS");

  private String _key;

  private MatchType(String key)
  {
    _key=key;
    _instancesMap.put(key,this);
    ALL.add(this);
  }

  /**
   * Get the identifiying key for this match type.
   * @return the identifiying key for this match type.
   */
  public String getKey()
  {
    return _key;
  }

  /**
   * Get a stringified representation of this object.
   * @return a stringified representation of this object.
   */
  @Override
  public String toString()
  {
    return _key;
  }

  /**
   * Get a match type by it's key.
   * @param key key of match type to find.
   * @return found match type.
   */
  public static MatchType getByKey(String key)
  {
    MatchType ret=_instancesMap.get(key);
    return ret;
  }
}
