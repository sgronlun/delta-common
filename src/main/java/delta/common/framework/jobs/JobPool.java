package delta.common.framework.jobs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import delta.common.utils.traces.UtilsLoggers;

/**
 * Represents a pool of jobs.
 * Such a pool manages :
 * <ul>
 * <li>a set of jobs,
 * <li>a possibly empty list of listeners. Such listeners will receive job added/removed/started/finished events.
 * </ul>
 * @author DAM
 */
public class JobPool
{
  private static final Logger _logger=UtilsLoggers.getUtilsLogger();

  /**
   * List of jobs to be executed.
   */
  private List<Job> _toDo;
  /**
   * List of jobs reserved by a <tt>Worker</tt> but not yet started.
   */
  private List<Job> _reserved;
  /**
   * List of jobs currently executed by a <tt>Worker</tt>.
   */
  private List<Job> _running;
  /**
   * List of finished jobs.
   */
  private List<Job> _finished;
  /**
   * List of managed listeners.
   */
  private List<JobPoolListener> _listeners;

  /**
   * Constructor.
   */
  public JobPool()
  {
    _toDo=new ArrayList<Job>();
    _reserved=new ArrayList<Job>();
    _running=new ArrayList<Job>();
    _finished=new ArrayList<Job>();
    _listeners=new ArrayList<JobPoolListener>();
  }

  /**
   * Reserve a job from the list of jobs to do.
   * @return A <tt>Job</tt> currently in the <code>JobState.TO_DO</code> state, or <code>null</code> if no job to do.
   */
  public synchronized Job getNewJob()
  {
    while(true)
    {
      int nb=_toDo.size()+_reserved.size()+_running.size();
      if (nb>0)
      {
        if (_toDo.size()==0)
        {
          //System.out.println(Thread.currentThread().getName()+" : start to wait !");
          try
          {
            wait(1000);
          }
          catch(InterruptedException ie)
          {
            _logger.error("Wait interrupted !",ie);
          }
          //System.out.println(Thread.currentThread().getName()+" : end of wait !");
        }
        else
        {
          Job ret=_toDo.get(0);
          _toDo.remove(0);
          _reserved.add(ret);
          return ret;
        }
      }
      else
      {
        return null;
      }
    }
  }

  /**
   * Add a new job.
   * @param jobImpl Job to do.
   * @return the newly created job.
   */
  public synchronized Job addJob(JobImpl jobImpl)
  {
    Job job=new Job(jobImpl);
    _toDo.add(job);
    JobPoolListener listener;
    for(Iterator<JobPoolListener> it=_listeners.iterator();it.hasNext();)
    {
      listener=it.next();
      try
      {
        listener.jobAdded(this,job);
      }
      catch(Throwable t)
      {
        _logger.error("Error in add callback for job ["+job.getLabel()+"]");
      }
    }
    notify();
    return job;
  }

  /**
   * Indicates that the specified <code>worker</code> has started the indicated <code>job</code>.
   * @param worker Worker that will do the job.
   * @param job Involved job.
   */
  public synchronized void jobStarted(Worker worker, Job job)
  {
    if (job.getState()!=JobState.TO_DO)
    {
      _logger.error("Starting job ["+job.getLabel()+"] in state ["+job.getState()+"]");
    }
    job.setState(JobState.RUNNING);
    _reserved.remove(job);
    _running.add(job);
    JobPoolListener listener;
    for(Iterator<JobPoolListener> it=_listeners.iterator();it.hasNext();)
    {
      listener=it.next();
      try
      {
        listener.jobStarted(worker,job);
      }
      catch(Throwable t)
      {
        _logger.error("Error in start callback for job ["+job.getLabel()+"]");
      }
    }
    notify();
  }

  /**
   * Indicates that the specified <code>worker</code> has finished the indicated <code>job</code>.
   * @param worker Worker that has done the <code>job</code>.
   * @param job Involved job.
   */
  public synchronized void jobFinished(Worker worker, Job job)
  {
    if (job.getState()!=JobState.RUNNING)
    {
      _logger.error("Finished job ["+job.getLabel()+"] in state ["+job.getState()+"]");
    }
    job.setState(JobState.FINISHED);
    _running.remove(job);
    _finished.add(job);
    JobPoolListener listener;
    for(Iterator<JobPoolListener> it=_listeners.iterator();it.hasNext();)
    {
      listener=it.next();
      try
      {
        listener.jobFinished(worker,job);
      }
      catch(Throwable t)
      {
        _logger.error("Error in finished callback for job ["+job.getLabel()+"]");
      }
    }
    notify();
  }

  /**
   * Remove a job.
   * @param job Job to remove.
   */
  public synchronized void removeJob(Job job)
  {
    boolean found=_toDo.remove(job);
    if (!found) found=_reserved.remove(job);
    if (!found) found=_running.remove(job);
    if (!found) found=_finished.remove(job);
    if (found)
    {
      JobPoolListener listener;
      for(Iterator<JobPoolListener> it=_listeners.iterator();it.hasNext();)
      {
        listener=it.next();
        try
        {
          listener.jobRemoved(this,job);
        }
        catch(Throwable t)
        {
          _logger.error("Error in removed callback for job ["+job.getLabel()+"]");
        }
      }
    }
    notify();
  }

  /**
   * Add a new job pool listener.
   * @param listener Job pool listener to add.
   */
  public synchronized void addJobPoolListener(JobPoolListener listener)
  {
    _listeners.add(listener);
  }

  /**
   * Remove a new job pool listener.
   * @param listener Job pool listener to remove.
   */
  public synchronized void removeJobPoolListener(JobPoolListener listener)
  {
    _listeners.remove(listener);
  }

  /**
   * Removes all job pool listeners.
   */
  public synchronized void removeAllJobPoolListener()
  {
    _listeners.clear();
  }

  /**
   * Get the number of jobs currently in the specified <code>state</code>.
   * @param state Targeted job state.
   * @return A number of jobs.
   */
  public synchronized int getNbJobs(JobState state)
  {
    if (state==JobState.TO_DO) return _toDo.size();
    if (state==JobState.RUNNING) return _running.size();
    if (state==JobState.FINISHED) return _finished.size();
    return 0;
  }

  /**
   * Get the number of jobs referenced by this pool.
   * @return the number of jobs referenced by this pool.
   */
  public synchronized int getNbJobs()
  {
    int nb=_toDo.size();
    nb+=_reserved.size();
    nb+=_running.size();
    nb+=_finished.size();
    return nb;
  }
}
