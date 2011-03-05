package de.chipxonio.adtech.selrunner.tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

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
				Exception e = i.next();
				if (e instanceof WebDriverException && hasScreenshot((WebDriverException)e)) screenshots++;
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
	
	protected boolean hasScreenshot(WebDriverException e) {
		Throwable cause = e.getCause();
		return cause instanceof ScreenshotException;
	}

	protected void extractScreenshot(WebDriverException e) {
		if (!this.hasScreenshot(e)) return;
		ScreenshotException cause = (ScreenshotException) e.getCause();
		try {
			File f = new File("/home/jketterl/screenshot.png");
			if (f.exists()) f.delete();
			FileOutputStream file = new FileOutputStream(f);
			file.write(Base64.decodeBase64((cause).getBase64EncodedScreenshot()));
			file.close();
			System.out.println("screenshot has been written");
		} catch (IOException e1) {
			e1.printStackTrace();
			System.out.println("failed writing screenshot");
		}
	}
}
