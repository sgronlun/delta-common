package delta.common.framework.jobs.gui.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import delta.common.framework.jobs.Job;
import delta.common.framework.jobs.JobPool;
import delta.common.framework.jobs.JobPoolListener;
import delta.common.framework.jobs.JobState;
import delta.common.framework.jobs.MultiThreadedJobExecutor;
import delta.common.framework.jobs.Worker;

public class MultiThreadedProgressDialog extends JDialog implements JobPoolListener
{
  private transient MultiThreadedJobExecutor _executor;
  private transient JobPool _pool;
  private int _nbThreads;

  public MultiThreadedProgressDialog(MultiThreadedJobExecutor executor)
  {
    _executor=executor;
    _pool=executor.getPool();
    _nbThreads=executor.getNbThreads();
    buildGUI();
  }

  private JLabel[] _labels;
  private JProgressBar _progressBar;

  private void buildGUI()
  {
    _labels=new JLabel[_nbThreads];
    for(int i=0;i<_nbThreads;i++)
      _labels[i]=new JLabel("Label for job "+i);
    _progressBar=new JProgressBar(SwingConstants.HORIZONTAL,0,_pool.getNbJobs());
    _progressBar.setStringPainted(true);
    JPanel panel=new JPanel(new GridBagLayout());
    Insets insets=new Insets(5,5,5,5);
    GridBagConstraints c=new GridBagConstraints(0,0,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.NONE,insets,0,0);
    int y=0;
    for(int i=0;i<_nbThreads;i++)
    {
      c.gridy=y; panel.add(_labels[i],c); y++;
    }
    c.gridy=y;c.fill=GridBagConstraints.BOTH;
    panel.add(_progressBar,c);
    setContentPane(panel);
    int nbJobs=_pool.getNbJobs();
    setTitle(nbJobs+" jobs");
    pack();
    setModal(false);
  }

  public void start()
  {
    _pool.addJobPoolListener(this);
    setVisible(true);
  }

  public void stop()
  {
    _pool.removeJobPoolListener(this);
    setVisible(false);
    dispose();
  }

  public void jobAdded(JobPool pool, Job job)
  {
    System.out.println("jobAdded : "+job);
  }

  public void jobFinished(Worker worker, Job job)
  {
    Runnable r=new GUIUpdater(this);
    SwingUtilities.invokeLater(r);
  }

  public void jobRemoved(JobPool pool, Job job)
  {
    System.out.println("jobRemoved : "+job);
  }

  public void jobStarted(Worker worker, Job job)
  {
    Runnable r=new GUIUpdater(this);
    SwingUtilities.invokeLater(r);
  }

  public void updateGUI()
  {
    int nb=_pool.getNbJobs(JobState.FINISHED);
    _progressBar.setValue(nb);
    _progressBar.setString(nb+"/"+_pool.getNbJobs());
    Worker worker;
    Job job;
    String label;
    for(int i=0;i<_nbThreads;i++)
    {
      label="-";
      worker=_executor.getWorker(i);
      if (worker!=null)
      {
        job=worker.getCurrentJob();
        if (job!=null)
        {
          label=job.getLabel();
        }
      }
      _labels[i].setText(label);
    }
    if (_executor.isFinished())
    {
      System.out.println("Finished !");
    }
  }
}

class GUIUpdater implements Runnable
{
  private MultiThreadedProgressDialog _dialog;
  public GUIUpdater(MultiThreadedProgressDialog d)
  {
    _dialog=d;
  }

  public void run()
  {
    _dialog.updateGUI();
  }
}
