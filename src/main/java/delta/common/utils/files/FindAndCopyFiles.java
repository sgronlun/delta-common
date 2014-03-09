package delta.common.utils.files;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import delta.common.utils.files.iterator.AbstractFileIteratorCallback;
import delta.common.utils.files.iterator.FileIterator;

/**
 * Find and copy a selection of files from a source directory.
 * @author DAM
 */
public class FindAndCopyFiles extends AbstractFileIteratorCallback
{
  private List<String> _filesList;
  private Set<String> _filesToSearch;
  private HashMap<String,File> _foundFilesMap;
  private HashMap<String,File> _targetStructure;
  private File _filesListFile;
  private File _srcDir;
  private File _targetDir;
  private boolean _flatTarget;
  private boolean _sourceFileGivesStructure;

  private void doIt()
  {
    FileIterator fi=new FileIterator(_srcDir,true,this);
    fi.run();
  }

  @Override
  public void handleFile(File absFile, File relFile)
  {
    if (_filesToSearch.contains(absFile.getName()))
    {
      _filesToSearch.remove(absFile.getName());
      _foundFilesMap.put(absFile.getName(),relFile);
    }
  }

  private void copyFiles()
  {
    _targetDir.mkdirs();
    File file=null;
    int nb=0;
    File srcFile;
    File parent;
    File targetDir;
    for(String fileName : _filesList)
    {
      file=_foundFilesMap.get(fileName);
      if (file==null)
      {
        System.out.println("Fichier non trouvé : "+fileName);
        continue;
      }
      srcFile=new File(_srcDir,file.getPath());
      if (_flatTarget)
      {
        targetDir=_targetDir;
      }
      else
      {
        if (_sourceFileGivesStructure)
        {
          parent=null;
          File toFile=_targetStructure.get(fileName);
          if (toFile!=null)
          {
            parent=toFile.getParentFile();
          }
        }
        else
        {
          parent=file.getParentFile();
        }
        if (parent!=null)
        {
          targetDir=new File(_targetDir,parent.getPath());
          targetDir.mkdirs();
        }
        else
        {
          targetDir=_targetDir;
        }
      }
      FileCopy.copyToDir(srcFile,targetDir);
      nb++;
    }
    System.out.println("Nb fichiers : "+nb);
  }

  private boolean readInputFile()
  {
    TextFileReader reader=new TextFileReader(_filesListFile);
    if (!reader.start()) return false;
    String line;
    while (true)
    {
      line=reader.getNextLine();
      if (line==null) break;
      line=line.trim();
      if (_sourceFileGivesStructure)
      {
        File f=new File(line);
        line=f.getName();
        _targetStructure.put(line,f);
      }
      _filesToSearch.add(line);
      _filesList.add(line);
    }
    reader.terminate();
    return true;
  }

  /**
   * Constructor.
   * @param filesListFile List of files to find.
   * @param srcRoot Root directory for source files.
   * @param targetDir Root directory for copied files.
   * @param flatTarget Indicates if the target directory will contain
   * flat files or 
   * @param sourceFileGivesStructure
   */
  public FindAndCopyFiles(String filesListFile, String srcRoot, String targetDir,
      boolean flatTarget, boolean sourceFileGivesStructure)
  {
    _filesListFile=new File(filesListFile);
    _filesList=new ArrayList<String>();
    _srcDir=new File(srcRoot);
    _targetDir=new File(targetDir);
    _flatTarget=flatTarget;
    _sourceFileGivesStructure=sourceFileGivesStructure;
    _filesList=new ArrayList<String>();
    _filesToSearch=new HashSet<String>();
    _foundFilesMap=new HashMap<String,File>();
    _targetStructure=new HashMap<String,File>();
    readInputFile();
    doIt();
    copyFiles();
  }

  /**
   * Main method for this tool.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    /*
    new FindAndCopyFiles("/home/dm/tmp/sélectionFB.txt",
        "/home/dm/tmp/windows/ninief/Photos/Sélection Album",
        "/home/dm/tmp/windows/ninief/Photos/Sélection FB trie",false,false);
    */
    new FindAndCopyFiles("/home/dm/tmp/toto.txt",
        "/home/dm/tmp/usa/originaux",
        "/home/dm/tmp/windows/d/tmp",false,true);
  }
}
