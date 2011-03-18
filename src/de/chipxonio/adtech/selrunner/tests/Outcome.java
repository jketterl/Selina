package de.chipxonio.adtech.selrunner.tests;

import java.lang.reflect.Method;

public class Outcome {
	protected Class<?> clss;
	protected Method method;
	
	public Outcome(Class<? extends TestCase> c, Method m) {
		this.clss = c;
		this.method = m;
	}
	
	public String toString() {
		return clss.getName() + "::" + method.getName();
	}
}
