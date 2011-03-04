package de.chipxonio.adtech.selrunner.engine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.ScreenshotException;

import de.chipxonio.adtech.selrunner.browsers.FirefoxBrowser;
import de.chipxonio.adtech.selrunner.hosts.Host;
import de.chipxonio.adtech.selrunner.testingthread.TestingThreadListener;
import de.chipxonio.adtech.selrunner.tests.AbstractTest;
import de.chipxonio.adtech.selrunner.tests.TestResult;

public class SelRunnerTask extends Thread {
	private Host host;
	private AbstractTest test;
	private Vector<TestingThreadListener> listeners = new Vector<TestingThreadListener>();
	private WebDriver driver = null;
	
	public SelRunnerTask(Host host, AbstractTest test) {
		super();
		this.host = host;
		this.test = test;
	}
	
	public void addListener(TestingThreadListener l) {
		this.listeners.add(l);
	}
	
	private void fireTestingComplete(TestResult result) {
		Iterator<TestingThreadListener> i = this.listeners.iterator();
		while (i.hasNext()) i.next().testingComplete(result);
	}
	
	public void removeListener(TestingThreadListener l) {
		this.listeners.remove(l);
	}
	
	public Host getHost() {
		return host;
	}
	public void setHost(Host host) {
		this.host = host;
	}
	public AbstractTest getTest() {
		return test;
	}
	public void setTest(AbstractTest test) {
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
		this.test.setDriver(this.getDriver());
		TestResult result = new TestResult();
		this.test.setResult(result);
		try {
			this.test.run();
		} catch (Exception e) {
			result.pushException(e);
			e.printStackTrace();
			if (e instanceof WebDriverException) this.extractScreenshot((WebDriverException) e);
		}
		this.fireTestingComplete(result);
		getDriver().quit();
	}
	
	protected void extractScreenshot(WebDriverException e) {
		Throwable cause = e.getCause();
		if (cause instanceof ScreenshotException) {
			try {
				File f = new File("/home/jketterl/screenshot.png");
				if (f.exists()) f.delete();
				FileOutputStream file = new FileOutputStream(f);
				file.write(Base64.decodeBase64(((ScreenshotException) cause).getBase64EncodedScreenshot()));
				file.close();
				System.out.println("screenshot has been written");
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("failed writing screenshot");
			}
		} else {
			System.out.println("no screenshot was provided");
		}
	}
}
