package delta.common.utils.text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * String-related tools.
 * @author DAM
 */
public class StringTools
{
  /**
   * Build a displayable string from a object array.
   * For instance {"DAM","TDE"} -> "[DAM,TDE]"
   * @param data object array
   * @return the computed <tt>String</tt>.
   */
  public static String getDisplayableArray(Object[] data)
  {
    if ((data==null) || (data.length==0)) return "[]";
    StringBuffer sb=new StringBuffer("[");
    for(int i=0;i<data.length;i++)
    {
      if (i>0) sb.append(", ");
      sb.append(data[i]);
    }
    sb.append(']');
    return sb.toString();
  }

  /**
   * Smart toString method, that copes gracefully with arrays.
   * @param value Value to show.
   * @return the result string.
   */
  public static String smartToString(Object value)
  {
    String valueStr=null;
    if (value!=null)
    {
      if (value.getClass().isArray())
      {
        valueStr=arrayToString(value);
      }
      else
      {
        valueStr=value.toString();
      }
    }
    return valueStr;
  }

  private static String arrayToString(Object array)
  {
    StringBuilder sb=new StringBuilder();
    sb.append('[');
    int length=Array.getLength(array);
    for(int i=0;i<length;i++)
    {
      if (i>0)
      {
        sb.append(',');
      }
      Object value=Array.get(array,i);
      String valueStr=smartToString(value);
      sb.append(valueStr);
    }
    sb.append(']');
    return sb.toString();
  }

  /**
   * Get the length of the common start string of specified
   * strings.
   * @param s1 a string.
   * @param s2 another string.
   * @return this length.
   */
  public static int getStartMatchLength(String s1, String s2)
  {
    if (s1==null) return 0;
    if (s2==null) return 0;
    int min=s1.length();
    if (min>s2.length()) min=s2.length();
    if (min==0) return 0;
    char[] tmp=new char[min];
    s1.getChars(0,min,tmp,0);
    char[] tmp2=new char[min];
    s2.getChars(0,min,tmp2,0);
    int ret=0;
    while ((ret<min) && (tmp[ret]==tmp2[ret]))
    {
      ret++;
    }
    return ret;
  }

  /**
   * Find all strings after string <code>before</code> and before string <code>after</code>
   * in a line of text. 
   * @param line Line to use.
   * @param before String to search.
   * @param after String to search after.
   * @return A list of found strings.
   */
  public static List<String> findAllBetween(String line, String before, String after)
  {
    List<String> ret=new ArrayList<String>();
    int baseIndex=0;
    while(true)
    {
      int index=line.indexOf(before,baseIndex);
      if (index!=-1)
      {
        int index2=line.indexOf(after,index+before.length());
        if (index2!=-1)
        {
          String item=line.substring(index+before.length(),index2);
          ret.add(item);
          baseIndex=index2+after.length();
        }
        else
        {
          break;
        }
      }
      else
      {
        break;
      }
    }
    return ret;
  }

  /**
   * Find the string after string <code>before</code> in a line of text. 
   * @param line Line to use.
   * @param before String to search.
   * @return A string or <code>null</code> if <code>before</code> was not found.
   */
  public static String findAfter(String line, String before)
  {
    String ret=null;
    int index=line.indexOf(before);
    if (index!=-1)
    {
      ret=line.substring(index+before.length());
    }
    return ret;
  }

  /**
   * Find the string between string <code>before</code> and string <code>after</code> in a line of text. 
   * @param line Line to use.
   * @param before String to search before.
   * @param after String to search after.
   * @return A string or <code>null</code> if <code>before</code> or <code>after</code> was not found.
   */
  public static String findBetween(String line, String before, String after)
  {
    String ret=null;
    int index=line.indexOf(before);
    if (index!=-1)
    {
      int index2=line.indexOf(after,index+before.length());
      if (index2!=-1)
      {
        ret=line.substring(index+before.length(),index2);
      }
    }
    return ret;
  }
}
