package de.chipxonio.adtech.selina.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import de.chipxonio.adtech.selina.engine.SelinaTask;
import de.chipxonio.adtech.selina.gui.components.PackageTree;
import de.chipxonio.adtech.selina.hosts.Host;
import de.chipxonio.adtech.selina.library.Library;
import de.chipxonio.adtech.selina.packages.TestDefinition;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Dimension;

import javax.swing.JScrollPane;

public class TaskEditor extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel buttonPanel = null;
	private JButton okButton = null;
	private JButton cancelButton = null;
	private SelinaTask task;  //  @jve:decl-index=0:
	private boolean confirmed = false;
	private JPanel jContentPane = null;
	private JPanel contentPanel = null;
	private JLabel jLabel = null;
	private JComboBox hostComboBox = null;
	private PackageTree packageTree = null;
	private Library library;
	private JScrollPane jScrollPane = null;  //  @jve:decl-index=0:visual-constraint="435,217"

	/**
	 * @param owner
	 */
	public TaskEditor(Frame owner, Library l, SelinaTask task) {
		super(owner);
		this.library = l;
		this.task = task;
		initialize();
	}
	
	public SelinaTask run() {
		this.setVisible(true);
		if (!this.confirmed) return null;
		return task;
	}

	public SelinaTask getTask() {
		return task;
	}

	public void setTask(SelinaTask task) {
		this.task = task;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		this.setModal(true);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setTitle("Task Editor");
	}

	/**
	 * This method initializes buttonPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.insets = new Insets(2, 5, 2, 5);
			gridBagConstraints2.insets = new Insets(2, 5, 2, 5);
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			buttonPanel.add(getOkButton(), gridBagConstraints1);
			buttonPanel.add(getCancelButton(), gridBagConstraints2);
		}
		return buttonPanel;
	}

	/**
	 * This method initializes okButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText("OK");
			okButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					task.setHost((Host) getHostComboBox().getSelectedItem());
					task.setTest((TestDefinition) getPackageTree().getSelectionPath().getLastPathComponent());
					confirmed = true;
					dispose();
				}
			});
		}
		return okButton;
	}

	/**
	 * This method initializes cancelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton();
			cancelButton.setText("Cancel");
			cancelButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return cancelButton;
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
			jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
			jContentPane.add(getContentPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes contentPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getContentPanel() {
		if (contentPanel == null) {
			GridBagConstraints gc;
			jLabel = new JLabel();
			jLabel.setText("Host:");
			contentPanel = new JPanel();
			contentPanel.setLayout(new GridBagLayout());
			gc = new GridBagConstraints();
			gc.gridx = 0;
			gc.gridy = 0;
			gc.insets = new Insets(2, 5, 2, 5);
			gc.weightx = 0;
			gc.anchor = GridBagConstraints.WEST;
			contentPanel.add(jLabel, gc);
			gc = new GridBagConstraints();
			gc.gridx = 1;
			gc.gridy = 0;
			gc.weightx = 1;
			gc.fill = GridBagConstraints.HORIZONTAL;
			gc.insets = new Insets(2, 5, 2, 5);
			contentPanel.add(getHostComboBox(), gc);
			gc = new GridBagConstraints();
			gc.gridx = 0;
			gc.gridy = 1;
			gc.gridwidth = 2;
			gc.insets = new Insets(2, 5, 2, 5);
			gc.fill = GridBagConstraints.BOTH;
			gc.weightx = 1;
			gc.weighty = 1;
			contentPanel.add(getJScrollPane(), gc);
		}
		return contentPanel;
	}

	/**
	 * This method initializes hostComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getHostComboBox() {
		if (hostComboBox == null) {
			hostComboBox = new JComboBox(this.library.getHostList());
			hostComboBox.setPreferredSize(new Dimension(200, 22));
		}
		return hostComboBox;
	}

	/**
	 * This method initializes packageTree	
	 * 	
	 * @return de.chipxonio.adtech.selina.gui.components.PackageTree	
	 */
	private PackageTree getPackageTree() {
		if (packageTree == null) {
			packageTree = new PackageTree(this.library.getPackageList());
		}
		return packageTree;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getPackageTree());
		}
		return jScrollPane;
	}

}
