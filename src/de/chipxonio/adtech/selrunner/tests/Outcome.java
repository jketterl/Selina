package de.chipxonio.adtech.selrunner.tests;

import java.lang.reflect.Method;

public class Outcome {
	protected Class<?> clss;
	protected Method method;
	protected String message;
	
	public Outcome(Class<? extends TestCase> c, Method m) {
		this.clss = c;
		this.method = m;
	}
	
	public Outcome(Class<? extends TestCase> c, Method m, String message) {
		this(c, m);
		this.message = message;
	}
	
	public String toString() {
		return clss.getName() + "::" + method.getName() + (message != null ? ": " + message : "");
	}
}
