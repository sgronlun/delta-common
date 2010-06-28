package delta.common.utils.files;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import delta.common.utils.files.iterator.AbstractFileIteratorCallback;
import delta.common.utils.files.iterator.FileIterator;

public class FindAndCopyFiles extends AbstractFileIteratorCallback
{
  private List<String> _filesList;
  private Set<String> _filesToSearch;
  private HashMap<String,File> _foundFilesMap;
  private File _filesListFile;
  private File _srcDir;
  private File _targetDir;
  private boolean _flatTarget;

  private void traiterFichiers()
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

  private void copierFichiers()
  {
    _targetDir.mkdirs();
    File file=null;
    int nb=0;
    File srcFile;
    File parent;
    File targetDir;
    String fileName;
    for(Iterator<String> it=_filesList.iterator();it.hasNext();)
    {
      fileName=it.next();
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
        parent=file.getParentFile();
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

  private boolean parcourirFichier()
  {
    TextFileReader reader=new TextFileReader(_filesListFile);
    if (!reader.start()) return false;
    String line;
    while (true)
    {
      line=reader.getNextLine();
      if (line==null) break;
      line=line.trim();
      _filesToSearch.add(line);
      _filesList.add(line);
    }
    reader.terminate();
    return true;
  }

  public FindAndCopyFiles(String filesListFile, String srcRoot, String targetDir,
      boolean flatTarget)
  {
    _filesListFile=new File(filesListFile);
    _filesList=new ArrayList<String>();
    _srcDir=new File(srcRoot);
    _targetDir=new File(targetDir);
    _flatTarget=flatTarget;
    _filesList=new ArrayList<String>();
    _filesToSearch=new HashSet<String>();
    _foundFilesMap=new HashMap<String,File>();
    parcourirFichier();
    traiterFichiers();
    copierFichiers();
  }

  public static void main(String[] args)
  {
    new FindAndCopyFiles("/home/dm/tmp/sélectionFB.txt",
        "/home/dm/tmp/windows/ninief/Photos/Sélection Album",
        "/home/dm/tmp/windows/ninief/Photos/Sélection FB trie",false);
  }
}
