package de.chipxonio.adtech.selrunner.gui.components;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import de.chipxonio.adtech.selrunner.packages.PackageList;

public class PackageLibrary extends JPanel {

	private static final long serialVersionUID = 1L;
	private PackageList list;
	private JPanel buttonPanel = null;
	private JButton addButton = null;
	private JButton removeButton = null;
	private JScrollPane jScrollPane = null;
	private JList packageList = null;

	/**
	 * This is the default constructor
	 */
	public PackageLibrary(PackageList list) {
		super();
		this.list = list;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setLayout(new BorderLayout());
		this.add(getButtonPanel(), BorderLayout.SOUTH);
		this.add(getJScrollPane(), BorderLayout.CENTER);
	}

	/**
	 * This method initializes buttonPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = 0;
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			buttonPanel.add(getAddButton(), new GridBagConstraints());
			buttonPanel.add(getRemoveButton(), gridBagConstraints);
		}
		return buttonPanel;
	}

	/**
	 * This method initializes addButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAddButton() {
		if (addButton == null) {
			addButton = new JButton();
			addButton.setText("Add Package...");
		}
		return addButton;
	}

	/**
	 * This method initializes removeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getRemoveButton() {
		if (removeButton == null) {
			removeButton = new JButton();
			removeButton.setText("Remove Package(s)");
		}
		return removeButton;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getPackageList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes packageList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getPackageList() {
		if (packageList == null) {
			packageList = new JList((ListModel) this.list);
		}
		return packageList;
	}

}
