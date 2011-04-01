package de.chipxonio.adtech.selrunner.browsers;

import org.openqa.selenium.remote.DesiredCapabilities;

public class InternetExplorerBrowser extends WebDriverBrowser {
	private static final long serialVersionUID = 1911686840075730412L;

	@Override
	protected DesiredCapabilities getCapabilities() {
		return DesiredCapabilities.internetExplorer();
	}

	@Override
	public String getName() {
		return "Internet Explorer";
	}
}
