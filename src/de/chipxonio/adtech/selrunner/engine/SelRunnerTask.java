package de.chipxonio.adtech.selrunner.engine;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

import org.openqa.selenium.WebDriver;

import de.chipxonio.adtech.selrunner.browsers.FirefoxBrowser;
import de.chipxonio.adtech.selrunner.hosts.Host;
import de.chipxonio.adtech.selrunner.packages.TestDefinition;
import de.chipxonio.adtech.selrunner.tests.AbstractTest;
import de.chipxonio.adtech.selrunner.tests.TestResult;

public class SelRunnerTask extends Thread implements Serializable {
	private static final long serialVersionUID = 7731026883005748237L;
	private Host host;
	private TestDefinition test;
	private Vector<SelRunnerTaskListener> listeners = new Vector<SelRunnerTaskListener>();
	private WebDriver driver = null;
	
	public SelRunnerTask() {
		super();
	};
	
	public SelRunnerTask(Host host, TestDefinition test) {
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
	public TestDefinition getTest() {
		return test;
	}
	public void setTest(TestDefinition test) {
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
			AbstractTest test = this.getTest().getInstance();
			test.setResult(result);
			test.setDriver(driver);
			test.run();
		} catch (Exception e) {
			result.pushException(e);
		}
		this.fireTestingComplete(result);
		try {
			driver.quit();
			this.driver = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return this.getTest().toString() + " on " + this.getHost().toString() + " using Firefox";
	}
}
