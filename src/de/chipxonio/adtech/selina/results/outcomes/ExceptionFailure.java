package de.chipxonio.adtech.selina.results.outcomes;

import java.lang.reflect.Method;
import java.net.URL;

import de.chipxonio.adtech.selina.tests.TestCase;

public class ExceptionFailure extends Failure {
	public ExceptionFailure(Class<? extends TestCase> c, Method m, URL url, Exception e) {
		super(c, m, url, e.getClass().getName() + " with message \"" + e.getMessage() + "\"");
		this.stackTrace = e.getStackTrace();
	}
}
