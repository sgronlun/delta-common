package delta.common.utils.files.index;

import java.util.List;

/**
 * Interface of a file chooser.
 * @author DAM
 */
public interface FileChooser
{
  /**
   * Choose a file between a series of file data.
   * @param list File data to choose from.
   * @return A file or <code>null</code> if nothing was chosen.
   */
  FileData chooseFile(List<FileData> list);
}
