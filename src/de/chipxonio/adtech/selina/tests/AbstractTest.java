package de.chipxonio.adtech.selina.tests;

import org.openqa.selenium.WebDriver;

import de.chipxonio.adtech.selina.engine.SelinaTask;
import de.chipxonio.adtech.selina.results.TestResult;

abstract public class AbstractTest {
	private WebDriver driver;
	private TestResult result;
	protected SelinaTask task;
	
	public TestResult getOverallResult() {
		return result;
	}

	public void setOverallResult(TestResult result) {
		this.result = result;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public void setTask(SelinaTask task) {
		this.task = task;
	}
	
	abstract public String getTestName();
	
	abstract public void run();
}
