package de.chipxonio.adtech.selina.gui.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.TreePath;

import de.chipxonio.adtech.selina.browsers.Browser;
import de.chipxonio.adtech.selina.engine.SelinaJob;
import de.chipxonio.adtech.selina.engine.SelinaTask;
import de.chipxonio.adtech.selina.hosts.Host;
import de.chipxonio.adtech.selina.library.Library;
import de.chipxonio.adtech.selina.packages.Package;
import de.chipxonio.adtech.selina.packages.PackageLoaderException;
import de.chipxonio.adtech.selina.packages.TestDefinition;

public class TaskGenerator extends JPanel {

	private static final long serialVersionUID = 1L;
	private SelinaJob job;  //  @jve:decl-index=0:
	private Library library;
	private PackageTree packageTree = null;
	private JList hostList = null;
	private JScrollPane leftScrollPane = null;
	private JScrollPane rightScrollPane = null;
	private JSplitPane jSplitPane = null;
	private JPanel buttonPanel = null;  //  @jve:decl-index=0:visual-constraint="527,292"
	private JButton generateButton = null;
	private JLabel jLabel = null;
	private JSplitPane jSplitPane1 = null;
	private BrowserLibrary browserLibrary = null;

	/**
	 * This is the default constructor
	 */
	public TaskGenerator(SelinaJob job, Library l) {
		super();
		this.setJob(job);
		this.setLibrary(l);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel = new JLabel();
		jLabel.setText("Choose the test and machine combinations you would like to run, then click 'Generate Tasks':");
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(null, "Task Generator", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		this.add(getJSplitPane(), BorderLayout.CENTER);
		this.add(getButtonPanel(), BorderLayout.SOUTH);
		this.add(jLabel, BorderLayout.NORTH);
	}
	
	public SelinaJob getJob() {
		return job;
	}

	public void setJob(SelinaJob job) {
		this.job = job;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		if (library == null) return;
		this.library = library;
		this.getPackageTree().setList(library.getPackageList());
		this.getHostList().setModel(library.getHostList());
	}

	/**
	 * This method initializes packageTree	
	 * 	
	 * @return de.chipxonio.adtech.selina.gui.components.PackageTree	
	 */
	private PackageTree getPackageTree() {
		if (packageTree == null) {
			packageTree = new PackageTree();
			packageTree
					.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
						public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
							getGenerateButton();
						}
					});
		}
		return packageTree;
	}

	/**
	 * This method initializes hostList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getHostList() {
		if (hostList == null) {
			hostList = new JList();
			hostList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					getGenerateButton();
				}
			});
			hostList.setCellRenderer(new DefaultListCellRenderer(){
				private static final long serialVersionUID = -1451056106171374369L;

				@Override
				public Component getListCellRendererComponent(JList list,
						Object value, int index, boolean isSelected,
						boolean cellHasFocus) {
					JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
							cellHasFocus);
					int status = ((Host) value).getStatus();
					String color = "red";
					if ((status & Host.UP) == Host.UP)
						color = "green";
					else if ((status & Host.QUERYING) == Host.QUERYING)
						color = "orange";
					label.setIcon(new ImageIcon(getClass().getClassLoader().getResource("de/chipxonio/adtech/selina/resources/bullet_" + color + ".png")));
					return label;
				}
				
			});
		}
		return hostList;
	}

	/**
	 * This method initializes leftScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getLeftScrollPane() {
		if (leftScrollPane == null) {
			leftScrollPane = new JScrollPane();
			leftScrollPane.setViewportView(getPackageTree());
		}
		return leftScrollPane;
	}

	/**
	 * This method initializes rightScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getRightScrollPane() {
		if (rightScrollPane == null) {
			rightScrollPane = new JScrollPane();
			rightScrollPane.setViewportView(getHostList());
		}
		return rightScrollPane;
	}
	
	public SelinaTask[] generateTasks() {
		Vector<SelinaTask> tasks = new Vector<SelinaTask>();
		TreePath[] definitions = this.getPackageTree().getSelectionPaths();
		Object[] hosts = this.getHostList().getSelectedValues();
		Object[] browsers = this.getBrowserLibrary().getSelectedValues();
		for (int i = 0; i < definitions.length; i++) {
			for (int k = 0; k < hosts.length; k++) {
				for (int l = 0; l < browsers.length; l++) {
					Object o = definitions[i].getLastPathComponent();
					TestDefinition test;
					try {
						if (o instanceof Package) {
							test = ((Package) o).getRootTest();
						} else {
							test = (TestDefinition) o;
						}
						tasks.add(new SelinaTask((Host) hosts[k], test, (Browser) browsers[l]));
					} catch (PackageLoaderException e) {
						e.printStackTrace();
					}
				}
				/*
				if (o instanceof TestDefinition) {
					tasks.add(new SelRunnerTask((Host) hosts[k], (TestDefinition) o));
				} else if (o instanceof Package) {
					try {
						TestDefinition t = ((Package) o).getRootTest();
						tasks.add(new SelRunnerTask((Host) hosts[k], t));
					} catch (PackageLoaderException e) {
						e.printStackTrace();
					}
					
				}
				*/
			}
		}
		return (SelinaTask[]) tasks.toArray(new SelinaTask[0]);
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
			jSplitPane.setRightComponent(getJSplitPane1());
			jSplitPane.setLeftComponent(getLeftScrollPane());
		}
		return jSplitPane;
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
			GridBagConstraints gc = new GridBagConstraints();
			gc.insets = new Insets(2, 5, 2, 5);
			buttonPanel.add(getGenerateButton(), gc);
		}
		return buttonPanel;
	}

	/**
	 * This method initializes generateButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getGenerateButton() {
		if (generateButton == null) {
			generateButton = new JButton();
			generateButton.setText("Generate Tasks");
			generateButton.setIcon(new ImageIcon(getClass().getResource("/de/chipxonio/adtech/selina/resources/arrow_down.png")));
			generateButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SelinaTask[] tasks = generateTasks();
					for (int i = 0; i < tasks.length; i++) getJob().add(tasks[i]);
				}
			});
		}
		generateButton.setEnabled(
				getPackageTree().getSelectionCount() > 0 &&
				getHostList().getSelectedIndices().length > 0 &&
				getBrowserLibrary().getSelectedIndices().length > 0
		);
		return generateButton;
	}

	/**
	 * This method initializes jSplitPane1	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setDividerLocation(100);
			jSplitPane1.setLeftComponent(getRightScrollPane());
			jSplitPane1.setRightComponent(getBrowserLibrary());
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes browserLibrary	
	 * 	
	 * @return de.chipxonio.adtech.selina.gui.components.BrowserLibrary	
	 */
	private BrowserLibrary getBrowserLibrary() {
		if (browserLibrary == null) {
			browserLibrary = new BrowserLibrary();
			browserLibrary.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					getGenerateButton();
				}
			});
		}
		return browserLibrary;
	}
}  //  @jve:decl-index=0:visual-constraint="12,15"
