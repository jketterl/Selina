package de.chipxonio.adtech.selrunner.tests;

public class TestResult {
	private int successes = 0;
	private int failures = 0;
	
	public String toString() {
		return "successful: " + this.successes + ", failed: " + this.failures;
	}
	
	public boolean isSuccessful() {
		return this.failures == 0;
	}
}
