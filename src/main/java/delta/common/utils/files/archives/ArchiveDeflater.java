package delta.common.utils.files.archives;

import java.io.File;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

import delta.common.utils.files.FileCopy;
import delta.common.utils.traces.UtilsLoggers;

public class ArchiveDeflater
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  private File _archivePath;
	private File _root;

	public ArchiveDeflater(File archivePath, File root)
	{
		_archivePath=archivePath;
		_root=root;
	}

  public boolean go()
  {
  	if (!_root.mkdirs())
  	{
      _logger.error("Cannot create root dir ["+_root+"]");
  		return false;
  	}
  	boolean ok=true;
  	JarFile jarFile=null;
  	try
    {
  		jarFile=new JarFile(_archivePath);
      Enumeration<JarEntry> enumeration=jarFile.entries();
      JarEntry e=null;
      InputStream is=null;
      File current;
      while (enumeration.hasMoreElements())
      {
        e=enumeration.nextElement();
        current=new File(_root,e.getName().replace('/',File.separatorChar));
        if (e.isDirectory())
        {
        	if (!current.mkdirs())
        	{
        		_logger.error("Cannot create directory ["+current+"]");
        		ok=false;
        		break;
        	}
        }
        else
        {
          current.getParentFile().mkdirs();
          is=jarFile.getInputStream(e);
          ok=FileCopy.copy(is,current);
          if (!ok)
          {
        		break;
          }
        }
      }
      if (jarFile!=null) jarFile.close();
    }
    catch(Exception e)
    {
      _logger.error("",e);
    	ok=false;
    }
    return ok;
  }
}
