package de.chipxonio.adtech.selrunner.engine;

import de.chipxonio.adtech.selrunner.tests.TestResult;

public interface SelRunnerTaskListener {
	public static final int STOPPED = 0;
	public static final int RUNNING = 1;
	public static final int COMPLETE = 2;
	
	public void statusUpdated(SelRunnerTask source, int status);
	public void testingComplete(TestResult result);
}
