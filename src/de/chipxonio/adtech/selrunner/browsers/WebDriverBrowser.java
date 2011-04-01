package de.chipxonio.adtech.selrunner.browsers;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import de.chipxonio.adtech.selrunner.hosts.Host;

public abstract class WebDriverBrowser extends Browser {
	private static final long serialVersionUID = 8574993202429022354L;

	@Override
	public WebDriver getDriver(Host host) {
		return new RemoteWebDriver(this.getWebDriverURL(host), this.getCapabilities());
	}
		
	private URL getWebDriverURL(Host host) {
		try {
			return new URL("http://" + host.getHostName() + ":" + host.getPort() + "/wd/hub");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	abstract protected DesiredCapabilities getCapabilities();
}
