package de.chipxonio.adtech.selina.results.outcomes;

import java.lang.reflect.Method;
import java.net.URL;

import de.chipxonio.adtech.selina.tests.TestCase;

public class Failure extends Outcome {
	protected String message;
	protected StackTraceElement[] stackTrace;

	/**
	 * @deprecated failing without a message is discouraged.
	 * @see Failure(Class<?extends TestCase> c, Method m, String message) instead.
	 */
	public Failure(Class<? extends TestCase> c, Method m, URL url) {
		super(c, m, url);
		this.stackTrace = this.gatherStackInformation();
	}

	public Failure(Class<? extends TestCase> c, Method m, URL url, String message) {
		super(c, m, url);
		this.message = message;
		this.stackTrace = this.gatherStackInformation();
	}
	
	protected StackTraceElement[] gatherStackInformation() {
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		StackTraceElement[] result = new StackTraceElement[stack.length - 4];
		for (int i = 0; i < stack.length - 4; i++) {
			result[i] = stack[i + 4];
		}
		return result;
	}
	
	public String toString() {
		return super.toString() + (message != null ? ": " + message : "");
	}
	
	public StackTraceElement[] getStackTrace() {
		return this.stackTrace;
	}
	
	public String getMessage() {
		return this.message;
	}
}
