package delta.common.utils.files.archives;

import java.io.File;

/**
 * Builds archives from directories.
 * @author DAM
 */
public class DirectoryArchiver
{
  /**
   * Build an archive file that contains the given directory.
   * @param archiveFile Archive file to write.
   * @param rootDir Root directory of files to use.
   * @return <code>true</code> if it was sucessfull, <code>false</code> otherwise.
   */
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
