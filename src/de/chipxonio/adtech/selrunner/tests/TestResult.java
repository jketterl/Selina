package de.chipxonio.adtech.selrunner.tests;

import java.util.Vector;

public class TestResult {
	private int passes = 0;
	private int failures = 0;
	private Vector<Exception> exceptions = new Vector<Exception>();
	
	public String toString() {
		String result = "successful: " + this.passes + ", failed: " + this.failures;
		if (this.exceptions.size() > 0) result += ", exceptions: " + exceptions.size();
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
}