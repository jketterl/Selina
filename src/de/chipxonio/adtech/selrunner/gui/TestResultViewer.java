package de.chipxonio.adtech.selrunner.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.WindowConstants;

import de.chipxonio.adtech.selrunner.tests.TestResult;
import javax.swing.JButton;

public class TestResultViewer extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private TestResult result;
	private JPanel contentPanel = null;
	private JLabel jLabel = null;
	private JList exceptionList = null;
	private JScrollPane jScrollPane = null;
	private JLabel jLabel1 = null;
	private JScrollPane jScrollPane1 = null;  //  @jve:decl-index=0:visual-constraint="220,237"
	private JList failureList = null;  //  @jve:decl-index=0:visual-constraint="473,280"
	private JPanel buttonPanel = null;
	private JButton closeButton = null;

	/**
	 * @param owner
	 */
	public TestResultViewer(Frame owner, TestResult result) {
		super(owner);
		this.result = result;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(600, 200);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
			jContentPane.add(getContentPanel(), BorderLayout.CENTER);
			jContentPane.add(getButtonPanel(), BorderLayout.SOUTH);
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
			contentPanel = new JPanel();
			contentPanel.setLayout(new GridBagLayout());
			GridBagConstraints gc;
			gc = new GridBagConstraints();
			gc.gridx = 0;
			gc.anchor = GridBagConstraints.WEST;
			gc.gridy = 0;
			jLabel = new JLabel();
			jLabel.setText("Exceptions");
			contentPanel.add(jLabel, gc);
			gc = new GridBagConstraints();
			gc.fill = GridBagConstraints.BOTH;
			gc.gridy = 1;
			gc.weightx = 1.0;
			gc.weighty = 1.0;
			gc.gridx = 0;
			contentPanel.add(getJScrollPane(), gc);
			gc = new GridBagConstraints();
			gc.gridx = 0;
			gc.anchor = GridBagConstraints.WEST;
			gc.gridy = 2;
			jLabel1 = new JLabel();
			jLabel1.setText("Failures");
			contentPanel.add(jLabel1, gc);
			gc = new GridBagConstraints();
			gc.fill = GridBagConstraints.BOTH;
			gc.gridy = 3;
			gc.weightx = 1.0;
			gc.weighty = 1.0;
			gc.gridx = 0;
			contentPanel.add(getJScrollPane1(), gc);
		}
		return contentPanel;
	}

	/**
	 * This method initializes exceptionList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getExceptionList() {
		if (exceptionList == null) {
			exceptionList = new JList((ListModel) this.result.getExceptions());
		}
		return exceptionList;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getExceptionList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getFailureList());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes failureList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getFailureList() {
		if (failureList == null) {
			failureList = new JList((ListModel) this.result.getFailures());
		}
		return failureList;
	}

	/**
	 * This method initializes buttonPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			GridBagConstraints gc = new GridBagConstraints();
			gc.insets = new Insets(2, 5, 2, 5);
			gc.weightx = 1.0;
			gc.anchor = GridBagConstraints.EAST;
			buttonPanel.add(getCloseButton(), gc);
		}
		return buttonPanel;
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

}
