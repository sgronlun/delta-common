package delta.common.utils.text;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.files.TextFileReader;

/**
 * Tool to find encoding used by text files.
 * @author DAM
 */
public class TextEncodingFinder
{
  private static final Logger LOGGER=Logger.getLogger(TextEncodingFinder.class);

  private List<String> _encodings;

  private boolean _verbose;

  /**
   * Get a list of encoding considered as a reasonable default.
   * @return a list of encoding names.
   */
  public static List<String> getDefaultEncodings()
  {
    List<String> ret=EncodingNames.getFrequentEncodings();
    return ret;
  }

  /**
   * Constructor.
   * @param encodings Encodings to use.
   */
  public TextEncodingFinder(List<String> encodings)
  {
    List<String> tmp=encodings;
    if (tmp==null)
    {
      tmp=getDefaultEncodings();
    }
    _encodings=new ArrayList<String>(tmp);
    _verbose=LOGGER.isInfoEnabled();
  }

  /**
   * File the encoding of a text file.
   * @param f Text file to use.
   * @return An encoding statistics object or <code>null</code> if encoding cannot be found
   * (file is unreadable or has no specific character).
   */
  public TextEncodingFinder.EncodingStats findEncoding(File f)
  {
    int nbEncodings=_encodings.size();
    List<TextEncodingFinder.EncodingStats> results=new ArrayList<TextEncodingFinder.EncodingStats>(nbEncodings);
    // Compute some statistics about special chars found in each file
    {
      TextEncodingFinder.EncodingStats result;
      String encoding;
      for(int i=0;i<nbEncodings;i++)
      {
        encoding=_encodings.get(i);
        if (LOGGER.isDebugEnabled())
        {
          LOGGER.debug("Trying encoding : "+encoding);
        }
        result=evaluateEncoding(f,encoding);
        results.add(result);
      }
    }
    // Find the highest statistic
    int max=0;
    TextEncodingFinder.EncodingStats bestEncoding=null;
    {
      TextEncodingFinder.EncodingStats encodingStat;
      for(int i=0;i<nbEncodings;i++)
      {
        encodingStat=results.get(i);
        if (encodingStat!=null)
        {
          int nbLines=encodingStat.getNbLines();
          if (nbLines>max)
          {
            max=nbLines;
            bestEncoding=encodingStat;
          }
        }
      }
    }
    // Verbose results
    if ((_verbose) && (max>0))
    {
      String foundEncoding="none";
      if (bestEncoding!=null)
      {
        foundEncoding=bestEncoding.getEncoding();
      }
      LOGGER.info(f.getPath()+"\t"+foundEncoding);
      StringBuilder sb=new StringBuilder();
      String encoding;
      TextEncodingFinder.EncodingStats result;
      for(int i=0;i<nbEncodings;i++)
      {
        encoding=_encodings.get(i);
        result=results.get(i);
        if (i>0)
        {
          sb.append('/');
        }
        sb.append(encoding);
        sb.append('=');
        if (result!=null)
        {
          sb.append(result.getNbLines());
        }
        else
        {
          sb.append("ERROR");
        }
      }
      String msg=sb.toString();
      LOGGER.info(msg);
    }
    return bestEncoding;
  }

  /**
   * Evaluate an encoding on a given file.
   * @param f File to test.
   * @param encoding Encoding to test.
   * @return A statistics object, or <code>null</code> if a problem occurred.
   */
  public TextEncodingFinder.EncodingStats evaluateEncoding(File f, String encoding)
  {
    TextEncodingFinder.EncodingStats stats=null;
    boolean foundOne=false;
    int nbLines=0;
    String line=null;
    char[] chars=TextConstants.SPECIAL_CHARS.toCharArray();
    int nbChars=chars.length;
    int lineNumber=0;

    TextFileReader reader=null;
    try
    {
      reader=new TextFileReader(f,encoding);
      if (reader.start())
      {
        while (true)
        {
          line=reader.getNextLine();
          if (line==null) break;
          lineNumber++;
          for(int i=0;i<nbChars;i++)
          {
            if (line.indexOf(chars[i])!=-1)
            {
              if (_verbose)
              {
                if (!foundOne)
                {
                  LOGGER.info("Using encoding ["+encoding+"]");
                  foundOne=true;
                }
                LOGGER.info("Found ["+chars[i]+"] in line "+lineNumber+" ["+line+"]");
              }
              nbLines++;
            }
          }
        }
        stats=new TextEncodingFinder.EncodingStats(encoding,nbLines);
      }
    }
    catch(Exception e)
    {
      String msg="Error while reading file ["+f.getName()+"] with encoding ["+encoding+"]";
      LOGGER.error(msg,e);
    }
    finally
    {
      if (reader!=null)
      {
        reader.terminate();
      }
    }
    return stats;
  }

  /**
   * Check an encoding.
   * @param encoding
   * @return <code>true</code> if ok, <code>false</code> otherwise.
   */
  public static boolean checkEncoding(String encoding)
  {
    boolean ret;
    try
    {
      Charset.forName(encoding);
      ret=true;
    }
    catch(Exception e)
    {
      ret=false;
    }
    return ret;
  }

  /**
   * Encoding statistics for a file.
   * @author DAM
   */
  public static class EncodingStats
  {
    private String _encoding;
    private int _nbLines;

    /**
     * Full constructor.
     * @param encoding Encoding.
     * @param nbLines Number of lines containing char(s) encoded with this encoding.
     */
    public EncodingStats(String encoding, int nbLines)
    {
      _encoding=encoding;
      _nbLines=nbLines;
    }

    /**
     * Get the targeted encoding.
     * @return the targeted encoding.
     */
    public String getEncoding()
    {
      return _encoding;
    }

    /**
     * Get the number of lines containing char(s) encoded with this encoding.
     * @return the number of lines containing char(s) encoded with this encoding.
     */
    public int getNbLines()
    {
      return _nbLines;
    }
  }
}
