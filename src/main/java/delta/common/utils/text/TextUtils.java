package delta.common.utils.text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import delta.common.utils.files.TextFileReader;

/**
 * @author DAM
 */
public class TextUtils
{

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
