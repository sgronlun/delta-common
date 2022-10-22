package delta.common.utils.text;

import java.text.Normalizer;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.BooleanTools;

/**
 * Filter that selects strings through equals/starts with/ends with/contains matches.
 * @author DAM
 */
public class StringFilter
{
  private static final Logger LOGGER=Logger.getLogger(StringFilter.class);

  private String _model;
  private MatchType _matchType;
  private boolean _ignoreCase;
  private boolean _normalise;

  /**
   * Constructor.
   * @param model Model string.
   * @param matchType Match type.
   * @param ignoreCase Indicates if case matters (<code>false</code>) or not (<code>true</code>).
   */
  public StringFilter(String model, MatchType matchType, boolean ignoreCase)
  {
      this(model, matchType, ignoreCase, false);
  }

    /**
     * Constructor.
     * 
     * @param model      Model string.
     * @param matchType  Match type.
     * @param ignoreCase Indicates if case matters (<code>false</code>) or not
     *                   (<code>true</code>).
     * @param normalise  Indicates if international characters (with accents,
     *                   etc.), matter (<code>false</code>) or not
     *                   (<code>true</code>).
     */
    public StringFilter(String model, MatchType matchType, boolean ignoreCase,
            boolean normalise) {
        _model = model;
        _matchType = matchType;
        _ignoreCase = ignoreCase;
        if (ignoreCase) {
            _model = model.toUpperCase();
        }
        this._normalise = normalise;
    }

  /**
   * Tests whether or not the specified string should be selected
   * by this filter.
   * @param stringToTest The string to be tested.
   * @return <code>true</code> if and only if <code>pathname</code> should be included.
   */
  public boolean accept(String stringToTest)
  {
    boolean ret=false;
    if (stringToTest!=null)
    {
      String toMatch=stringToTest;
      if (_ignoreCase)
      {
        toMatch=stringToTest.toUpperCase();
      }
      if (_normalise) {
          toMatch = normalise(toMatch);
      }
      if (_matchType==MatchType.EQUALS)
      {
        ret=toMatch.equals(_model);
      }
      else if (_matchType==MatchType.STARTS_WITH)
      {
        ret=toMatch.startsWith(_model);
      }
      else if (_matchType==MatchType.ENDS_WITH)
      {
        ret=toMatch.endsWith(_model);
      }
      else if (_matchType==MatchType.CONTAINS)
      {
        ret=toMatch.contains(_model);
      }
    }
    return ret;
  }

  /**
   * Standard toString method.
   * @return A stringified representation of this object.
   */
  @Override
  public String toString()
  {
    StringBuilder sb=new StringBuilder();
    sb.append("_matchType=[").append(_matchType).append("]");
    sb.append(", _ignoreCase=[").append(_ignoreCase).append("]");
    sb.append(", _model=[").append(_model).append("]");
    String ret=sb.toString();
    return ret;
  }

    /**
     * Remove accents and diacritics.
     * 
     * @param str the string to normalise.
     * @return the normalised string.
     */
    public static String normalise(String str) {
        String norm = Normalizer.normalize(str, Normalizer.Form.NFKD);
        return norm.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }

  /**
   * Build an instance of this class from a string definition.
   * @param filterDef String definition to use.
   * @return An instance of this class, or <code>null</code> if a problem occurred.
   */
  public static StringFilter buildFromString(String filterDef)
  {
    StringFilter ret=null;
    try
    {
      List<String> items=StringSplitter.splitAsList(filterDef,',');
      if ((items!=null) && (items.size()==3))
      {
        String matchTypeStr=items.get(0);
        MatchType matchType=MatchType.getByKey(matchTypeStr);
        String ignoreCaseStr=items.get(1);
        boolean ignoreCase=BooleanTools.parseBoolean(ignoreCaseStr,false);
        String model=items.get(2);
        ret=new StringFilter(model,matchType,ignoreCase);
      }
    }
    catch(Exception e)
    {
      LOGGER.error("Bad string filter definition",e);
    }
    return ret;
  }
}
