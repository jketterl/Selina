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
import de.chipxonio.adtech.selrunner.tests.TestResultListener;

public class SelRunnerTask extends Thread implements Serializable, TestResultListener {
	private static final long serialVersionUID = 7731026883005748237L;
	private Host host;
	private TestDefinition test;
	private Vector<SelRunnerTaskListener> listeners = new Vector<SelRunnerTaskListener>();
	private WebDriver driver = null;
	private int status = SelRunnerTaskListener.STOPPED;
	private TestResult result;
	
	public int getStatus() {
		return status;
	}

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
	
	private void fireResultChanged(TestResult result) {
		Iterator<SelRunnerTaskListener> i = this.listeners.iterator();
		while (i.hasNext()) i.next().resultChanged(this, result);
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
	
	public void reset() throws SelRunnerTaskException {
		if (this.getStatus() == SelRunnerTaskListener.RUNNING)
			throw new SelRunnerTaskException("Cannot reset a running task");
		if (this.result != null) {
			this.result.removeListener(this);
			this.result = null;
		}
		this.setStatus(SelRunnerTaskListener.STOPPED);
	}

	public void run()
	{
		this.setStatus(SelRunnerTaskListener.RUNNING);
		result = new TestResult();
		result.addListener(this);
		WebDriver driver = null;
		try {
			driver = this.getDriver();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (driver != null) {
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
		this.setStatus(SelRunnerTaskListener.COMPLETE);
	}
	
	public String toString() {
		if (this.getTest() == null || this.getHost() == null) return "<uninitialized task>";
		String ret;
		ret = this.getTest().toString() + " on " + this.getHost().toString() + " using Firefox";
		if (result != null) ret += " (" + result.toString() + ")";
		return ret;
	}
	
	private void setStatus(int status) {
		this.status = status;
		Iterator<SelRunnerTaskListener> i = this.listeners.iterator();
		while (i.hasNext()) i.next().statusUpdated(this, status);
	}

	public TestResult getResult() {
		return result;
	}

	@Override
	public void testResultChanged(TestResult result) {
		this.fireResultChanged(result);
	}
}
