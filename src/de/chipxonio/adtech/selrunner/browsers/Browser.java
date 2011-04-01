package de.chipxonio.adtech.selrunner.browsers;

import java.io.Serializable;

import org.openqa.selenium.WebDriver;

import de.chipxonio.adtech.selrunner.hosts.Host;

abstract public class Browser implements Serializable {
	private static final long serialVersionUID = -1438333015573778668L;
	protected String name;
	

	public String toString()
	{
		return this.getName();
	}

	abstract public WebDriver getDriver(Host host);
	abstract public String getName();
}
