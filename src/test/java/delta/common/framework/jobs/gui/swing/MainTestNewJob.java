package delta.common.framework.jobs.gui.swing;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import delta.common.framework.jobs.SimpleJobStatus;
import delta.common.framework.jobs.SimpleJobStatus.SimpleJobLevelStatus;
import delta.common.framework.jobs.gui.swing.JobStatusPanelController;

/**
 * @author DAM
 */
public class MainTestNewJob
{
  /**
   * @param args
   */
  public static void main(String[] args)
  {
    SimpleJobStatus status=new SimpleJobStatus(2);
    status.setJobTitle("Character log update");
    SimpleJobLevelStatus level1=status.getEditableLevelStatus(0);
    level1.setRange(0,1);
    level1.setCurrentValue(0);
    level1.setLabel("Reading new log entries");
    SimpleJobLevelStatus level2=status.getEditableLevelStatus(1);
    level2.setRange(1,12);
    level2.setCurrentValue(1);
    level2.setLabel("Page 1 of 12");

    JobStatusPanelController ctrl=new JobStatusPanelController(status);
    JPanel panel=ctrl.getPanel();
    JFrame frame=new JFrame("Test");
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(panel,BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }
}
