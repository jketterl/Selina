package de.chipxonio.adtech.selrunner.gui.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 8584005392814040230L;
	private Image image;
	
	public ImagePanel(Image i) {
		this.image = i;
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(this.image, 0, 0, null);
	}

	@Override
	public Dimension getPreferredSize() {
		Dimension d = super.getPreferredSize();
		d.setSize(this.image.getWidth(this), this.image.getHeight(this));
		return d;
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int w,
			int h) {
		this.setPreferredSize(new Dimension(w, h));
		return super.imageUpdate(img, infoflags, x, y, w, h);
	}
}
