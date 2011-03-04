package de.chipxonio.adtech.selrunner.gui;

import javax.swing.JPanel;
import java.awt.Frame;
import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;

import de.chipxonio.adtech.selrunner.gui.components.HostLibrary;
import de.chipxonio.adtech.selrunner.hosts.HostList;
import javax.swing.WindowConstants;

public class SelRunnerPreferencesGui extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel buttonPanel = null;
	private JButton okButton = null;
	private JButton canelButton = null;
	private JPanel testLibraryPanel = null;
	private JPanel hostPanel = null;

	/**
	 * @param owner
	 */
	public SelRunnerPreferencesGui(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
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
			jTabbedPane.addTab("Package Library", null, getTestLibraryPanel(), null);
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
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = 0;
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			buttonPanel.add(getOkButton(), new GridBagConstraints());
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
	 * This method initializes testLibraryPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTestLibraryPanel() {
		if (testLibraryPanel == null) {
			testLibraryPanel = new JPanel();
			testLibraryPanel.setLayout(new GridBagLayout());
		}
		return testLibraryPanel;
	}

	/**
	 * This method initializes hostPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getHostPanel() {
		if (hostPanel == null) {
			hostPanel = new HostLibrary(new HostList());
		}
		return hostPanel;
	}

}
