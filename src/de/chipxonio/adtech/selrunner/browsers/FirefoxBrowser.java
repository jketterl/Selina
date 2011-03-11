package de.chipxonio.adtech.selrunner.browsers;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class FirefoxBrowser extends WebDriverBrowser {
	protected String name = "Firefox";
	
	@Override
	protected DesiredCapabilities getCapabilities() {
		DesiredCapabilities c = DesiredCapabilities.firefox();
		c.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
		return c;
	}
}
