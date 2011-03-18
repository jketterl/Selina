package de.chipxonio.adtech.selrunner.tests;

import java.lang.reflect.Method;

public class Pass extends Outcome {
	public Pass(Class<? extends TestCase> c, Method m) {
		super(c, m);
	}

	public Pass(Class<? extends TestCase> c, Method m, String message) {
		super(c, m, message);
	}
}
