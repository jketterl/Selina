package de.chipxonio.adtech.selrunner.screenshots;

import java.awt.Image;

import javax.swing.ImageIcon;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.ScreenshotException;

public class Screenshot {
	private Image image;
	
	public Screenshot(byte[] data) {
		this.setImage((new ImageIcon(data)).getImage());
	}
	
	public Screenshot(WebDriverException e) throws MissingScreenshotException {
		if (!(e.getCause() instanceof ScreenshotException))
			throw new MissingScreenshotException();
		ScreenshotException cause = (ScreenshotException) e.getCause();
		this.setImage((new ImageIcon(Base64.decodeBase64(cause.getBase64EncodedScreenshot()))).getImage());
	}

	public Image getImage() {
		return image;
	}
	
	private void setImage(Image image) {
		this.image = image;
	}
}
