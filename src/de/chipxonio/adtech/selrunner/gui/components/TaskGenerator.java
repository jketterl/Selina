package de.chipxonio.adtech.selrunner.gui.components;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JList;

import de.chipxonio.adtech.selrunner.engine.SelRunnerJob;
import de.chipxonio.adtech.selrunner.engine.SelRunnerTask;
import de.chipxonio.adtech.selrunner.hosts.Host;
import de.chipxonio.adtech.selrunner.library.Library;
import de.chipxonio.adtech.selrunner.packages.PackageLoaderException;
import de.chipxonio.adtech.selrunner.packages.TestDefinition;
import de.chipxonio.adtech.selrunner.packages.Package;

import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.tree.TreePath;

import java.awt.GridBagConstraints;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class TaskGenerator extends JPanel {

	private static final long serialVersionUID = 1L;
	private SelRunnerJob job;  //  @jve:decl-index=0:
	private Library library;
	private PackageTree packageTree = null;
	private JList hostList = null;
	private JScrollPane leftScrollPane = null;
	private JScrollPane rightScrollPane = null;
	private JSplitPane jSplitPane = null;
	private JPanel buttonPanel = null;  //  @jve:decl-index=0:visual-constraint="527,292"
	private JButton generateButton = null;
	private JLabel jLabel = null;

	/**
	 * This is the default constructor
	 */
	public TaskGenerator(SelRunnerJob job, Library l) {
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
	
	public SelRunnerJob getJob() {
		return job;
	}

	public void setJob(SelRunnerJob job) {
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
	 * @return de.chipxonio.adtech.selrunner.gui.components.PackageTree	
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
	
	public SelRunnerTask[] generateTasks() {
		Vector<SelRunnerTask> tasks = new Vector<SelRunnerTask>();
		TreePath[] definitions = this.getPackageTree().getSelectionPaths();
		Object[] hosts = this.getHostList().getSelectedValues();
		for (int i = 0; i < definitions.length; i++) {
			for (int k = 0; k < hosts.length; k++) {
				Object o = definitions[i].getLastPathComponent();
				if (o instanceof TestDefinition) {
					tasks.add(new SelRunnerTask((Host) hosts[k], (TestDefinition) o));
				} else if (o instanceof Package) {
					try {
						TestDefinition[] t = ((Package) o).getTests();
						for (int l = 0; l < t.length; l++) {
							tasks.add(new SelRunnerTask((Host) hosts[k], t[l]));
						}
					} catch (PackageLoaderException e) {
						e.printStackTrace();
					}
					
				}
			}
		}
		return (SelRunnerTask[]) tasks.toArray(new SelRunnerTask[0]);
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setLeftComponent(getLeftScrollPane());
			jSplitPane.setRightComponent(getRightScrollPane());
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
			generateButton.setIcon(new ImageIcon(getClass().getResource("/de/chipxonio/adtech/selrunner/resources/arrow_down.png")));
			generateButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SelRunnerTask[] tasks = generateTasks();
					for (int i = 0; i < tasks.length; i++) getJob().add(tasks[i]);
				}
			});
		}
		generateButton.setEnabled(getPackageTree().getSelectionCount() > 0 && getHostList().getSelectedIndices().length > 0);
		return generateButton;
	}
}  //  @jve:decl-index=0:visual-constraint="12,15"
