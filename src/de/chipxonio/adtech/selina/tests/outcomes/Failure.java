package de.chipxonio.adtech.selina.tests.outcomes;

import java.lang.reflect.Method;

import de.chipxonio.adtech.selina.tests.TestCase;

public class Failure extends Outcome {
	protected String message;

	/**
	 * @deprecated failing without a message is discouraged.
	 * @see Failure(Class<?extends TestCase> c, Method m, String message) instead.
	 */
	public Failure(Class<? extends TestCase> c, Method m) {
		super(c, m);
	}

	public Failure(Class<? extends TestCase> c, Method m, String message) {
		super(c, m);
		this.message = message;
	}
	
	public String toString() {
		return super.toString() + (message != null ? ": " + message : "");
	}
}
