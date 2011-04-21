package de.chipxonio.adtech.selina.library;

import java.util.prefs.Preferences;

import de.chipxonio.adtech.selina.hosts.HostList;
import de.chipxonio.adtech.selina.packages.PackageList;

public class LocalLibrary extends Library {
	private HostList hosts;
	private PackageList packages;
	private Preferences preferences;
	
	public LocalLibrary(Preferences p) {
		this.preferences = p;
	}

	@Override
	public HostList getHostList() {
		if (this.hosts == null) {
			this.hosts = new HostList(this.preferences.node("hostlist"));
		}
		return this.hosts;
	}

	@Override
	public PackageList getPackageList() {
		if (this.packages == null) {
			this.packages = new PackageList(this.preferences.node("packagelist"));
		}
		return this.packages;
	}
}
