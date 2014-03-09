package delta.common.utils.html;

/**
 * HTML conversion facilities.
 * @author DAM
 */
public class HtmlConversions
{
  /**
   * Espace a string for inclusion in an HTML document.
   * @param s String to escape.
   * @param escapeNewLines <code>true</code> to escape new lines.
   * @return the escaped string.
   */
  public static String stringToHtml(String s, boolean escapeNewLines)
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
      // ignore '\r'
      else if (c=='\r') sb.append("");
      else if (c=='\n')
      {
        if (escapeNewLines) sb.append("<br>"); else sb.append(' ');
      }
      else if (c=='>') sb.append("&gt;");
      else if (c=='<') sb.append("&lt;");
      else if (c=='"') sb.append("&quot;");
      else sb.append(c);
    }
    return sb.toString();
  }
}