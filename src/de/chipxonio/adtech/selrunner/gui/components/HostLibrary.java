package de.chipxonio.adtech.selrunner.gui.components;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import de.chipxonio.adtech.selrunner.hosts.HostList;

public class HostLibrary extends JPanel {

	private static final long serialVersionUID = 1L;
	private HostList list;
	private JScrollPane jScrollPane = null;
	private JPanel buttonBanel = null;
	private JList hostList = null;
	private JButton addHostButton = null;
	private JButton removeHostButton = null;

	/**
	 * This is the default constructor
	 */
	public HostLibrary(HostList list) {
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
		this.add(getJScrollPane(), BorderLayout.CENTER);
		this.add(getButtonBanel(), BorderLayout.SOUTH);
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getHostList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes buttonBanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonBanel() {
		if (buttonBanel == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = 0;
			buttonBanel = new JPanel();
			buttonBanel.setLayout(new GridBagLayout());
			buttonBanel.add(getAddHostButton(), new GridBagConstraints());
			buttonBanel.add(getRemoveHostButton(), gridBagConstraints);
		}
		return buttonBanel;
	}

	/**
	 * This method initializes hostList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getHostList() {
		if (hostList == null) {
			hostList = new JList((ListModel) this.list);
		}
		return hostList;
	}

	/**
	 * This method initializes addHostButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAddHostButton() {
		if (addHostButton == null) {
			addHostButton = new JButton();
			addHostButton.setText("Add Host...");
		}
		return addHostButton;
	}

	/**
	 * This method initializes removeHostButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getRemoveHostButton() {
		if (removeHostButton == null) {
			removeHostButton = new JButton();
			removeHostButton.setText("Remove Host");
		}
		return removeHostButton;
	}

}
