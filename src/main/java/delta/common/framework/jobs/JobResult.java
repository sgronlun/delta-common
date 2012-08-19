package delta.common.framework.jobs;

/**
 * @author DAM
 */
public class JobResult<T>
{
  private T _result;

  public JobResult()
  {
    
  }

  private T getResult()
  {
    return _result;
  }

  public void setResult(T result)
  {
    _result=result;
  }
}
