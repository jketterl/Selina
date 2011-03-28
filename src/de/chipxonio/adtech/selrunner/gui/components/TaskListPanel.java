package de.chipxonio.adtech.selrunner.gui.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.chipxonio.adtech.selrunner.engine.SelRunnerJob;
import de.chipxonio.adtech.selrunner.engine.SelRunnerTask;
import de.chipxonio.adtech.selrunner.engine.SelRunnerTaskListener;
import de.chipxonio.adtech.selrunner.gui.TestResultViewer;
import de.chipxonio.adtech.selrunner.tests.TestResult;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class TaskListPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane jScrollPane = null;
	private JPanel buttonPanel = null;
	private SelRunnerJob job;  //  @jve:decl-index=0:
	private JList taskList = null;
	private JButton removeTaskButton = null;
	private ListDataListener jobListener;  //  @jve:decl-index=0:

	/**
	 * This is the default constructor
	 */
	public TaskListPanel() {
		super();
		initialize();
	}
	
	public TaskListPanel(SelRunnerJob job) {
		this();
		this.setJob(job);
	}

	public SelRunnerJob getJob() {
		if (this.job == null) {
			this.job = new SelRunnerJob();
		}
		return job;
	}
	
	private ListDataListener getJobListener(){
		if (jobListener == null) {
			jobListener = new ListDataListener() {
				@Override
				public void intervalRemoved(ListDataEvent e) {
					getRemoveTaskButton();
				}
				
				@Override
				public void intervalAdded(ListDataEvent e) {
					getRemoveTaskButton();
				}
				
				@Override
				public void contentsChanged(ListDataEvent e) {
					getRemoveTaskButton();
				}
			};
		}
		return jobListener;
	}

	public void setJob(SelRunnerJob job) {
		if (this.job != null) this.job.removeListDataListener(this.getJobListener());
		this.job = job;
		this.getTaskList().setModel(job);
		job.addListDataListener(this.getJobListener());
		getRemoveTaskButton();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(null, "Task Queue", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		this.add(getJScrollPane(), BorderLayout.CENTER);
		this.add(getButtonPanel(), BorderLayout.SOUTH);
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getTaskList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes buttonPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(2,5,2,5);
			buttonPanel.add(getRemoveTaskButton(), gridBagConstraints);
		}
		return buttonPanel;
	}

	/**
	 * This method initializes taskList	
	 * 	
	 * @return javax.swing.JList	
	 */
	public JList getTaskList() {
		if (taskList == null) {
			taskList = new JList();
			taskList.setCellRenderer(new DefaultListCellRenderer(){
				private static final long serialVersionUID = 1191528509556498236L;

				@Override
				public Component getListCellRendererComponent(JList list,
						Object value, int index, boolean isSelected,
						boolean cellHasFocus) {
					JLabel l = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
							cellHasFocus);
					Icon i = null;
					SelRunnerTask task = (SelRunnerTask) value;
					switch (task.getStatus()) {
					case SelRunnerTaskListener.RUNNING:
						i = new ImageIcon(getClass().getClassLoader().getResource("de/chipxonio/adtech/selrunner/resources/loader.gif"));
						break;
					case SelRunnerTaskListener.COMPLETE:
						if (task.getResult().isSuccessful()) {
							i = new ImageIcon(getClass().getClassLoader().getResource("de/chipxonio/adtech/selrunner/resources/accept.png"));
						} else {
							i = new ImageIcon(getClass().getClassLoader().getResource("de/chipxonio/adtech/selrunner/resources/exclamation.png"));
						}
						break;
					default:
						i = new Icon() {
							@Override
							public void paintIcon(Component c, Graphics g, int x, int y) {
								// NOOP - this is an empty icon.
							}
							
							@Override
							public int getIconWidth() {
								return 16;
							}
							
							@Override
							public int getIconHeight() {
								return 16;
							}
						};
					}
					if (i != null) {
						l.setIcon(i);
						// TODO this is pretty exhausting, but it's necessary to make animated gifs animated.
						// unfortunately it also makes the list repaint itself constantly. it doesn't consume very much
						// cpu, so i'll leave it in. better solutions are however welcome.
						if (i instanceof ImageIcon) ((ImageIcon) i).setImageObserver(list);
					}
					return l;
				}
			});
			taskList.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() != 2) return;
					TestResult result = ((SelRunnerTask) taskList.getSelectedValue()).getResult();
					(new TestResultViewer(null, result)).setVisible(true);
					
				}
			});
		}
		return taskList;
	}

	/**
	 * This method initializes removeTaskButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getRemoveTaskButton() {
		if (removeTaskButton == null) {
			removeTaskButton = new JButton();
			removeTaskButton.setText("Remove Task(s)");
			removeTaskButton.setIcon(new ImageIcon(getClass().getResource("/de/chipxonio/adtech/selrunner/resources/cross.png")));
			removeTaskButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object[] tasks = getTaskList().getSelectedValues();
					for (int i = 0; i < tasks.length; i++) getJob().remove(tasks[i]);
				}
			});
		}
		removeTaskButton.setEnabled(this.getJob().size() > 0);
		return removeTaskButton;
	}
}
