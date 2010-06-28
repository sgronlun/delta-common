package delta.common.utils.text;

import java.util.ArrayList;
import java.util.List;

/**
 * A tool that splits a <tt>String</tt> into a series of <tt>String</tt>s.
 * @author DAM
 */
public class StringSplitter
{
  /**
   * Separator used for string splitting, if no separator is specified.
   */
  public static final char DEFAULT_SEPARATOR=',';

  /**
   * Split a <tt>String</tt> into an array of <tt>String</tt>s, using the <code>DEFAULT_SEPARATOR</code>.
   * @param toSplit string to split
   * @return a array of split strings.
   */
  public static final String[] split(String toSplit)
  {
    return split(toSplit,DEFAULT_SEPARATOR);
  }

  /**
   * Split a <tt>String</tt> into an array of <tt>String</tt>s, using the specified <code>separator</code>.
   * @param toSplit string to split
   * @param separator separator used to split text
   * @return a array of split strings.
   */
  public static final String[] split(String toSplit, char separator)
  {
    if (toSplit==null) return null;
    int length=toSplit.length();
    if (length==0) return new String[0];

    char[] chars=toSplit.toCharArray();
    int nbPieces=1;
    for(int i=0;i<length;i++)
    {
      if (chars[i]==separator) nbPieces++;
    }
    String[] ret=new String[nbPieces];
    StringBuilder sb=new StringBuilder();
    int pieceIndex=0;
    for(int i=0;i<length;i++)
    {
      if (chars[i]==separator)
      {
        ret[pieceIndex]=sb.toString();
        sb.setLength(0);
        pieceIndex++;
      }
      else
      {
        sb.append(chars[i]);
      }
    }
    ret[pieceIndex]=sb.toString();
    return ret;
  }

  /**
   * Split a <tt>String</tt> into a <tt>List</tt> of <tt>String</tt>s, using the <code>DEFAULT_SEPARATOR</code>.
   * @param text string to split
   * @return list of split strings.
   */
  public static final List<String> splitAsList(String text)
  {
    return splitAsList(text,DEFAULT_SEPARATOR);
  }

  /**
   * Split a <tt>String</tt> into a <tt>List</tt> of <tt>String</tt>s, using the specified <code>separator</code>.
   * @param text string to split
   * @param separator separator used to split text
   * @return a list of split strings.
   */
  public static final List<String> splitAsList(String text, char separator)
  {
    if (text==null) throw new IllegalArgumentException("text==null");

    List<String> result=new ArrayList<String>();
    if (text.length()>0)
    {
      int nextIndex=0;
      int previousIndex=0;
      String toAdd;
      while (nextIndex!=-1)
      {
        nextIndex=text.indexOf(separator,previousIndex);
        if (nextIndex==-1)
        {
          toAdd=text.substring(previousIndex);
        }
        else
        {
          toAdd=text.substring(previousIndex,nextIndex);
        }
        result.add(toAdd);
        previousIndex=nextIndex+1;
      }
    }
    return result;
  }
}
