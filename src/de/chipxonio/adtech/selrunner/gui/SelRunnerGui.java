package de.chipxonio.adtech.selrunner.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.filechooser.FileFilter;

import de.chipxonio.adtech.selrunner.engine.SelRunnerEngine;
import de.chipxonio.adtech.selrunner.engine.SelRunnerJob;
import de.chipxonio.adtech.selrunner.engine.SelRunnerJobListener;
import de.chipxonio.adtech.selrunner.engine.SelRunnerTask;
import de.chipxonio.adtech.selrunner.gui.components.TaskGenerator;
import de.chipxonio.adtech.selrunner.gui.components.TaskListPanel;
import de.chipxonio.adtech.selrunner.library.Library;
import de.chipxonio.adtech.selrunner.screenshots.Screenshot;
import de.chipxonio.adtech.selrunner.tests.TestResult;

public class SelRunnerGui extends JFrame implements SelRunnerJobListener {

	private class TaskContextMenu extends JPopupMenu {
		private static final long serialVersionUID = -2012460762563752709L;
		private JMenu showScreenshotMenu;
		private SelRunnerTask task;

		public TaskContextMenu(SelRunnerTask task) {
			super();
			this.task = task;
			this.add(getShowScreenshotMenu());
		}
		
		private JMenu getShowScreenshotMenu() {
			if (showScreenshotMenu == null) {
				showScreenshotMenu = new JMenu();
				showScreenshotMenu.setText("Show Screenshot");
				Iterator<Screenshot> i = task.getResult().getScreenshots().iterator();
				int count = 0;
				while (i.hasNext()) {
					count++;
					final Screenshot s = i.next();
					JMenuItem item = new JMenuItem("Screenshot " + count);
					item.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							(new ScreenshotViewer(null, s.getImage())).setVisible(true);
						}
					});
					showScreenshotMenu.add(item);
				}
			}
			return showScreenshotMenu;
		}
	}

	private class JobFilter extends FileFilter {
		@Override
		public String getDescription() {
			return "Selnium Runner jobs (*.srjob)";
		}
		
		@Override
		public boolean accept(File f) {
			if (f.isDirectory()) return true;
			return (getExtension(f).equals("srjob"));
		}
		
		private String getExtension(File f) {
			int i = f.getName().lastIndexOf('.');
			if (i <= 0) return "";
			return f.getName().substring(i + 1).toLowerCase();
		}
	}

	private static final long serialVersionUID = -4222699284599413079L;
	private SelRunnerEngine engine;  //  @jve:decl-index=0:
	private SelRunnerJob job;  //  @jve:decl-index=0:
	private Library library;  //  @jve:decl-index=0:
	private ListDataListener jobListener;  //  @jve:decl-index=0:
	private JPanel jContentPane = null;
	private JButton startButton = null;
	private JMenuBar jJMenuBar = null;
	private JMenu fileMenu = null;
	private JMenuItem fileExitMenuItem = null;
	private JMenu editMenu = null;
	private JMenuItem preferencesMenuItem = null;
	private JMenuItem fileSaveMenuItem = null;
	private JMenuItem fileOpenMenuItem = null;
	private JSplitPane jSplitPane = null;
	private TaskListPanel taskList = null;
	private JMenuItem fileNewMenuItem = null;
	private TaskGenerator taskGenerator = null;
	private JMenu helpMenu = null;
	private JMenuItem aboutMenuItem = null;
	/**
	 * This method initializes 
	 * 
	 */
	public SelRunnerGui() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(665, 455));
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setJMenuBar(getJJMenuBar());
        this.setTitle("Selenium Runner");
        this.setContentPane(getJContentPane());
			
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getStartButton(), BorderLayout.SOUTH);
			jContentPane.add(getJSplitPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes startButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getStartButton() {
		if (startButton == null) {
			startButton = new JButton();
			startButton.setText("Start Testing");
			startButton.setIcon(new ImageIcon(getClass().getResource("/de/chipxonio/adtech/selrunner/resources/control_play_blue.png")));
			startButton.setPreferredSize(new Dimension(113, 35));
			startButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getEngine().runJob(getJob());
				}
			});
		}
		startButton.setEnabled(getJob().size() > 0);
		return startButton;
	}
	
	private ListDataListener getJobListener(){
		if (this.jobListener == null) {
			this.jobListener = new ListDataListener() {
				@Override
				public void intervalRemoved(ListDataEvent e) {
					getStartButton();
				}
				
				@Override
				public void intervalAdded(ListDataEvent e) {
					getStartButton();
				}
				
				@Override
				public void contentsChanged(ListDataEvent e) {
					getStartButton();
				}
			};
		}
		return this.jobListener;
	}
	
	public void setJob(final SelRunnerJob job) {
		if (this.job != null) this.job.removeListDataListener(this.getJobListener());
		this.job = job;
		this.getTaskList().setJob(job);
		this.getTaskGenerator().setJob(job);
		job.addListener(this);
		job.addListDataListener(this.getJobListener());
		getStartButton();
	}
	
	public SelRunnerJob getJob() {
		if (this.job == null) {
			this.setJob(new SelRunnerJob());
		}
		return this.job;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getFileMenu());
			jJMenuBar.add(getEditMenu());
			jJMenuBar.add(getHelpMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes fileMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.add(getFileNewMenuItem());
			fileMenu.add(getFileOpenMenuItem());
			fileMenu.add(getFileSaveMenuItem());
			fileMenu.add(new JSeparator());
			fileMenu.add(getFileExitMenuItem());
		}
		return fileMenu;
	}

	public SelRunnerEngine getEngine() {
		return engine;
	}

	public void setEngine(SelRunnerEngine engine) {
		this.engine = engine;
	}

	/**
	 * This method initializes fileExitMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getFileExitMenuItem() {
		if (fileExitMenuItem == null) {
			fileExitMenuItem = new JMenuItem();
			fileExitMenuItem.setText("Exit");
			fileExitMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return fileExitMenuItem;
	}

	@Override
	public void testingComplete(TestResult result) {
		//((DefaultListModel) this.getResultList().getModel()).addElement(result);
		//this.getResultTextPane().setText(result.toString());
	}

	/**
	 * This method initializes editMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getEditMenu() {
		if (editMenu == null) {
			editMenu = new JMenu();
			editMenu.setText("Edit");
			editMenu.add(getPreferencesMenuItem());
		}
		return editMenu;
	}

	/**
	 * This method initializes preferencesMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getPreferencesMenuItem() {
		if (preferencesMenuItem == null) {
			preferencesMenuItem = new JMenuItem();
			preferencesMenuItem.setText("Preferences...");
			preferencesMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					(new PreferencesGui(null, getLibrary())).setVisible(true);
				}
			});
		}
		return preferencesMenuItem;
	}

	/**
	 * This method initializes fileSaveMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getFileSaveMenuItem() {
		if (fileSaveMenuItem == null) {
			fileSaveMenuItem = new JMenuItem();
			fileSaveMenuItem.setText("Save Job...");
			fileSaveMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser dialog = new JFileChooser();
					dialog.setFileFilter(new JobFilter());
					if (dialog.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) return;
					File f = dialog.getSelectedFile();
					if (f.getName().indexOf('.') <= 0) {
						f = new File(f.getAbsoluteFile() + ".srjob");
					}
					getJob().saveToFile(f);
				}
			});
		}
		return fileSaveMenuItem;
	}

	/**
	 * This method initializes fileOpenMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getFileOpenMenuItem() {
		if (fileOpenMenuItem == null) {
			fileOpenMenuItem = new JMenuItem();
			fileOpenMenuItem.setText("Open Job...");
			fileOpenMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser dialog = new JFileChooser();
					dialog.setFileFilter(new JobFilter());
					if (dialog.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return;
					setJob(SelRunnerJob.loadFromFile(dialog.getSelectedFile()));
				}
			});
		}
		return fileOpenMenuItem;
	}

	public void setLibrary(Library l) {
		this.library = l;
		this.getTaskGenerator().setLibrary(l);
	}
	
	public Library getLibrary() {
		return this.library;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setDividerLocation(190);
			jSplitPane.setTopComponent(getTaskGenerator());
			jSplitPane.setBottomComponent(getTaskList());
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes taskList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private TaskListPanel getTaskList() {
		if (taskList == null) {
			taskList = new TaskListPanel(this.getJob());
			taskList.getTaskList().addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e) {
					this.showPopup(e);
				}
				@Override
				public void mousePressed(MouseEvent e) {
					this.showPopup(e);
				}
				private void showPopup(MouseEvent e) {
					if (!e.isPopupTrigger()) return;
					int index = taskList.getTaskList().locationToIndex(e.getPoint());
					if (index < 0) return;
					taskList.getTaskList().setSelectedIndex(index);
					SelRunnerTask task = (SelRunnerTask)taskList.getTaskList().getModel().getElementAt(index);
					if (task == null) return;
					(new TaskContextMenu(task)).show(taskList, e.getX(), e.getY());
				}
			});
		}
		return taskList;
	}

	/**
	 * This method initializes fileNewMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getFileNewMenuItem() {
		if (fileNewMenuItem == null) {
			fileNewMenuItem = new JMenuItem();
			fileNewMenuItem.setText("New Job");
			fileNewMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					setJob(new SelRunnerJob());
				}
			});
		}
		return fileNewMenuItem;
	}

	/**
	 * This method initializes taskGenerator	
	 * 	
	 * @return de.chipxonio.adtech.selrunner.gui.components.TaskGenerator	
	 */
	private TaskGenerator getTaskGenerator() {
		if (taskGenerator == null) {
			taskGenerator = new TaskGenerator(this.getJob(), this.getLibrary());
			
		}
		return taskGenerator;
	}

	/**
	 * This method initializes helpMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText("Help");
			helpMenu.add(getAboutMenuItem());
		}
		return helpMenu;
	}

	/**
	 * This method initializes aboutMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText("About...");
			final Frame parent = this;
			aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					(new AboutDialog(parent)).setVisible(true);
				}
			});
		}
		return aboutMenuItem;
	}
}  //  @jve:decl-index=0:visual-constraint="78,21"
