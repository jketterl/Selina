package de.chipxonio.adtech.selrunner.browsers;

import org.openqa.selenium.remote.DesiredCapabilities;

public class InternetExplorerBrowser extends WebDriverBrowser {
	@Override
	protected DesiredCapabilities getCapabilities() {
		return DesiredCapabilities.internetExplorer();
	}

	@Override
	public String getName() {
		return "Internet Explorer";
	}
}
