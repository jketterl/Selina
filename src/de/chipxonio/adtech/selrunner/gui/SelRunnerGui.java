package de.chipxonio.adtech.selrunner.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
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
import javax.swing.JSplitPane;
import javax.swing.ListModel;
import javax.swing.WindowConstants;

import de.chipxonio.adtech.selrunner.engine.SelRunnerEngine;
import de.chipxonio.adtech.selrunner.engine.SelRunnerEngineListener;
import de.chipxonio.adtech.selrunner.engine.SelRunnerJob;
import de.chipxonio.adtech.selrunner.engine.SelRunnerTask;
import de.chipxonio.adtech.selrunner.hosts.Host;
import de.chipxonio.adtech.selrunner.hosts.HostEditor;
import de.chipxonio.adtech.selrunner.hosts.HostList;
import de.chipxonio.adtech.selrunner.packageloader.PackageLoader;
import de.chipxonio.adtech.selrunner.packageloader.PackageLoaderException;
import de.chipxonio.adtech.selrunner.tests.TestResult;

public class SelRunnerGui extends JFrame implements SelRunnerEngineListener {

	private static final long serialVersionUID = -4222699284599413079L;
	private HostList hostList = null;
	private SelRunnerEngine engine;  //  @jve:decl-index=0:
	private PackageLoader loader;
	private JPanel jContentPane = null;
	private JButton startButton = null;
	private JList hostListGui = null;
	private JScrollPane jScrollPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JMenuBar jJMenuBar = null;
	private JMenu fileMenu = null;
	private JMenuItem fileOpenMenuItem = null;
	private JSplitPane jSplitPane = null;
	private JScrollPane jScrollPane1 = null;
	private JMenuItem fileExitMenuItem = null;
	private JList resultList = null;
	private JMenu editMenu = null;
	private JMenuItem preferencesMenuItem = null;
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
					if (loader == null) return;
					((DefaultListModel) getResultList().getModel()).clear();
					Iterator<Host> i = getHostList().iterator();
					SelRunnerJob job = new SelRunnerJob();
					while (i.hasNext()) {
						try {
							SelRunnerTask task = new SelRunnerTask(i.next(), loader.getTestSuite());
							job.addTask(task);
						} catch (PackageLoaderException e1) {
							e1.printStackTrace();
						}
					}
					getEngine().setJob(job);
					getEngine().run();
				}
			});
		}
		return startButton;
	}

	/**
	 * This method initializes hostListGui	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getHostListGui() {
		if (hostListGui == null) {
			hostListGui = new JList((ListModel) getHostList());
			hostListGui.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						Host host = (Host) hostListGui.getSelectedValue();
						new HostEditor(null, host).run();
					}
				}
				public void mousePressed(java.awt.event.MouseEvent e) {
				}
				public void mouseReleased(java.awt.event.MouseEvent e) {
				}
				public void mouseEntered(java.awt.event.MouseEvent e) {
				}
				public void mouseExited(java.awt.event.MouseEvent e) {
				}
			});
		}
		return hostListGui;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getHostListGui());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);
			jPanel.add(getJPanel1(), BorderLayout.SOUTH);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setVgap(1);
			flowLayout.setHgap(8);
			jPanel1 = new JPanel();
			jPanel1.setLayout(flowLayout);
			jPanel1.add(getJButton(), null);
			jPanel1.add(getJButton1(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("+");
			jButton.setPreferredSize(new Dimension(50, 22));
			jButton.setToolTipText("add another host");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Host host = (new HostEditor(null, null)).run();
					if (host != null) getHostList().add(host);
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("-");
			jButton1.setPreferredSize(new Dimension(50, 22));
			jButton1.setToolTipText("remove host(s)");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Host host = (Host)getHostListGui().getSelectedValue();
					if (host != null) getHostList().remove(host);
				}
			});
		}
		return jButton1;
	}
	
	private HostList getHostList() {
		if (hostList == null) {
			hostList = new HostList();
		}
		return hostList;
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
			fileMenu.add(getFileOpenMenuItem());
			fileMenu.add(getFileExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes fileOpenMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getFileOpenMenuItem() {
		if (fileOpenMenuItem == null) {
			fileOpenMenuItem = new JMenuItem();
			fileOpenMenuItem.setText("Open test package...");
			fileOpenMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser dialog = new JFileChooser();
					if (dialog.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return;
					try {
						loader = new PackageLoader(dialog.getSelectedFile());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return fileOpenMenuItem;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setLeftComponent(getJPanel());
			jSplitPane.setRightComponent(getJScrollPane1());
		}
		return jSplitPane;
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
			resultList = new JList(new DefaultListModel());
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
					(new SelRunnerPreferences(null)).setVisible(true);
				}
			});
		}
		return preferencesMenuItem;
	}
}  //  @jve:decl-index=0:visual-constraint="78,21"
