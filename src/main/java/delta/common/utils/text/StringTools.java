package delta.common.utils.text;

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
