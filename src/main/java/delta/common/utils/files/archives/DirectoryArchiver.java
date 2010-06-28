package delta.common.utils.files.archives;

import java.io.File;

/**
 * @author DAM
 */
public class DirectoryArchiver
{
  public boolean go(File archiveFile, File rootDir)
  {
    ArchiveBuilder builder=new ArchiveBuilder(archiveFile);
    boolean ret=builder.start();
    if (ret)
    {
      ret=builder.addDirectory(rootDir,null);
      builder.terminate();
    }
    return ret;
  }
}
