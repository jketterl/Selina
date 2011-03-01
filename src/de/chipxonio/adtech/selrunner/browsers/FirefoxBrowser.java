package de.chipxonio.adtech.selrunner.browsers;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

public class FirefoxBrowser extends WebDriverBrowser {
	protected String name = "Firefox";
	
	@Override
	protected Capabilities getCapabilities() {
		return DesiredCapabilities.firefox();
	}
}
