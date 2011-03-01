package de.chipxonio.adtech.selrunner.browsers;

import org.openqa.selenium.WebDriver;

import de.chipxonio.adtech.selrunner.hosts.Host;

abstract public class Browser {
	protected String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString()
	{
		return this.getName();
	}

	abstract public WebDriver getDriver(Host host);
}
