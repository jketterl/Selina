package de.chipxonio.adtech.selina.tests.outcomes;

import java.lang.reflect.Method;

import de.chipxonio.adtech.selina.tests.TestCase;

public class Pass extends Outcome {
	public Pass(Class<? extends TestCase> c, Method m) {
		super(c, m);
	}
}
