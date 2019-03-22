package delta.common.utils.io.xml;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;

import delta.common.utils.io.StreamTools;

/**
 * Helper to write XML documents to files.
 * @author DAM
 */
public class XmlFileWriterHelper
{
  private static final Logger LOGGER=Logger.getLogger(XmlFileWriterHelper.class);

  /**
   * Write some XML data to a file.
   * @param outFile Output file.
   * @param encoding Encoding to use.
   * @param writer Writer to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, String encoding, XmlWriter writer)
  {
    boolean ret;
    FileOutputStream fos=null;
    try
    {
      File parentFile=outFile.getAbsoluteFile().getParentFile();
      if (!parentFile.exists())
      {
        parentFile.mkdirs();
      }
      fos=new FileOutputStream(outFile);
      SAXTransformerFactory tf=(SAXTransformerFactory)TransformerFactory.newInstance();
      TransformerHandler hd=tf.newTransformerHandler();
      Transformer serializer=hd.getTransformer();
      serializer.setOutputProperty(OutputKeys.ENCODING,encoding);
      serializer.setOutputProperty(OutputKeys.INDENT,"yes");
      StreamResult streamResult=new StreamResult(fos);
      hd.setResult(streamResult);
      hd.startDocument();
      writer.writeXml(hd);
      hd.endDocument();
      ret=true;
    }
    catch (Exception exception)
    {
      LOGGER.error("Cannot write XML data to file " + outFile,exception);
      ret=false;
    }
    finally
    {
      StreamTools.close(fos);
    }
    return ret;
  }
}
