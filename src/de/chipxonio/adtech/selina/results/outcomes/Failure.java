package de.chipxonio.adtech.selina.results.outcomes;

import java.lang.reflect.Method;

import de.chipxonio.adtech.selina.tests.TestCase;

public class Failure extends Outcome {
	protected String message;
	protected StackTraceElement[] stackTrace;

	/**
	 * @deprecated failing without a message is discouraged.
	 * @see Failure(Class<?extends TestCase> c, Method m, String message) instead.
	 */
	public Failure(Class<? extends TestCase> c, Method m) {
		super(c, m);
		this.stackTrace = Thread.currentThread().getStackTrace();
	}

	public Failure(Class<? extends TestCase> c, Method m, String message) {
		super(c, m);
		this.message = message;
		this.stackTrace = Thread.currentThread().getStackTrace();
	}
	
	public String toString() {
		return super.toString() + (message != null ? ": " + message : "");
	}
	
	public StackTraceElement[] getStackTrace() {
		return this.stackTrace;
	}
}
