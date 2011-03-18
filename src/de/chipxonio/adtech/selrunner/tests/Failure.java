package de.chipxonio.adtech.selrunner.tests;

import java.lang.reflect.Method;

public class Failure extends Outcome {
	protected String message;

	public Failure(Class<? extends TestCase> c, Method m) {
		super(c, m);
	}

	public Failure(Class<? extends TestCase> c, Method m, String message) {
		this(c, m);
		this.message = message;
	}
	
	public String toString() {
		return super.toString() + (message != null ? ": " + message : "");
	}
}
