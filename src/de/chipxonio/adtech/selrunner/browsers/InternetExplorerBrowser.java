package de.chipxonio.adtech.selrunner.browsers;

import org.openqa.selenium.remote.DesiredCapabilities;

public class InternetExplorerBrowser extends WebDriverBrowser {
	protected String name = "Internet Explorer";
	
	@Override
	protected DesiredCapabilities getCapabilities() {
		return DesiredCapabilities.internetExplorer();
	}
}
