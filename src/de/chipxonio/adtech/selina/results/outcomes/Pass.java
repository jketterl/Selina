package de.chipxonio.adtech.selina.results.outcomes;

import java.lang.reflect.Method;
import java.net.URL;

import de.chipxonio.adtech.selina.tests.TestCase;

public class Pass extends Outcome {
	public Pass(Class<? extends TestCase> c, Method m, URL url) {
		super(c, m, url);
	}
}
