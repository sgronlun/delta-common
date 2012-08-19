package delta.common.framework.jobs.gui.swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import delta.common.framework.jobs.JobState;
import delta.common.framework.jobs.JobStatus;
import delta.common.framework.jobs.JobStatus.JobLevelStatus;

/**
 * Controller of a job status display panel.
 * @author DAM
 */
public class JobStatusPanelController
{
  // GUI
  private JPanel _panel;
  private JButton _ctrlButton;
  private JProgressBar[] _bars;
  private JLabel[] _labels;
  
  // Data
  private JobStatus _status;
  
  /**
   * Constructor.
   * @param status Managed status.
   */
  public JobStatusPanelController(JobStatus status)
  {
    _status=status;
  }

  /**
   * Get the managed panel.
   * @return a panel.
   */
  public JPanel getPanel()
  {
    if (_panel==null)
    {
      _panel=buildPanel();
    }
    return _panel;
  }

  private JPanel buildPanel()
  {
    int nbItems=_status.getNumberOfLevels();
    JPanel panel=new JPanel(new GridBagLayout());
    Insets insets=new Insets(0,0,0,0);
    GridBagConstraints c=new GridBagConstraints(0,0,1,1,1.0,0.0,GridBagConstraints.WEST,GridBagConstraints.HORIZONTAL,insets,0,0);
    _bars=new JProgressBar[nbItems];
    _labels=new JLabel[nbItems];
    int y=1;
    for(int i=0;i<nbItems;i++)
    {
      JobLevelStatus status=_status.getLevelStatus(i);
      String label=status.getLabel();
      _labels[i]=new JLabel(label);
      panel.add(_labels[i],c);
      c.gridy=y;y++;
      int min=status.getMinimumValue();
      int max=status.getMaximumValue();
      int current=status.getCurrentValue();
      _bars[i]=new JProgressBar(min,max);
      _bars[i].setValue(current);
      _bars[i].setStringPainted(true);
      panel.add(_bars[i],c);
      c.gridy=y;y++;
    }
    _ctrlButton=new JButton("Cancel");
    ActionListener al=new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        doCtrlButton();
      }
    };
    _ctrlButton.addActionListener(al);
    GridBagConstraints bc=new GridBagConstraints(0,y,1,1,1.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.NONE,insets,0,0);
    bc.anchor=GridBagConstraints.CENTER;
    panel.add(_ctrlButton,bc);
    return panel;
  }

  public void updateDisplay()
  {
    int nbItemsData=_status.getNumberOfLevels();
    int nbItemsGUI=_bars.length;
    for(int i=0;i<nbItemsGUI;i++)
    {
      boolean visible = (i<nbItemsData);
      _bars[i].setVisible(visible);
      _labels[i].setVisible(visible);
      if (visible)
      {
        JobLevelStatus status=_status.getLevelStatus(i);
        String label=status.getLabel();
        _labels[i].setText(label);
        int min=status.getMinimumValue();
        _bars[i].setMinimum(min);
        int max=status.getMaximumValue();
        _bars[i].setMaximum(max);
        int current=status.getCurrentValue();
        _bars[i].setValue(current);
      }
    }
    updateButton();
  }

  private void updateButton()
  {
    JobState state=_status.getJobState();
    if (state==JobState.FINISHED)
    {
      _ctrlButton.setText("OK");
    }
    else if (state==JobState.RUNNING)
    {
      _ctrlButton.setText("Cancel");
    }
    else if (state==JobState.TO_DO)
    {
      _ctrlButton.setText("Start");
    }
    else
    {
      _ctrlButton.setText("???");
    }
  }

  private void doCtrlButton()
  {
    System.out.println("Ctrl button");
    JobState state=_status.getJobState();
    if (state==JobState.RUNNING)
    {
      // cancel job
    }
    else
    {
      // close window
    }
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    if (_panel!=null)
    {
      _panel.removeAll();
      _panel=null;
    }
    _ctrlButton=null;
    _bars=null;
    _labels=null;
  }
}
