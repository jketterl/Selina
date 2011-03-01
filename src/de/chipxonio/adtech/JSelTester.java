package de.chipxonio.adtech;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.SeleneseCommandExecutor;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class JSelTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		URL host;
		RemoteWebDriver driver;
		try {
			host = new URL("http://192.168.60.170:4444/wd/hub");
			driver = new RemoteWebDriver(host, DesiredCapabilities.firefox());
			TestingThread t1 = new TestingThread(driver);
			t1.start();
			Selenium sel = new DefaultSelenium("192.168.60.157", 4444, "*safari", "http://www.chip.de");
			CommandExecutor exec = new SeleneseCommandExecutor(sel);
			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setBrowserName("safari");
			driver = new RemoteWebDriver(exec, dc);
			/*
			host = new URL("http://192.168.60.170:4444/wd/hub");
			driver = new RemoteWebDriver(host, DesiredCapabilities.internetExplorer());
			*/
			TestingThread t2 = new TestingThread(driver);
			t2.start();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

}
