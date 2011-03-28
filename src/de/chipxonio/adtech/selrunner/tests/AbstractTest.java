package de.chipxonio.adtech.selrunner.tests;

import org.openqa.selenium.WebDriver;

abstract public class AbstractTest {
	private WebDriver driver;
	private TestResult result;
	
	public TestResult getResult() {
		return result;
	}

	public void setResult(TestResult result) {
		this.result = result;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	public static String getName() {
		return "Unnamed Test";
	}
	
	abstract public void run() throws Exception;
}
