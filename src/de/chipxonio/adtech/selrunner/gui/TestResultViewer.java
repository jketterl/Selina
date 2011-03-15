package de.chipxonio.adtech.selrunner.gui;

import javax.swing.JPanel;
import java.awt.Frame;
import java.awt.BorderLayout;
import javax.swing.JDialog;

import de.chipxonio.adtech.selrunner.tests.TestResult;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

public class TestResultViewer extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private TestResult result;
	private JPanel contenPanel = null;
	private JLabel jLabel = null;
	private JList exceptionList = null;
	private JScrollPane jScrollPane = null;

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
			jContentPane.add(getContenPanel(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes contenPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getContenPanel() {
		if (contenPanel == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.anchor = GridBagConstraints.WEST;
			gridBagConstraints.gridy = 0;
			jLabel = new JLabel();
			jLabel.setText("Exceptions");
			contenPanel = new JPanel();
			contenPanel.setLayout(new GridBagLayout());
			contenPanel.add(jLabel, gridBagConstraints);
			contenPanel.add(getJScrollPane(), gridBagConstraints1);
		}
		return contenPanel;
	}

	/**
	 * This method initializes exceptionList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getExceptionList() {
		if (exceptionList == null) {
			exceptionList = new JList(this.result.getExceptions());
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
			jScrollPane.setViewportView(getExceptionList());
		}
		return jScrollPane;
	}

}
