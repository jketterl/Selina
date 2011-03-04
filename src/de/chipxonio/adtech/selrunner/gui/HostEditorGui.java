package de.chipxonio.adtech.selrunner.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import de.chipxonio.adtech.selrunner.hosts.Host;

public class HostEditorGui extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private Host host;
	
	private boolean confirmed = false;
	
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
	public HostEditorGui(Frame owner, Host host) {
		super(owner);
		if (host == null) {
			host = new Host();
		}
		this.host = host;
		initialize();
	}
	
	public Host run()
	{
		this.setVisible(true);
		if (!this.confirmed) return null;
		return host;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(new Dimension(445, 145));
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setModal(true);
		this.setTitle("Selenium Host bearbeiten");
		this.setContentPane(getJContentPane());
		this.getRootPane().setDefaultButton(this.getOkButton());
		this.getRootPane().registerKeyboardAction(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
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
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.anchor = GridBagConstraints.WEST;
			gridBagConstraints4.gridy = 2;
			gridBagConstraints4.ipadx = 10;
			gridBagConstraints4.gridx = 0;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 1.0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.anchor = GridBagConstraints.WEST;
			gridBagConstraints2.gridy = 1;
			gridBagConstraints2.ipady = 0;
			gridBagConstraints2.ipadx = 10;
			gridBagConstraints2.gridx = 0;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.ipadx = 10;
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
					confirmed = true;
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
			cancelButon.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					dispose();
				}
			});
		}
		return cancelButon;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
