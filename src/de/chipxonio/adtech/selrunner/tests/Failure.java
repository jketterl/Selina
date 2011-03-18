package de.chipxonio.adtech.selrunner.tests;

import java.lang.reflect.Method;

public class Failure extends Outcome {
	public Failure(Class<? extends TestCase> c, Method m) {
		super(c, m);
	}

	public Failure(Class<? extends TestCase> c, Method m, String message) {
		super(c, m, message);
	}
}
