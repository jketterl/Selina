package de.chipxonio.adtech.selrunner.tests;

import java.util.Iterator;
import java.util.Vector;

import org.openqa.selenium.WebDriverException;

import de.chipxonio.adtech.selrunner.screenshots.MissingScreenshotException;
import de.chipxonio.adtech.selrunner.screenshots.Screenshot;
import de.chipxonio.adtech.selrunner.util.ActiveVector;

public class TestResult {
	private ActiveVector<Exception> exceptions = new ActiveVector<Exception>();
	private Vector<TestResultListener> listeners = new Vector<TestResultListener>();
	private Vector<Screenshot> screenshots = new Vector<Screenshot>();
	private Vector<TestCaseResult> results = new Vector<TestCaseResult>();
	private long startTime = 0, endTime = 0;
	
	public TestResult() {
		this.startTime = System.currentTimeMillis();
	}
	
	public String toString() {
		String result = "";
		if (this.startTime > 0 && this.endTime > 0) {
			result += "runtime: " + Math.round((this.endTime - this.startTime) / 1000) + "s; ";
		}
		// count passes & failures
		Iterator <TestCaseResult> i = this.results.iterator();
		int passes = 0; int failures = 0;
		while (i.hasNext()) {
			TestCaseResult r = i.next();
			passes += r.getPassCount();
			failures += r.getFailCount();
		}
		result += "passed: " + passes + ", failed: " + failures;
		if (this.exceptions.size() > 0) {
			result += ", exceptions: " + exceptions.size();
		}
		int screenshotCount = this.screenshots.size();
		if (screenshotCount > 0) result += ", screenshots: " + screenshotCount;
		return result;
	}
	
	public boolean isSuccessful() {
		Iterator <TestCaseResult> i = this.results.iterator();
		boolean successful = true;
		while (i.hasNext()) successful &= i.next().isSuccessful();
		return successful && this.exceptions.isEmpty();
	}
	
	public void pushException(Exception e) {
		// e.printStackTrace();
		this.exceptions.add(e);
		if (e instanceof WebDriverException) try {
			this.screenshots.add(new Screenshot((WebDriverException) e));
		} catch (MissingScreenshotException e1) {
		}
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
	
	public ActiveVector<Exception> getExceptions() {
		return exceptions;
	}
	
	public void pushCaseResult(TestCaseResult r) {
		this.results.add(r);
	}
}
