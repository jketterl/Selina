package de.chipxonio.adtech.selrunner.gui.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.chipxonio.adtech.selrunner.engine.SelRunnerJob;
import de.chipxonio.adtech.selrunner.engine.SelRunnerTask;
import de.chipxonio.adtech.selrunner.engine.SelRunnerTaskListener;
import de.chipxonio.adtech.selrunner.gui.TaskEditor;
import de.chipxonio.adtech.selrunner.library.Library;

public class TaskListPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane jScrollPane = null;
	private JPanel buttonPanel = null;
	private SelRunnerJob job;
	private JList taskList = null;
	private JButton addTaskButton = null;
	private JButton removeTaskButton = null;
	private Library library;
	private JLabel jLabel = null;

	/**
	 * This is the default constructor
	 */
	public TaskListPanel(Library l) {
		super();
		this.library = l;
		initialize();
	}
	
	public TaskListPanel(Library l, SelRunnerJob job) {
		this(l);
		this.setJob(job);
	}

	public SelRunnerJob getJob() {
		return job;
	}

	public void setJob(SelRunnerJob job) {
		this.job = job;
		this.getTaskList().setModel(job);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
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
			GridBagConstraints gridBagConstraints;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(2,5,2,5);
			buttonPanel.add(getAddTaskButton(), gridBagConstraints);
			gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(2,5,2,5);
			buttonPanel.add(getRemoveTaskButton(), gridBagConstraints);
			buttonPanel.add(jLabel, gridBagConstraints5);
		}
		return buttonPanel;
	}

	/**
	 * This method initializes taskList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getTaskList() {
		if (taskList == null) {
			taskList = new JList();
			taskList.setCellRenderer(new DefaultListCellRenderer(){
				private static final long serialVersionUID = 1191528509556498236L;

				@Override
				public Component getListCellRendererComponent(JList list,
						Object value, int index, boolean isSelected,
						boolean cellHasFocus) {
					// TODO Auto-generated method stub
					JLabel l = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
							cellHasFocus);
					switch (((SelRunnerTask) value).getStatus()) {
					case SelRunnerTaskListener.RUNNING:
						l.setIcon(new ImageIcon(getClass().getClassLoader().getResource("de/chipxonio/adtech/selrunner/resources/loader.gif")));
						break;
					case SelRunnerTaskListener.COMPLETE:
						l.setIcon(new ImageIcon(getClass().getClassLoader().getResource("de/chipxonio/adtech/selrunner/resources/accept.png")));
						break;
					}
					return l;
				}
			});
			taskList.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() != 2) return;
					(new TaskEditor(null, library, (SelRunnerTask) getTaskList().getSelectedValue())).run();
				}
			});
		}
		return taskList;
	}

	/**
	 * This method initializes addTaskButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAddTaskButton() {
		if (addTaskButton == null) {
			addTaskButton = new JButton();
			addTaskButton.setText("New Task...");
			addTaskButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SelRunnerTask task = (new TaskEditor(null, library, new SelRunnerTask())).run();
					if (task == null) return;
					getJob().add(task);
				}
			});
		}
		return addTaskButton;
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
			removeTaskButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object[] tasks = getTaskList().getSelectedValues();
					for (int i = 0; i < tasks.length; i++) getJob().remove(tasks[i]);
				}
			});
		}
		return removeTaskButton;
	}

	public void setLibrary(Library l) {
		this.library = l;
	}

}
