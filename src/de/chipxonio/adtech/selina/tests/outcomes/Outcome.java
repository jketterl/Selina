package de.chipxonio.adtech.selina.tests.outcomes;

import java.lang.reflect.Method;

import de.chipxonio.adtech.selina.tests.TestCase;

abstract public class Outcome {
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
