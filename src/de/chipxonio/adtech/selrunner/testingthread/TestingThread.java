package de.chipxonio.adtech.selrunner.testingthread;

import org.openqa.selenium.WebDriver;

import de.chipxonio.adtech.selrunner.browsers.FirefoxBrowser;
import de.chipxonio.adtech.selrunner.hosts.Host;
import de.chipxonio.adtech.selrunner.tests.AbstractTest;

public class TestingThread extends Thread {
	private Host host;
	private AbstractTest test;
	private WebDriver driver = null;
	
	public TestingThread(Host host, AbstractTest test)
	{
		super();
		this.host = host;
		this.test = test;
	}
	
	public WebDriver getDriver() {
		if (this.driver == null) {
			this.driver = new FirefoxBrowser().getDriver(host);
		}
		return this.driver;
	}

	public void run()
	{
		System.out.println("Starting thread execution");
		this.test.setDriver(this.getDriver());
		this.test.run();
		System.out.println("Thread finished");
		getDriver().quit();
	}
}
