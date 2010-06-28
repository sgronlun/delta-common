package delta.common.utils.files.archives;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;

import org.apache.log4j.Logger;

import delta.common.utils.io.FileIO;
import delta.common.utils.io.StreamTools;
import delta.common.utils.traces.UtilsLoggers;

public class ArchiveBuilder
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  private File _archiveFile;
  private FileOutputStream fos;
  private BufferedOutputStream bos;
  private JarOutputStream jos;

  public ArchiveBuilder(File archiveFile)
  {
    _archiveFile=archiveFile;
  }

  public boolean start()
  {
    boolean ok;
    try
    {
      fos=new FileOutputStream(_archiveFile);
      bos=new BufferedOutputStream(fos);
      jos=new JarOutputStream(bos);
      ok=true;
    }
    catch (IOException ioe)
    {
      _logger.error("",ioe);
      StreamTools.close(jos);
      jos=null;
      ok=false;
    }
    return ok;
  }

  public boolean addFile(File absolute, File archivePath)
  {
    boolean ok;
    try
    {
      ZipEntry entry=new ZipEntry(archivePath.getPath());
      entry.setSize(absolute.length());
      byte[] buffer=FileIO.readFile(absolute);
      jos.putNextEntry(entry);
      jos.write(buffer);
      jos.closeEntry();
      ok=true;
    }
    catch (IOException ioe)
    {
      ok=false;
    }
    return ok;
  }

  public boolean addDirectory(File root, File localRoot)
  {
    boolean ret=true;
    File[] files=root.listFiles();
    File f;
    String name;
    File currentLocalFile;
    File currentGlobalFile;
    for(int i=0;i<files.length;i++)
    {
      f=files[i];
      name=f.getName();
      currentGlobalFile=new File(root,name);
      currentLocalFile=new File(localRoot,name);
      if (f.isDirectory())
      {
        ret=addDirectory(currentGlobalFile,currentLocalFile);
      }
      else
      {
        ret=addFile(currentGlobalFile,currentLocalFile);
      }
      if (!ret)
      {
        break;
      }
    }
    return ret;
  }

  public void terminate()
  {
    StreamTools.close(jos);
    jos=null;
    StreamTools.close(bos);
    bos=null;
    StreamTools.close(fos);
    fos=null;
  }
}
