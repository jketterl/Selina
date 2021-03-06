package de.chipxonio.adtech.selina.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import de.chipxonio.adtech.selina.gui.components.FailureDetailPanel;
import de.chipxonio.adtech.selina.gui.components.treetable.JTreeTable;
import de.chipxonio.adtech.selina.results.TestCaseResult;
import de.chipxonio.adtech.selina.results.TestResult;
import de.chipxonio.adtech.selina.results.outcomes.Failure;
import de.chipxonio.adtech.selina.results.outcomes.Outcome;
import de.chipxonio.adtech.selina.results.outcomes.Pass;
import javax.swing.JSplitPane;

public class TestResultViewer extends JDialog {
	
	private static final Color successColor = new Color(200, 255, 200);  //  @jve:decl-index=0:
	private static final Color failureColor = new Color(255, 200, 200);

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private TestResult result;
	private JScrollPane jScrollPane1 = null;  //  @jve:decl-index=0:visual-constraint="220,237"
	private JPanel buttonPanel = null;
	private JButton closeButton = null;
	private JTreeTable resultTable = null;
	private FailureDetailPanel failureDetailPanel = null;
	private JSplitPane contentPanel = null;
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
	private JTreeTable getResultTable() {
		if (resultTable == null) {
			resultTable = new JTreeTable(this.result);
			resultTable.getColumnModel().getColumn(0).setPreferredWidth(300);
			resultTable.setDefaultRenderer(TestCaseResult.class, new DefaultTableCellRenderer(){
				private static final long serialVersionUID = 8619907859395215841L;

				@Override
				public Component getTableCellRendererComponent(JTable table,
						Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
							row, column);
					if (value instanceof TestCaseResult) {
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
					} else if (value instanceof Outcome) {
						if (!isSelected) {
							if (value instanceof Pass) {
								l.setBackground(successColor);
							} else {
								l.setBackground(failureColor);
							}
						}
						l.setText("");
					}
					return l;
				}
			});
			resultTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					if (e.getValueIsAdjusting()) return;
					Object row = resultTable.getValueAt(e.getFirstIndex(), 1);
					if (!(row instanceof Failure)) {
						getFailureDetailPanel().setFailure(null);
					} else {
						Failure f = (Failure) row;
						getFailureDetailPanel().setFailure(f);
					}
				}
			});
		}
		return resultTable;
	}

	/**
	 * This method initializes failureDetailPanel	
	 * 	
	 * @return de.chipxonio.adtech.selina.gui.components.FailureDetailPanel	
	 */
	private FailureDetailPanel getFailureDetailPanel() {
		if (failureDetailPanel == null) {
			failureDetailPanel = new FailureDetailPanel();
		}
		return failureDetailPanel;
	}

	/**
	 * This method initializes contentPanel	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getContentPanel() {
		if (contentPanel == null) {
			contentPanel = new JSplitPane();
			contentPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
			contentPanel.setDividerLocation(400);
			contentPanel.setLeftComponent(getJScrollPane1());
			contentPanel.setRightComponent(getFailureDetailPanel());
		}
		return contentPanel;
	}

}
