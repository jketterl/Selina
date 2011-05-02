package de.chipxonio.adtech.selina.engine;

import de.chipxonio.adtech.selina.results.TestResult;

public interface SelinaListener {
	public void testingComplete(TestResult result);
}
