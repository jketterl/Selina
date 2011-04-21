package de.chipxonio.adtech.selina.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import de.chipxonio.adtech.selina.gui.components.HostLibrary;
import de.chipxonio.adtech.selina.gui.components.PackageLibrary;
import de.chipxonio.adtech.selina.library.Library;

public class PreferencesGui extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel buttonPanel = null;
	private JPanel hostPanel = null;
	private PackageLibrary packageLibrary = null;
	private Library library;
	private JButton closeButton = null;

	/**
	 * @param owner
	 */
	public PreferencesGui(Frame owner, Library library) {
		super(owner);
		this.library = library;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(600, 400);
		this.setTitle("Selina Preferences");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setModal(true);
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
			jContentPane.add(getJTabbedPane(), BorderLayout.CENTER);
			jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("Package Library", null, getPackageLibrary(), null);
			jTabbedPane.addTab("Host Library", null, getHostPanel(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes buttonPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(java.awt.FlowLayout.RIGHT);
			flowLayout.setVgap(2);
			buttonPanel = new JPanel();
			buttonPanel.setLayout(flowLayout);
			buttonPanel.add(getCloseButton(), null);
		}
		return buttonPanel;
	}

	/**
	 * This method initializes hostPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getHostPanel() {
		if (hostPanel == null) {
			hostPanel = new HostLibrary(this.library.getHostList());
		}
		return hostPanel;
	}

	/**
	 * This method initializes packageLibrary	
	 * 	
	 * @return de.chipxonio.adtech.selina.gui.components.PackageLibrary	
	 */
	private PackageLibrary getPackageLibrary() {
		if (packageLibrary == null) {
			packageLibrary = new PackageLibrary(this.library.getPackageList());
		}
		return packageLibrary;
	}

	/**
	 * This method initializes closeButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCloseButton() {
		if (closeButton == null) {
			closeButton = new JButton();
			closeButton.setText("Close");
			closeButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return closeButton;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
