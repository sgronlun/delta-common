package delta.common.utils.html;

/**
 * @author DAM
 */
public class HtmlConversions
{
  public static String stringToHtml(String s)
  {
    StringBuilder sb=new StringBuilder();
    int l=s.length();
    for(int i=0;i<l;i++)
    {
      char c=s.charAt(i);
      if (c=='é') sb.append("&eacute;");
      else if (c=='è') sb.append("&egrave;");
      else if (c=='ê') sb.append("&ecirc;");
      else if (c=='à') sb.append("&agrave;");
      else if (c=='û') sb.append("&ucirc;");
      else if (c=='\n') sb.append("<br>");
      else if (c=='>') sb.append("&gt;");
      else if (c=='<') sb.append("&lt;");
      else if (c=='"') sb.append("&quot;");
      else sb.append(c);
    }
    return sb.toString();
  }
}