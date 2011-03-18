package de.chipxonio.adtech.selrunner.tests;

import java.util.Iterator;
import java.util.Vector;

import org.openqa.selenium.WebDriverException;

import de.chipxonio.adtech.selrunner.screenshots.MissingScreenshotException;
import de.chipxonio.adtech.selrunner.screenshots.Screenshot;
import de.chipxonio.adtech.selrunner.util.ActiveVector;

public class TestResult {
	private ActiveVector<Pass> passes = new ActiveVector<Pass>();;
	private ActiveVector<Failure> failures = new ActiveVector<Failure>();
	private ActiveVector<Exception> exceptions = new ActiveVector<Exception>();
	private Vector<TestResultListener> listeners = new Vector<TestResultListener>();
	private Vector<Screenshot> screenshots = new Vector<Screenshot>();
	private long startTime = 0, endTime = 0;
	
	public TestResult() {
		this.startTime = System.currentTimeMillis();
	}
	
	public String toString() {
		String result = "";
		if (this.startTime > 0 && this.endTime > 0) {
			result += "runtime: " + Math.round((this.endTime - this.startTime) / 1000) + "s; ";
		}
		result += "passed: " + this.passes.size() + ", failed: " + this.failures.size();
		if (this.exceptions.size() > 0) {
			result += ", exceptions: " + exceptions.size();
		}
		int screenshotCount = this.screenshots.size();
		if (screenshotCount > 0) result += ", screenshots: " + screenshotCount;
		return result;
	}
	
	public boolean isSuccessful() {
		return this.failures.isEmpty() && this.exceptions.isEmpty();
	}
	
	public void pushException(Exception e) {
		this.exceptions.add(e);
		if (e instanceof WebDriverException) try {
			this.screenshots.add(new Screenshot((WebDriverException) e));
		} catch (MissingScreenshotException e1) {
		}
		this.fireTestResultChanged();
	}
	
	public void pushPass(Pass p) {
		this.passes.add(p);
		this.fireTestResultChanged();
	}
	
	public void pushFailure(Failure f) {
		this.failures.add(f);
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

	public ActiveVector<Pass> getPasses() {
		return passes;
	}

	public ActiveVector<Failure> getFailures() {
		return failures;
	}
}
