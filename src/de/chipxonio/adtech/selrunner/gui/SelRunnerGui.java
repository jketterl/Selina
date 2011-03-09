package de.chipxonio.adtech.selrunner.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;

import de.chipxonio.adtech.selrunner.engine.SelRunnerEngine;
import de.chipxonio.adtech.selrunner.engine.SelRunnerEngineListener;
import de.chipxonio.adtech.selrunner.engine.SelRunnerJob;
import de.chipxonio.adtech.selrunner.engine.SelRunnerTask;
import de.chipxonio.adtech.selrunner.gui.components.TaskListPanel;
import de.chipxonio.adtech.selrunner.library.Library;
import de.chipxonio.adtech.selrunner.packages.PackageLoaderException;
import de.chipxonio.adtech.selrunner.tests.TestResult;

public class SelRunnerGui extends JFrame implements SelRunnerEngineListener {

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
	private JPanel jContentPane = null;
	private JButton startButton = null;
	private JMenuBar jJMenuBar = null;
	private JMenu fileMenu = null;
	private JScrollPane jScrollPane1 = null;
	private JMenuItem fileExitMenuItem = null;
	private JList resultList = null;
	private JMenu editMenu = null;
	private JMenuItem preferencesMenuItem = null;
	private JMenuItem fileSaveMenuItem = null;
	private JMenuItem fileOpenMenuItem = null;
	private JSplitPane jSplitPane = null;
	private TaskListPanel taskList = null;
	private JMenuItem jMenuItem = null;
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
        this.setSize(new Dimension(667, 341));
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
			startButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					((DefaultListModel) getResultList().getModel()).clear();
					getEngine().runJob(getJob());
				}
			});
		}
		return startButton;
	}
	
	public void setJob(SelRunnerJob job) {
		this.job = job;
		this.getTaskList().setJob(job);
	}
	
	public SelRunnerJob getJob() {
		if (this.job == null) {
			this.setJob(new SelRunnerJob());
		}
		return this.job;
		/*
		Iterator<Host> i = this.getLibrary().getHostList().iterator();
		SelRunnerJob job = new SelRunnerJob();
		while (i.hasNext()) {
			try {
				Host host = i.next();
				Class<AbstractTest>[] tests = pack.getTests();
				for (int k = 0; k < tests.length; k++) {
					SelRunnerTask task = new SelRunnerTask(host, tests[k]);
					job.add(task);
				}
			} catch (PackageLoaderException e1) {
				e1.printStackTrace();
			}
		}
		return job;
		*/
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
			fileMenu.add(getJMenuItem());
			fileMenu.add(getFileOpenMenuItem());
			fileMenu.add(getFileSaveMenuItem());
			fileMenu.add(new JSeparator());
			fileMenu.add(getFileExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getResultList());
		}
		return jScrollPane1;
	}
	
	public SelRunnerEngine getEngine() {
		return engine;
	}

	public void setEngine(SelRunnerEngine engine) {
		if (this.engine != null) this.engine.removeListener(this);
		this.engine = engine;
		this.engine.addListener(this);
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
					// TODO: this is not so pretty. we should return to the SelRunner at this point
					System.exit(0);
				}
			});
		}
		return fileExitMenuItem;
	}

	@Override
	public void testingComplete(TestResult result) {
		((DefaultListModel) this.getResultList().getModel()).addElement(result);
		//this.getResultTextPane().setText(result.toString());
	}

	/**
	 * This method initializes resultList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getResultList() {
		if (resultList == null) {
			final JFrame parent = this;
			resultList = new JList(new DefaultListModel());
			resultList.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() != 2) return;
					TestResult result = (TestResult) getResultList().getSelectedValue();
					Iterator<Image> i = result.getScreenshots().iterator();
					while (i.hasNext()) (new ScreenshotViewer(parent, i.next())).setVisible(true);
				}
			});
		}
		return resultList;
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
		this.taskList.setLibrary(l);
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
			jSplitPane.setDividerLocation(400);
			jSplitPane.setRightComponent(getJScrollPane1());
			jSplitPane.setLeftComponent(getTaskList());
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
			taskList = new TaskListPanel(this.getLibrary(), this.getJob());
		}
		return taskList;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem() {
		if (jMenuItem == null) {
			jMenuItem = new JMenuItem();
			jMenuItem.setText("generate task");
			jMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						SelRunnerTask task = new SelRunnerTask(
							getLibrary().getHostList().get(2),
							getLibrary().getPackageList().get(0).getTests()[0]
						);
						getJob().add(task);
					} catch (PackageLoaderException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return jMenuItem;
	}
}  //  @jve:decl-index=0:visual-constraint="78,21"
