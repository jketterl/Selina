package de.chipxonio.adtech.selina.library;

import de.chipxonio.adtech.selina.hosts.HostList;
import de.chipxonio.adtech.selina.packages.PackageList;

abstract public class Library {
	abstract public HostList getHostList();
	abstract public PackageList getPackageList();
}