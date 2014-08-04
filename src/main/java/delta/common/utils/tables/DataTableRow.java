package delta.common.utils.tables;

import java.io.PrintStream;

/**
 * A row of data in a data table.
 * @author DAM
 */
public class DataTableRow
{
  private Object[] _data;

  /**
   * Constructor.
   * @param nbColumns Number of columns to use.
   */
  public DataTableRow(int nbColumns)
  {
    _data=new Object[nbColumns];
  }

  public Object getData(int columnIndex)
  {
    Object ret=_data[columnIndex];
    return ret;
  }

  public boolean setData(int columnIndex, Object o)
  {
    if (_data.length<columnIndex)
    {
      Object[] newData=new Object[columnIndex];
      System.arraycopy(_data,0,newData,0,_data.length);
      _data=newData;
    }
    _data[columnIndex]=o;
    return true;
  }

  public void dump(PrintStream ps)
  {
    int nb=_data.length;
    Object o;
    for(int i=0;i<nb;i++)
    {
      if (i>0) ps.print('\t');
      o=_data[i];
      ps.print(o);
    }
    ps.print('\n');
  }
}
