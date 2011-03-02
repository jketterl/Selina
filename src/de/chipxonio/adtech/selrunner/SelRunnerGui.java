package de.chipxonio.adtech.selrunner;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import de.chipxonio.adtech.selrunner.hosts.Host;
import de.chipxonio.adtech.selrunner.hosts.HostEditor;
import de.chipxonio.adtech.selrunner.hosts.HostList;

public class SelRunnerGui extends JFrame {

	private static final long serialVersionUID = -4222699284599413079L;
	private HostList hostList = null;
	private JPanel jContentPane = null;
	private JButton startButton = null;
	private JList jList = null;
	private JScrollPane jScrollPane = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
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
        this.setSize(new Dimension(619, 281));
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
			jContentPane.add(getJPanel(), BorderLayout.WEST);
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
		if (jList == null) {
			jList = new JList((ListModel) getHostList());
			jList.addMouseListener(new java.awt.event.MouseListener() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					if (e.getClickCount() == 2) {
						Host host = (Host) jList.getSelectedValue();
						new HostEditor(null, host).run();
					}
				}
				public void mousePressed(java.awt.event.MouseEvent e) {
				}
				public void mouseReleased(java.awt.event.MouseEvent e) {
				}
				public void mouseEntered(java.awt.event.MouseEvent e) {
				}
				public void mouseExited(java.awt.event.MouseEvent e) {
				}
			});
		}
		return jList;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getJList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BorderLayout());
			jPanel.add(getJScrollPane(), BorderLayout.CENTER);
			jPanel.add(getJPanel1(), BorderLayout.SOUTH);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setVgap(1);
			flowLayout.setHgap(8);
			jPanel1 = new JPanel();
			jPanel1.setLayout(flowLayout);
			jPanel1.add(getJButton(), null);
			jPanel1.add(getJButton1(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("+");
			jButton.setToolTipText("add another host");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Host host = (new HostEditor(null, null)).run();
					if (host != null) getHostList().add(host);
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("-");
			jButton1.setToolTipText("remove host(s)");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Host host = (Host)getJList().getSelectedValue();
					if (host != null) getHostList().remove(host);
				}
			});
		}
		return jButton1;
	}
	
	private HostList getHostList() {
		if (hostList == null) {
			hostList = new HostList();
		}
		return hostList;
	}
}  //  @jve:decl-index=0:visual-constraint="95,65"
