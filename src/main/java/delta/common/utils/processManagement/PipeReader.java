package delta.common.utils.processManagement;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import delta.common.utils.io.StreamTools;
import delta.common.utils.traces.UtilsLoggers;

class PipeReader extends Thread
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  private InputStream _is;

  PipeReader(InputStream is)
  {
    _is=is;
  }

  @Override
  public void run()
  {
    InputStreamReader isr=null;
    BufferedReader br=null;
    try
    {
      isr=new InputStreamReader(_is);
      br=new BufferedReader(isr);
      String line_l;
      do
      {
        line_l=br.readLine();
      }
      while(line_l!=null);
    }
    catch(Exception e)
    {
    	_logger.error("",e);
    }
    finally
    {
      StreamTools.close(br);
      StreamTools.close(isr);
    }
    System.out.println("Thread ["+getName()+"] died !");
  }
}
