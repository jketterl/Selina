package de.chipxonio.adtech.selina.engine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

import org.openqa.selenium.WebDriver;

import de.chipxonio.adtech.selina.browsers.Browser;
import de.chipxonio.adtech.selina.hosts.Host;
import de.chipxonio.adtech.selina.hosts.HostRegistry;
import de.chipxonio.adtech.selina.packages.TestDefinition;
import de.chipxonio.adtech.selina.tests.AbstractTest;
import de.chipxonio.adtech.selina.tests.TestResult;
import de.chipxonio.adtech.selina.tests.TestResultListener;

public class SelinaTask implements Serializable, TestResultListener {
	private static final long serialVersionUID = 7731026883005748237L;
	private Host host;
	private TestDefinition test;
	private Browser browser;
	transient private Vector<SelinaTaskListener> listeners = new Vector<SelinaTaskListener>();
	transient private WebDriver driver = null;
	transient private int status = SelinaTaskListener.STOPPED;
	transient private TestResult result;
	
	/**
	 * restore object state after unserialization
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		this.listeners = new Vector<SelinaTaskListener>();
		// reconnect with host object
		Host h = HostRegistry.getSharedInstance().get(this.host.getId());
		if (h != null) this.host = h;
	}
	
	public int getStatus() {
		return status;
	}

	public SelinaTask() {
		super();
	};
	
	public SelinaTask(Host host, TestDefinition test, Browser browser) {
		super();
		this.host = host;
		this.test = test;
		this.browser = browser;
	}
	
	public void addListener(SelinaTaskListener l) {
		this.listeners.add(l);
	}
	
	private void fireTestingComplete(TestResult result) {
		Iterator<SelinaTaskListener> i = this.listeners.iterator();
		while (i.hasNext()) i.next().testingComplete(result);
	}
	
	private void fireResultChanged(TestResult result) {
		Iterator<SelinaTaskListener> i = this.listeners.iterator();
		while (i.hasNext()) i.next().resultChanged(this, result);
	}
	
	public void removeListener(SelinaTaskListener l) {
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
			this.driver = this.getBrowser().getDriver(host);
		}
		return this.driver;
	}
	
	public void reset() throws SelinaTaskException {
		if (this.getStatus() == SelinaTaskListener.RUNNING)
			throw new SelinaTaskException("Cannot reset a running task");
		if (this.result != null) {
			this.result.removeListener(this);
			this.result = null;
		}
		this.setStatus(SelinaTaskListener.STOPPED);
	}

	public void run()
	{
		this.setStatus(SelinaTaskListener.RUNNING);
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
				test.setOverallResult(result);
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
		result.stopTimer();
		this.setStatus(SelinaTaskListener.COMPLETE);
	}
	
	public String toString() {
		if (this.getTest() == null || this.getHost() == null) return "<uninitialized task>";
		String ret;
		ret = this.getTest().toString() + " on " + this.getHost().toString() + " using " + this.getBrowser().toString();
		if (result != null) ret += " (" + result.toString() + ")";
		return ret;
	}
	
	private void setStatus(int status) {
		this.status = status;
		Iterator<SelinaTaskListener> i = this.listeners.iterator();
		while (i.hasNext()) i.next().statusUpdated(this, status);
	}

	public TestResult getResult() {
		return result;
	}

	@Override
	public void testResultChanged(TestResult result) {
		this.fireResultChanged(result);
	}

	public Browser getBrowser() {
		return browser;
	}

	public void setBrowser(Browser browser) {
		this.browser = browser;
	}
}
