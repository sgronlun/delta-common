package delta.common.utils.text;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.files.TextFileReader;
import delta.common.utils.files.TextFileWriter;
import delta.common.utils.misc.MiscStringConstants;
import delta.common.utils.traces.UtilsLoggers;

/**
 * Text file converter.
 * @author DAM
 */
public class TextFileConverter
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  private String _sourceEncoding;
  private String _destEncoding;
  private String _destEOL;

  /**
   * Constructor.
   */
  public TextFileConverter()
  {
    this(null,EncodingNames.DEFAULT_ENCODING,null);
  }

  /**
   * Constructor.
   * @param sourceEncoding Source encoding, <code>null</code> for auto-detect.
   * @param destEncoding Destination encoding (mandatory).
   * @param destEOL Destination End of Line, <code>null</code> for native EOL.
   */
  public TextFileConverter(String sourceEncoding, String destEncoding, String destEOL)
  {
    _sourceEncoding=sourceEncoding;
    if (sourceEncoding!=null)
    {
      boolean sourceOK=TextEncodingFinder.checkEncoding(sourceEncoding);
      if (!sourceOK)
      {
        throw new IllegalArgumentException("Invalid source encoding : "+sourceEncoding);
      }
    }
    _destEncoding=destEncoding;
    if (destEncoding!=null)
    {
      boolean destOK=TextEncodingFinder.checkEncoding(destEncoding);
      if (!destOK)
      {
        throw new IllegalArgumentException("Invalid destination encoding : "+destEncoding);
      }
    }
    else
    {
      _destEncoding=EncodingNames.DEFAULT_ENCODING;
    }
    _destEOL=destEOL;
    if (destEOL==null)
    {
      _destEOL=MiscStringConstants.NATIVE_EOL;
    }
  }

  private String getSourceEncoding(File sourceFile)
  {
    String ret=null;
    if (_sourceEncoding==null)
    {
      List<String> encodings=TextEncodingFinder.getDefaultEncodings();
      TextEncodingFinder finder=new TextEncodingFinder(encodings);
      TextEncodingFinder.EncodingStats result=finder.findEncoding(sourceFile);
      if (result!=null)
      {
        ret=result.getEncoding();
      }
      else
      {
        ret=EncodingNames.DEFAULT_ENCODING;
        _logger.warn("No encoding specified and cannot find out the encoding of file ["+sourceFile+"]. Using default platform encoding ["+ret+"].");
      }
    }
    else
    {
      ret=_sourceEncoding;
    }
    return ret;
  }

  private String getDestEncoding()
  {
    String ret=_destEncoding;
    return ret;
  }

  /**
   * Converts a text file (which uses source encoding) into another text file
   * which uses destination encoding and specified end of line.
   * @param sourceFile Source file.
   * @param destFile Destination file.
   * @return <code>true</code> if it was successfull, <code>false</code> otherwise.
   */
  public boolean convert(File sourceFile, File destFile)
  {
    boolean ok;
    String sourceEncoding=getSourceEncoding(sourceFile);
    String destEncoding=getDestEncoding();

    TextFileReader reader=new TextFileReader(sourceFile,sourceEncoding);
    if (reader.start())
    {
      TextFileWriter writer=new TextFileWriter(destFile,destEncoding,_destEOL);
      if (writer.start())
      {
        String line=null;
        String newLine=null;
        ok=true;
        while (true)
        {
          line=reader.getNextLine();
          if (line==null) break;
          newLine=line;
          boolean writeOK=writer.writeNextLine(newLine);
          if (!writeOK)
          {
            ok=false;
            break;
          }
        }
        writer.terminate();
      }
      else
      {
        ok=false;
      }
      reader.terminate();
    }
    else
    {
      ok=false;
    }
    return ok;
  }
}
