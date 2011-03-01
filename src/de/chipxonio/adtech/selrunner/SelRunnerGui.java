package de.chipxonio.adtech.selrunner;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListModel;

import de.chipxonio.adtech.selrunner.hosts.Host;
import de.chipxonio.adtech.selrunner.hosts.HostList;

public class SelRunnerGui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4222699284599413079L;
	private JPanel jContentPane = null;
	private JButton startButton = null;
	private JList jList = null;

	/**
	 * This method initializes 
	 * 
	 */
	public SelRunnerGui() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(469, 244));
        this.setTitle("Selenium Runner");
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
			jContentPane.add(getStartButton(), BorderLayout.SOUTH);
			jContentPane.add(getJList(), BorderLayout.WEST);
		}
		return jContentPane;
	}

	/**
	 * This method initializes startButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getStartButton() {
		if (startButton == null) {
			startButton = new JButton();
			startButton.setText("Start Testing");
		}
		return startButton;
	}

	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private JList getJList() {
		HostList hostList = new HostList();
		hostList.add(new Host());
		if (jList == null) {
			jList = new JList((ListModel) hostList);
		}
		return jList;
	}
}  //  @jve:decl-index=0:visual-constraint="95,65"
