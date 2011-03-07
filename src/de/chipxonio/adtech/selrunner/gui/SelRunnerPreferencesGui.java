package de.chipxonio.adtech.selrunner.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import de.chipxonio.adtech.selrunner.gui.components.HostLibrary;
import de.chipxonio.adtech.selrunner.gui.components.PackageLibrary;
import de.chipxonio.adtech.selrunner.library.Library;
import de.chipxonio.adtech.selrunner.packages.PackageList;

public class SelRunnerPreferencesGui extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel buttonPanel = null;
	private JButton okButton = null;
	private JButton canelButton = null;
	private JPanel hostPanel = null;
	private PackageLibrary packageLibrary = null;
	private Library library;

	/**
	 * @param owner
	 */
	public SelRunnerPreferencesGui(Frame owner, Library library) {
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
		this.setSize(331, 267);
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
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.insets = new Insets(2, 5, 2, 5);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.insets = new Insets(2, 5, 2, 5);
			gridBagConstraints.gridy = 0;
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			buttonPanel.add(getOkButton(), gridBagConstraints4);
			buttonPanel.add(getCanelButton(), gridBagConstraints);
		}
		return buttonPanel;
	}

	/**
	 * This method initializes okButton	
	 * 	
	 * @return javax.swing.JToggleButton	
	 */
	private JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton();
			okButton.setText("OK");
		}
		return okButton;
	}

	/**
	 * This method initializes canelButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCanelButton() {
		if (canelButton == null) {
			canelButton = new JButton();
			canelButton.setText("Cancel");
		}
		return canelButton;
	}

	/**
	 * This method initializes hostPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getHostPanel() {
		if (hostPanel == null) {
			hostPanel = new HostLibrary(this.library.getHosts());
		}
		return hostPanel;
	}

	/**
	 * This method initializes packageLibrary	
	 * 	
	 * @return de.chipxonio.adtech.selrunner.gui.components.PackageLibrary	
	 */
	private PackageLibrary getPackageLibrary() {
		if (packageLibrary == null) {
			packageLibrary = new PackageLibrary(new PackageList());
		}
		return packageLibrary;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
