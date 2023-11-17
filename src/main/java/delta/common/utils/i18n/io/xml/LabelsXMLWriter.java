package delta.common.utils.i18n.io.xml;

import java.io.File;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import delta.common.utils.i18n.SingleLocaleLabelsManager;
import delta.common.utils.io.xml.XmlFileWriterHelper;
import delta.common.utils.io.xml.XmlWriter;

/**
 * Writes labels to XML files.
 * @author DAM
 */
public class LabelsXMLWriter
{
  /**
   * Write a labels manager to an XML file.
   * @param outFile Output file.
   * @param mgr Data to write.
   * @param encoding Encoding to use.
   * @return <code>true</code> if it succeeds, <code>false</code> otherwise.
   */
  public boolean write(File outFile, final SingleLocaleLabelsManager mgr, String encoding)
  {
    XmlFileWriterHelper helper=new XmlFileWriterHelper();
    XmlWriter writer=new XmlWriter()
    {
      @Override
      public void writeXml(TransformerHandler hd) throws Exception
      {
        writeLabels(hd,mgr);
      }
    };
    boolean ret=helper.write(outFile,encoding,writer);
    return ret;
  }

  /**
   * Write a labels manager to the given XML stream.
   * @param hd XML output stream.
   * @param mgr Data to write.
   * @throws SAXException If an error occurs.
   */
  private void writeLabels(TransformerHandler hd, SingleLocaleLabelsManager mgr) throws SAXException
  {
    AttributesImpl attrs=new AttributesImpl();
    // Locale
    String locale=mgr.getLocale();
    attrs.addAttribute("","",LabelsXMLConstants.LABELS_LOCALE_ATTR,XmlWriter.CDATA,locale);
    hd.startElement("","",LabelsXMLConstants.LABELS_TAG,attrs);
    List<String> keys=mgr.getKeys();
    for(String key : keys)
    {
      AttributesImpl entryAttrs=new AttributesImpl();
      // Key
      entryAttrs.addAttribute("","",LabelsXMLConstants.LABEL_KEY_ATTR,XmlWriter.CDATA,key);
      // Value
      String value=mgr.getLabel(key);
      entryAttrs.addAttribute("","",LabelsXMLConstants.LABEL_VALUE_ATTR,XmlWriter.CDATA,value);
      hd.startElement("","",LabelsXMLConstants.LABEL_TAG,entryAttrs);
      hd.endElement("","",LabelsXMLConstants.LABEL_TAG);
    }
    hd.endElement("","",LabelsXMLConstants.LABELS_TAG);
  }
}
