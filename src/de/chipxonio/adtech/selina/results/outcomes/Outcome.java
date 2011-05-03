package de.chipxonio.adtech.selina.results.outcomes;

import java.lang.reflect.Method;
import java.net.URL;

import de.chipxonio.adtech.selina.tests.TestCase;

abstract public class Outcome {
	protected Class<?> clss;
	protected Method method;
	protected URL url;
	
	public Outcome(Class<? extends TestCase> c, Method m, URL url) {
		this.clss = c;
		this.method = m;
		this.url = url;
	}
	
	public String toString() {
		return clss.getName() + "::" + method.getName();
	}
	
	public URL getUrl() {
		return url;
	}
}
