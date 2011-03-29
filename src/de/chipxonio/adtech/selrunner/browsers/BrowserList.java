package de.chipxonio.adtech.selrunner.browsers;

import de.chipxonio.adtech.selrunner.util.ActiveVector;

public class BrowserList extends ActiveVector<Browser> {
	private static final long serialVersionUID = -3310353983459180577L;
	
	public BrowserList() {
		super();
		this.add(new FirefoxBrowser());
		this.add(new InternetExplorerBrowser());
		this.add(new ChromeBrowser());
	}
}
