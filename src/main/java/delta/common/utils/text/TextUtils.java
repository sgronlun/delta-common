package delta.common.utils.text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import delta.common.utils.files.TextFileReader;
import delta.common.utils.misc.MiscStringConstants;

/**
 * @author DAM
 */
public class TextUtils
{
  public static String loadTextFile(File path, String encoding)
  {
    String ret=null;
    List<String> lines=TextFileReader.readAsLines(path,encoding);
    if ((lines!=null) && (lines.size()>0))
    {
      StringBuilder sb=new StringBuilder();
      for(String line : lines)
      {
        sb.append(line);
        sb.append(MiscStringConstants.NATIVE_EOL);
      }
      ret=sb.toString();
    }
    return ret;
  }

  public static List<String> splitAsLines(File page)
  {
    List<String> ret=new ArrayList<String>();
    TextFileReader reader=new TextFileReader(page);
    if (reader.start())
    {
      String line;
      while(true)
      {
        line=reader.getNextLine();
        if (line==null) break;
        ret.add(line);
      }
      reader.terminate();
    }
    return ret;
  }
}
