package de.chipxonio.adtech.selina.gui.components;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import de.chipxonio.adtech.selina.packages.Package;
import de.chipxonio.adtech.selina.packages.PackageList;

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
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.insets = new Insets(2, 5, 2, 5);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.insets = new Insets(2, 5, 2, 5);
			gridBagConstraints.gridy = 0;
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			buttonPanel.add(getAddButton(), gridBagConstraints3);
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
			addButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser dialog = new JFileChooser();
					if (dialog.showOpenDialog(getParent()) != JFileChooser.APPROVE_OPTION) return;
					try {
						list.add(new Package(dialog.getSelectedFile()));
					} catch (IOException e1) {
						// TODO display an error message
					}
				}
			});
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
			removeButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object[] packages = getPackageList().getSelectedValues();
					for (int i = 0; i < packages.length; i++) {
						list.remove((Package) packages[i]);
					}
				}
			});
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
