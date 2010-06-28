package delta.common.utils.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A set of tools related to collections handling.
 * @author DAM
 */
public class CollectionTools
{
  /**
   * Build a <tt>Set</tt> of <tt>String</tt> from a <tt>String</tt> array.
   * @param data String array to use.
   * @return A newly built <tt>Set</tt>.
   */
  public static Set<String> stringSetFromArray(String[] data)
  {
    HashSet<String> ret=new HashSet<String>();
    if ((data==null) || (data.length==0)) return ret;
    for(int i=0;i<data.length;i++)
    {
      ret.add(data[i]);
    }
    return ret;
  }

  /**
   * Build a <tt>List</tt> of <tt>String</tt> from a <tt>String</tt> array.
   * @param data String array to use.
   * @return A newly built <tt>List</tt>.
   */
  public static List<String> stringListFromArray(String[] data)
  {
    List<String> ret=new ArrayList<String>();
    if ((data==null) || (data.length==0)) return ret;
    for(int i=0;i<data.length;i++)
    {
      ret.add(data[i]);
    }
    return ret;
  }

  /**
   * Build an array of <tt>String</tt> from a <tt>Set</tt> of <tt>String</tt>s.
   * @param dataSet String collection to use.
   * @return A newly built array.
   */
  public static String[] stringArrayFromCollection(Collection<String> dataSet)
  {
    String[] ret;
    if (dataSet!=null)
    {
      ret=dataSet.toArray(new String[dataSet.size()]);
    }
    else
    {
      ret=new String[0];
    }
    return ret;
  }

  /**
   * Get the contents of a list as a string.
   * <code>null</code> or empty list gives "",
   * Each item is separated from the others by a <code>separator</code>.
   * Each <code>null</code> of empty item is ignored.
   * @param list List to use.
   * @param separator Separator to use.
   * @param before String to add before each item (<code>null</code> if none).
   * @param after String to add after each item (<code>null</code> if none).
   * @return A nicely formatted string.
   */
  public static String stringListAsString(List<String> list, String separator, String before, String after)
  {
    String ret="";
    StringBuilder sb=new StringBuilder();
    if ((list!=null) && (list.size()>0))
    {
      String listItem;
      for(Iterator<String> it=list.iterator();it.hasNext();)
      {
        listItem=it.next();
        if ((listItem!=null) && (listItem.length()>0))
        {
          if (sb.length()>0) sb.append(separator);
          if (before!=null) sb.append(before);
          sb.append(listItem);
          if (after!=null) sb.append(after);
        }
      }
    }
    ret=sb.toString();
    return ret;
  }

  /**
   * Get the contents of a list as a string.
   * <code>null</code> or empty list gives "",
   * Each item is separated from the others by a space.
   * Each <code>null</code> of empty item is ignored.
   * @param list List to use.
   * @param before String to add before each item (<code>null</code> if none).
   * @param after String to add after each item (<code>null</code> if none).
   * @return A nicely formatted string.
   */
  public static String stringListAsString(List<String> list, String before, String after)
  {
    String ret=stringListAsString(list," ",before,after);
    return ret;
  }

  /**
   * Get the contents of a list as a string.
   * <code>null</code> or empty list gives "",
   * Each item is separated from the others by a space.
   * Each <code>null</code> of empty item is ignored.
   * @param list List to use.
   * @return A nicely formatted string.
   */
  public static String stringListAsString(List<String> list)
  {
    String ret=stringListAsString(list,null,null);
    return ret;
  }
}
