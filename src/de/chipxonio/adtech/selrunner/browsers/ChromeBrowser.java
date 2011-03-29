package de.chipxonio.adtech.selrunner.browsers;

import org.openqa.selenium.remote.DesiredCapabilities;

public class ChromeBrowser extends WebDriverBrowser {
	@Override
	public String getName() {
		return "Google Chrome";
	}

	@Override
	protected DesiredCapabilities getCapabilities() {
		return DesiredCapabilities.chrome();
	}
}
