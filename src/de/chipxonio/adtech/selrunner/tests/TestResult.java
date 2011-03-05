package de.chipxonio.adtech.selrunner.tests;

import java.awt.Image;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.ScreenshotException;

public class TestResult {
	private int passes = 0;
	private int failures = 0;
	private Vector<Exception> exceptions = new Vector<Exception>();
	
	public String toString() {
		String result = "successful: " + this.passes + ", failed: " + this.failures;
		if (this.exceptions.size() > 0) {
			result += ", exceptions: " + exceptions.size();
			Iterator<Exception> i = this.exceptions.iterator();
			int screenshots = 0;
			while (i.hasNext()) {
				if (hasScreenshot(i.next())) screenshots++;
			}
			if (screenshots > 0) result += ", screenshots: " + screenshots;
		}
		return result;
	}
	
	public boolean isSuccessful() {
		return this.failures == 0;
	}
	
	public void pushException(Exception e) {
		this.exceptions.add(e);
	}
	
	public void pass() {
		this.passes++;
	}
	
	public void fail() {
		this.failures++;
	}
	
	protected boolean hasScreenshot(Exception e) {
		if (!(e instanceof WebDriverException)) return false;
		Throwable cause = e.getCause();
		return cause instanceof ScreenshotException;
	}
	
	public Vector<Image> getScreenshots() {
		Vector<Image> screenshots = new Vector<Image>();
		Iterator<Exception> i = this.exceptions.iterator();
		while (i.hasNext()) {
			Exception e = i.next();
			if (hasScreenshot(e)) screenshots.add(extractScreenshot((WebDriverException) e));
		}
		return screenshots;
	}

	protected Image extractScreenshot(WebDriverException e) {
		if (!this.hasScreenshot(e)) return null;
		ScreenshotException cause = (ScreenshotException) e.getCause();
		return (new ImageIcon(Base64.decodeBase64(cause.getBase64EncodedScreenshot()))).getImage();
	}
}
