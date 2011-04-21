package de.chipxonio.adtech.selina.gui.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import de.chipxonio.adtech.selina.engine.SelinaJob;
import de.chipxonio.adtech.selina.engine.SelinaTask;
import de.chipxonio.adtech.selina.engine.SelinaTaskListener;
import de.chipxonio.adtech.selina.gui.TestResultViewer;
import de.chipxonio.adtech.selina.tests.TestResult;

public class TaskListPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane jScrollPane = null;
	private JPanel buttonPanel = null;
	private SelinaJob job;  //  @jve:decl-index=0:
	private JList taskList = null;
	private JButton removeTaskButton = null;

	/**
	 * This is the default constructor
	 */
	public TaskListPanel() {
		super();
		initialize();
	}
	
	public TaskListPanel(SelinaJob job) {
		this();
		this.setJob(job);
	}

	public SelinaJob getJob() {
		if (this.job == null) {
			this.job = new SelinaJob();
		}
		return job;
	}
	
	public void setJob(SelinaJob job) {
		this.job = job;
		this.getTaskList().setModel(job);
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
			taskList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					getRemoveTaskButton();
				}
			});
			taskList.setCellRenderer(new DefaultListCellRenderer(){
				private static final long serialVersionUID = 1191528509556498236L;

				@Override
				public Component getListCellRendererComponent(JList list,
						Object value, int index, boolean isSelected,
						boolean cellHasFocus) {
					JLabel l = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
							cellHasFocus);
					Icon i = null;
					SelinaTask task = (SelinaTask) value;
					switch (task.getStatus()) {
					case SelinaTaskListener.RUNNING:
						i = new ImageIcon(getClass().getClassLoader().getResource("de/chipxonio/adtech/selina/resources/loader.gif"));
						break;
					case SelinaTaskListener.COMPLETE:
						if (task.getResult().isSuccessful()) {
							i = new ImageIcon(getClass().getClassLoader().getResource("de/chipxonio/adtech/selina/resources/accept.png"));
						} else {
							i = new ImageIcon(getClass().getClassLoader().getResource("de/chipxonio/adtech/selina/resources/exclamation.png"));
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
					TestResult result = ((SelinaTask) taskList.getSelectedValue()).getResult();
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
			removeTaskButton.setIcon(new ImageIcon(getClass().getResource("/de/chipxonio/adtech/selina/resources/cross.png")));
			removeTaskButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object[] tasks = getTaskList().getSelectedValues();
					for (int i = 0; i < tasks.length; i++) getJob().remove(tasks[i]);
				}
			});
		}
		removeTaskButton.setEnabled(this.getTaskList().getSelectedIndices().length > 0);
		return removeTaskButton;
	}
}
