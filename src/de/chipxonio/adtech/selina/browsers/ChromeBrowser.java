package de.chipxonio.adtech.selina.browsers;

import org.openqa.selenium.remote.DesiredCapabilities;

public class ChromeBrowser extends WebDriverBrowser {
	private static final long serialVersionUID = 6883367383510704541L;

	@Override
	public String getName() {
		return "Google Chrome";
	}

	@Override
	protected DesiredCapabilities getCapabilities() {
		return DesiredCapabilities.chrome();
	}
}
