package de.chipxonio.adtech.selina.engine;

import de.chipxonio.adtech.selina.results.TestResult;

public interface SelinaTaskListener {
	public static final int STOPPED = 0;
	public static final int RUNNING = 1;
	public static final int COMPLETE = 2;
	
	public void statusUpdated(SelinaTask source, int status);
	public void resultChanged(SelinaTask source, TestResult result);
	public void testingComplete(TestResult result);
}
