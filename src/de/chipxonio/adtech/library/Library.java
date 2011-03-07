package de.chipxonio.adtech.library;

import de.chipxonio.adtech.selrunner.hosts.HostList;
import de.chipxonio.adtech.selrunner.packages.PackageList;

abstract public class Library {
	abstract public HostList getHosts();
	abstract public PackageList getPackages();
}