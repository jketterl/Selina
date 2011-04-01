package de.chipxonio.adtech.selrunner.tests;

import org.openqa.selenium.WebDriver;

abstract public class AbstractTest {
	private WebDriver driver;
	private TestResult result;
	
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
	
	abstract public String getTestName();
	
	abstract public void run() throws Exception;
}
