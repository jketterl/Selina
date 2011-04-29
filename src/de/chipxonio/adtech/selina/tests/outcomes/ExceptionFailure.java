package de.chipxonio.adtech.selina.tests.outcomes;

import java.lang.reflect.Method;

import de.chipxonio.adtech.selina.tests.TestCase;

public class ExceptionFailure extends Failure {
	public ExceptionFailure(Class<? extends TestCase> c, Method m, Exception e) {
		super(c, m, e.getMessage());
	}
}