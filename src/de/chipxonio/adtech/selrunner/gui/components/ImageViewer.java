package de.chipxonio.adtech.selrunner.gui.components;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImageViewer extends JPanel {
	private static final long serialVersionUID = 8584005392814040230L;
	private Image image;
	
	public ImageViewer(Image i) {
		this.image = i;
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(this.image, 0, 0, null);
	}
}
