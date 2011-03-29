package de.chipxonio.adtech.selrunner.browsers;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.SeleneseCommandExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import de.chipxonio.adtech.selrunner.hosts.Host;

public class SafariBrowser extends Browser {
	@Override
	public WebDriver getDriver(Host host) {
		try {
			URL seleniumServer = new URL("http://localhost:4444");
			URL remoteUrl = new URL("http://www.google.com/");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setBrowserName("safari");
			CommandExecutor executor = new SeleneseCommandExecutor(seleniumServer, remoteUrl, capabilities);
			return new RemoteWebDriver(executor, capabilities);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String getName() {
		return "Safari";
	}
}
