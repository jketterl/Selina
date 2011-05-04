package de.chipxonio.adtech.selina.gui.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import de.chipxonio.adtech.selina.results.outcomes.Failure;

public class FailureDetailPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField urlTextField = null;
	private JTextField messageTextField = null;
	private JScrollPane jScrollPane = null;
	private Failure failure;  //  @jve:decl-index=0:
	private JList stackTraceList = null;

	/**
	 * This is the default constructor
	 */
	public FailureDetailPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setBorder(BorderFactory.createTitledBorder(null, "Failure Details", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
		this.setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx = 0; gc.weighty = 0;
		
		// add labels
		gc.insets = new Insets(2, 5, 2, 5);
		gc.anchor = GridBagConstraints.WEST;
		
		JLabel label;
		label = new JLabel("Browser URL:");
		gc.gridx = 0; gc.gridy = 0;
		this.add(label, gc);

		label = new JLabel("Message:");
		gc.gridx = 0; gc.gridy = 1;
		this.add(label, gc);
		
		label = new JLabel("Stack Trace:");
		gc.gridx = 0; gc.gridy = 2;
		gc.gridwidth = 2;
		this.add(label, gc);
		
		// add text fields
		gc.weightx = 1; gc.gridwidth = 1;
		
		gc.gridx = 1; gc.gridy = 0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		this.add(getUrlTextField(), gc);

		gc.gridx = 1; gc.gridy = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		this.add(getMessageTextField(), gc);
		
		// add list box
		gc.weighty = 1;

		gc.gridx = 0; gc.gridy = 3;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.BOTH;
		this.add(getJScrollPane(), gc);
	}

	/**
	 * This method initializes urlTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getUrlTextField() {
		if (urlTextField == null) {
			urlTextField = new JTextField();
			urlTextField.setEditable(false);
		}
		return urlTextField;
	}

	/**
	 * This method initializes messageTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getMessageTextField() {
		if (messageTextField == null) {
			messageTextField = new JTextField();
			messageTextField.setEditable(false);
		}
		return messageTextField;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getStackTraceList());
		}
		return jScrollPane;
	}

	public void setFailure(Failure f) {
		this.failure = f;
		URL url = this.failure.getUrl();
		getUrlTextField().setText(url != null ? url.toString() : "");
		getMessageTextField().setText(this.failure.getMessage());
		getStackTraceList().setListData(this.failure.getStackTrace());
	}

	/**
	 * This method initializes stackTraceList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getStackTraceList() {
		if (stackTraceList == null) {
			stackTraceList = new JList();
		}
		return stackTraceList;
	}
}


