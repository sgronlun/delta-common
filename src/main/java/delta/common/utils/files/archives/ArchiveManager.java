package delta.common.utils.files.archives;

import java.io.File;
import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.apache.log4j.Logger;

/**
 * Manages access to an archive file.
 * @author DAM
 */
public class ArchiveManager
{
  private static final Logger LOGGER=Logger.getLogger(ArchiveManager.class);

  private File _archiveFile;
  private JarFile _archive;
  
  /**
   * Constructor.
   * @param archiveFile Archive file to manage.
   */
  public ArchiveManager(File archiveFile)
  {
    _archiveFile=archiveFile;
    _archive=null;
  }
  
  /**
   * Open the managed archive.
   * @return <code>true</code> if open succeeded, <code>false</code> otherwise.
   */
  public boolean open()
  {
    boolean ok=false;
    try
    {
      _archive=new JarFile(_archiveFile);
      ok=true;
    }
    catch(Exception e)
    {
      LOGGER.error("Cannot open archive file ["+_archiveFile+"]");
      _archive=null;
    }      
    return ok;
  }
  
  /**
   * Get an input stream on an archive entry.
   * @param name Entry name.
   * @return An input stream or <code>null</code> if not found.
   */
  public InputStream getEntry(String name)
  {
    InputStream is=null;
    if (_archive!=null)
    {
      ZipEntry entry=_archive.getEntry(name);
      if (entry!=null)
      {
        try
        {
          is=_archive.getInputStream(entry);
        }
        catch(Exception e)
        {
          LOGGER.error("Cannot open archive entry ["+name+"] in file ["+_archiveFile+"]");
          is=null;
        }      
      }
    }
    return is;
  }
  
  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    if (_archive!=null)
    {
      try
      {
        _archive.close();
      }
      catch(Exception e)
      {
        LOGGER.error("Cannot close archive file ["+_archiveFile+"]");
        _archive=null;
      }      
    }
    _archiveFile=null;
  }
}
