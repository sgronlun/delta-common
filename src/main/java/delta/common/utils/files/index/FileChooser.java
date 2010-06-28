package delta.common.utils.files.index;

import java.util.List;

/**
 * @author DAM
 */
public interface FileChooser
{
  FileData chooseFile(List<FileData> list);
}
