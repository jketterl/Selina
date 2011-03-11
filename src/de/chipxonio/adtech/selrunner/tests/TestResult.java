package de.chipxonio.adtech.selrunner.tests;

import java.util.Iterator;
import java.util.Vector;

import org.openqa.selenium.WebDriverException;

import de.chipxonio.adtech.selrunner.screenshots.MissingScreenshotException;
import de.chipxonio.adtech.selrunner.screenshots.Screenshot;

public class TestResult {
	private int passes = 0;
	private int failures = 0;
	private Vector<Exception> exceptions = new Vector<Exception>();
	private Vector<TestResultListener> listeners = new Vector<TestResultListener>();
	private Vector<Screenshot> screenshots = new Vector<Screenshot>();
	
	public String toString() {
		String result = "passes: " + this.passes + ", failed: " + this.failures;
		if (this.exceptions.size() > 0) {
			result += ", exceptions: " + exceptions.size();
			int screenshotCount = this.screenshots.size();
			if (screenshotCount > 0) result += ", screenshots: " + screenshotCount;
		}
		return result;
	}
	
	public boolean isSuccessful() {
		return this.failures == 0;
	}
	
	public void pushException(Exception e) {
		this.exceptions.add(e);
		if (e instanceof WebDriverException) try {
			this.screenshots.add(new Screenshot((WebDriverException) e));
		} catch (MissingScreenshotException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.fireTestResultChanged();
	}
	
	public void pass() {
		this.passes++;
		this.fireTestResultChanged();
	}
	
	public void fail() {
		this.failures++;
		this.fireTestResultChanged();
	}
	
	public Vector<Screenshot> getScreenshots() {
		return screenshots;
	}

	public void addListener(TestResultListener l) {
		this.listeners.add(l);
	}
	
	public void removeListener(TestResultListener l) {
		this.listeners.remove(l);
	}
	
	private void fireTestResultChanged() {
		Iterator<TestResultListener> i = this.listeners.iterator();
		while (i.hasNext()) i.next().testResultChanged(this);
	}

	public void pushScreenshot(Screenshot screenshot) {
		this.screenshots.add(screenshot);
	}
}
