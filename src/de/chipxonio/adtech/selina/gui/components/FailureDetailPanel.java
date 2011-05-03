package de.chipxonio.adtech.selina.gui.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
	private JLabel jLabel = null;
	private JTextField urlTextField = null;
	private JLabel jLabel1 = null;
	private JTextField messageTextField = null;
	private JScrollPane jScrollPane = null;
	private Failure failure;
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
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createTitledBorder(null, "Failure Details", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));

		GridBagConstraints gc1 = new GridBagConstraints();
		jLabel = new JLabel();
		jLabel.setText("Browser URL:");
		gc1.gridx = 0; gc1.gridy = 0;
		gc1.anchor = GridBagConstraints.WEST;
		this.add(jLabel, gc1);

		GridBagConstraints gc2 = new GridBagConstraints();
		gc2.gridx = 1; gc2.gridy = 0;
		gc2.fill = GridBagConstraints.HORIZONTAL;
		this.add(getUrlTextField(), gc2);

		jLabel1 = new JLabel();
		jLabel1.setText("Message:");
		GridBagConstraints gc3 = new GridBagConstraints();
		gc3.gridx = 0; gc3.gridy = 1;
		gc3.anchor = GridBagConstraints.WEST;
		this.add(jLabel1, gc3);
		
		GridBagConstraints gc4 = new GridBagConstraints();
		gc4.gridx = 1; gc4.gridy = 1;
		gc4.fill = GridBagConstraints.HORIZONTAL;
		this.add(getMessageTextField(), gc4);
		
		GridBagConstraints gc5 = new GridBagConstraints();
		gc5.gridx = 0; gc5.gridy = 2;
		gc5.gridwidth = GridBagConstraints.REMAINDER;
		gc5.fill = GridBagConstraints.BOTH;
		this.add(getJScrollPane(), gc5);
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
