package de.chipxonio.adtech.selrunner.engine;

import java.util.Iterator;
import java.util.Vector;

import org.openqa.selenium.WebDriver;

import de.chipxonio.adtech.selrunner.browsers.FirefoxBrowser;
import de.chipxonio.adtech.selrunner.hosts.Host;
import de.chipxonio.adtech.selrunner.tests.AbstractTest;
import de.chipxonio.adtech.selrunner.tests.TestResult;

public class SelRunnerTask extends Thread {
	private Host host;
	private Class<AbstractTest> test;
	private Vector<SelRunnerTaskListener> listeners = new Vector<SelRunnerTaskListener>();
	private WebDriver driver = null;
	
	public SelRunnerTask(Host host, Class<AbstractTest> test) {
		super();
		this.host = host;
		this.test = test;
	}
	
	public void addListener(SelRunnerTaskListener l) {
		this.listeners.add(l);
	}
	
	private void fireTestingComplete(TestResult result) {
		Iterator<SelRunnerTaskListener> i = this.listeners.iterator();
		while (i.hasNext()) i.next().testingComplete(result);
	}
	
	public void removeListener(SelRunnerTaskListener l) {
		this.listeners.remove(l);
	}
	
	public Host getHost() {
		return host;
	}
	public void setHost(Host host) {
		this.host = host;
	}
	public Class<AbstractTest> getTest() {
		return test;
	}
	public void setTest(Class<AbstractTest> test) {
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
		TestResult result = new TestResult();
		WebDriver driver = null;
		try {
			driver = this.getDriver();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (driver == null) return;
		try {
			AbstractTest test = this.getTest().newInstance();
			test.setResult(result);
			test.setDriver(driver);
			test.run();
		} catch (Exception e) {
			result.pushException(e);
		}
		this.fireTestingComplete(result);
		try {
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
