package delta.common.utils.files;

import java.io.File;

public class Path
{
  private String[] _paths;
  private String _path;

  public Path()
  {
    _paths=new String[]{};
    _path="";
  }

  public Path(String path)
  {
    _paths=new String[]{path};
    _path=path;
  }

  private Path(String[] paths, String path)
  {
    _paths=paths;
    _path=path;
  }

  public Path buildChildPath(String childPath)
  {
    String[] paths=new String[_paths.length+1];
    System.arraycopy(_paths,0,paths,0,_paths.length);
    paths[_paths.length]=childPath;
    String path;
    if (_path.length()==0)
    {
      path=childPath;
    }
    else
    {
      path=_path+File.separator+childPath;
    }
    return new Path(paths,path);
  }

  public String getPath()
  {
    return _path;
  }

  public int getLevel()
  {
    if (_paths==null) return 0;
    return _paths.length;
  }

  public String getPath(int level)
  {
    if (_paths==null) return null;
    return _paths[level];
  }

  @Override
  public String toString()
  {
    return _path;
  }
}
