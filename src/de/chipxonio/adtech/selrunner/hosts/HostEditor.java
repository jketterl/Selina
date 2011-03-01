package de.chipxonio.adtech.selrunner.hosts;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.Insets;
import java.awt.Dimension;

public class HostEditor extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private Host host;
	
	private JPanel jContentPane = null;
	private JLabel nameLabel = null;
	private JTextField nameTextField = null;
	private JLabel hostNameLabel = null;
	private JTextField hostNameTextField = null;
	private JLabel portLabel = null;
	private JTextField portTextField = null;

	private JPanel fieldPanel = null;

	private JPanel buttonPanel = null;

	private JButton okButton = null;

	private JButton cancelButon = null;

	/**
	 * @param owner
	 */
	public HostEditor(Frame owner, Host host) {
		super(owner);
		this.host = host;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(new Dimension(445, 145));
		this.setTitle("Selenium Host bearbeiten");
		this.setContentPane(getJContentPane());
		this.getRootPane().setDefaultButton(this.getOkButton());
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
			jContentPane.add(getFieldPanel(), BorderLayout.CENTER);
			jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes nameTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getNameTextField() {
		if (nameTextField == null) {
			nameTextField = new JTextField();
			nameTextField.setText(this.host.getName());
		}
		return nameTextField;
	}

	/**
	 * This method initializes hostNameTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getHostNameTextField() {
		if (hostNameTextField == null) {
			hostNameTextField = new JTextField();
			hostNameTextField.setText(this.host.getHostName());
		}
		return hostNameTextField;
	}

	/**
	 * This method initializes portTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getPortTextField() {
		if (portTextField == null) {
			portTextField = new JTextField();
			portTextField.setText(Integer.toString(this.host.getPort()));
		}
		return portTextField;
	}

	/**
	 * This method initializes fieldPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getFieldPanel() {
		if (fieldPanel == null) {
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.BOTH;
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.gridy = 2;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.gridwidth = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.gridy = 2;
			gridBagConstraints4.gridx = 0;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.gridwidth = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.gridx = 0;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints1.ipadx = 0;
			gridBagConstraints1.gridwidth = 2;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridx = 0;
			portLabel = new JLabel();
			portLabel.setText("Port:");
			hostNameLabel = new JLabel();
			hostNameLabel.setText("Rechnername / IP:");
			nameLabel = new JLabel();
			nameLabel.setText("Name:");
			fieldPanel = new JPanel();
			fieldPanel.setLayout(new GridBagLayout());
			fieldPanel.add(nameLabel, gridBagConstraints);
			fieldPanel.add(getNameTextField(), gridBagConstraints1);
			fieldPanel.add(hostNameLabel, gridBagConstraints2);
			fieldPanel.add(getHostNameTextField(), gridBagConstraints3);
			fieldPanel.add(portLabel, gridBagConstraints4);
			fieldPanel.add(getPortTextField(), gridBagConstraints5);
		}
		return fieldPanel;
	}

	/**
	 * This method initializes buttonPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout());
			buttonPanel.add(getOkButton(), null);
			buttonPanel.add(getCancelButon(), null);
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
					host.setName(nameTextField.getText());
					host.setHostName(hostNameTextField.getText());
					host.setPort(Integer.parseInt(portTextField.getText()));
					dispose();
				}
			});
		}
		return okButton;
	}

	/**
	 * This method initializes cancelButon	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancelButon() {
		if (cancelButon == null) {
			cancelButon = new JButton();
			cancelButon.setText("Abbrechen");
		}
		return cancelButon;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
