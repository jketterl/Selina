package de.chipxonio.adtech.selrunner.library;

import java.util.prefs.Preferences;

import de.chipxonio.adtech.selrunner.hosts.HostList;
import de.chipxonio.adtech.selrunner.packages.PackageList;

public class LocalLibrary extends Library {
	private HostList hosts;
	private PackageList packages;
	private Preferences preferences;
	
	public LocalLibrary(Preferences p) {
		this.preferences = p;
	}

	@Override
	public HostList getHosts() {
		if (this.hosts == null) {
			this.hosts = new HostList(this.preferences.node("hostlist"));
		}
		return this.hosts;
	}

	@Override
	public PackageList getPackages() {
		if (this.packages == null) {
			this.packages = new PackageList();
		}
		return this.packages;
	}
}
