package de.chipxonio.adtech.selrunner.gui;

import java.awt.Frame;
import java.awt.Image;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import de.chipxonio.adtech.selrunner.gui.components.ImagePanel;

public class ScreenshotViewer extends JDialog {

	private static final long serialVersionUID = 1L;
	private Image image;
	private ImagePanel imageViewer = null;
	private JScrollPane jScrollPane = null;
	/**
	 * @param owner
	 */
	public ScreenshotViewer(Frame owner, Image image) {
		super(owner);
		this.image = image;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setTitle("Selenium Screenshot");
		this.setContentPane(getJScrollPane());
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.pack();
	}

	/**
	 * This method initializes imageViewer	
	 * 	
	 * @return de.chipxonio.adtech.selrunner.gui.components.ImageViewer	
	 */
	private ImagePanel getImageViewer() {
		if (imageViewer == null) {
			imageViewer = new ImagePanel(this.image);
		}
		return imageViewer;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getImageViewer());
		}
		return jScrollPane;
	}

}
