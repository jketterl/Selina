package de.chipxonio.adtech.selrunner.gui.components;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import de.chipxonio.adtech.selrunner.gui.HostEditorGui;
import de.chipxonio.adtech.selrunner.hosts.Host;
import de.chipxonio.adtech.selrunner.hosts.HostList;
import java.awt.Insets;

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
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(2, 5, 2, 5);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.insets = new Insets(2, 5, 2, 5);
			gridBagConstraints.gridy = 0;
			buttonBanel = new JPanel();
			buttonBanel.setLayout(new GridBagLayout());
			buttonBanel.add(getAddHostButton(), gridBagConstraints2);
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
			hostList.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						(new HostEditorGui(null, (Host) getHostList().getSelectedValue())).run();
					}
				}
			});
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
			addHostButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Host host = (new HostEditorGui(null, new Host())).run();
					if (host != null) list.add(host);
				}
			});
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
			removeHostButton.setText("Remove Host(s)");
			removeHostButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Object[] hosts = getHostList().getSelectedValues();
					for (int i = 0; i < hosts.length; i++) {
						list.remove((Host) hosts[i]);
					}
				}
			});
		}
		return removeHostButton;
	}

}
