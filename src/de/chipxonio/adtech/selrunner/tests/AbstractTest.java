package de.chipxonio.adtech.selrunner.tests;

import org.openqa.selenium.WebDriver;

abstract public class AbstractTest {
	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	private WebDriver driver;
	
	abstract public TestResult run();
}
