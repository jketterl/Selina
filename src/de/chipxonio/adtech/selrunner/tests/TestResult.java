package de.chipxonio.adtech.selrunner.tests;

import java.lang.reflect.Method;
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
	private long startTime = 0, endTime = 0;
	private Class<?> currentClass;
	private Method currentMethod;
	
	public TestResult() {
		this.startTime = System.currentTimeMillis();
	}
	
	public String toString() {
		String result = "";
		if (this.startTime > 0 && this.endTime > 0) {
			result += "runtime: " + Math.round((this.endTime - this.startTime) / 1000) + "s; ";
		}
		result += "passed: " + this.passes + ", failed: " + this.failures;
		if (this.exceptions.size() > 0) {
			result += ", exceptions: " + exceptions.size();
			int screenshotCount = this.screenshots.size();
			if (screenshotCount > 0) result += ", screenshots: " + screenshotCount;
		}
		return result;
	}
	
	public boolean isSuccessful() {
		return this.failures == 0 && this.exceptions.size() == 0;
	}
	
	public void pushException(Exception e) {
		this.exceptions.add(e);
		if (e instanceof WebDriverException) try {
			this.screenshots.add(new Screenshot((WebDriverException) e));
		} catch (MissingScreenshotException e1) {
		}
		this.fireTestResultChanged();
	}
	
	public void pass() {
		this.passes++;
		this.fireTestResultChanged();
	}
	
	public void fail() {
		System.out.println(this.currentClass.getName() + "::" + this.currentMethod.getName());
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

	public void stopTimer() {
		this.endTime = System.currentTimeMillis();
	}
	
	public void setTestClass(Class<?> c) {
		this.currentClass = c;
	}
	
	public void setTestMethod(Method m) {
		this.currentMethod = m;
	}

	public Vector<Exception> getExceptions() {
		return exceptions;
	}
}
