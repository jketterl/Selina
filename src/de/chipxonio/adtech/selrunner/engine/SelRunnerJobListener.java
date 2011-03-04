package de.chipxonio.adtech.selrunner.engine;

import de.chipxonio.adtech.selrunner.tests.TestResult;

public interface SelRunnerJobListener {
	public void testingComplete(TestResult result);
}
