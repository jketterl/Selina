package de.chipxonio.adtech.selrunner.library;

import de.chipxonio.adtech.selrunner.hosts.HostList;
import de.chipxonio.adtech.selrunner.packages.PackageList;

abstract public class Library {
	abstract public HostList getHostList();
	abstract public PackageList getPackageList();
}