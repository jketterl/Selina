package de.chipxonio.adtech.selina.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import de.chipxonio.adtech.selina.gui.components.treetable.JTreeTable;
import de.chipxonio.adtech.selina.tests.TestResult;

public class TestResultViewer extends JDialog {
	
	private static final Color successColor = new Color(200, 255, 200);
	private static final Color failureColor = new Color(255, 200, 200);

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private TestResult result;
	private JPanel contentPanel = null;
	private JList exceptionList = null;
	private JScrollPane jScrollPane = null;
	private JScrollPane jScrollPane1 = null;  //  @jve:decl-index=0:visual-constraint="220,237"
	private JPanel buttonPanel = null;
	private JButton closeButton = null;
	private JTreeTable resultTable = null;

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
		this.setSize(600, 700);
		this.setTitle("Test Result Viewer");
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
			gc.fill = GridBagConstraints.BOTH;
			gc.gridy = 0;
			gc.weightx = 1.0;
			gc.weighty = 3.0;
			gc.gridx = 0;
			contentPanel.add(getJScrollPane1(), gc);
			gc = new GridBagConstraints();
			gc.fill = GridBagConstraints.BOTH;
			gc.gridy = 1;
			gc.weightx = 1.0;
			gc.weighty = 1.0;
			gc.gridx = 0;
			contentPanel.add(getJScrollPane(), gc);
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
			jScrollPane.setBorder(BorderFactory.createTitledBorder(null, "Exceptions", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
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
			jScrollPane1.setBorder(BorderFactory.createTitledBorder(null, "Test Results", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
			jScrollPane1.setViewportView(getResultTable());
		}
		return jScrollPane1;
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

	/**
	 * This method initializes resultTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getResultTable() {
		if (resultTable == null) {
			resultTable = new JTreeTable(this.result);
			resultTable.getColumnModel().getColumn(0).setPreferredWidth(300);
			/*
			resultTable.setDefaultRenderer(TestCaseResult.class, new DefaultTableCellRenderer(){
				private static final long serialVersionUID = 8619907859395215841L;

				@Override
				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
							row, column);
					TestCaseResult r = (TestCaseResult) value;
					switch (column) {
					case 0:
						l.setText(r.toString());
						break;
					case 1: 
						l.setText("");
						break;
					case 2: 
						l.setText(Integer.toString(r.getPassCount()));
						break;
					case 3: 
						l.setText(Integer.toString(r.getFailCount()));
						break;
					}
					if (!isSelected) {
						if (r.isSuccessful()) {
							l.setBackground(successColor);
						} else {
							l.setBackground(failureColor);
						}
					}
					return l;
				}
			});
			*/
		}
		return resultTable;
	}

}
