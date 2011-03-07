package de.chipxonio.adtech.selrunner.packages;

import java.util.Iterator;

public class PackageLoader extends ClassLoader {
	private static PackageLoader sharedInstance;
	private PackageList packagelist;
	
	public void registerPackage(Package p) {
		packagelist.add(p);
	}
	
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		if (packagelist == null) throw new ClassNotFoundException(name);
		Iterator<Package> i = packagelist.iterator();
		while (i.hasNext()) try {
			return i.next().loadClass(name);
		} catch (ClassNotFoundException e1) {
		}
		throw new ClassNotFoundException(name);
	}
	
	public static PackageLoader getSharedInstance()
	{
		if (sharedInstance == null) {
			sharedInstance = new PackageLoader();
		}
		return sharedInstance;
	}

	public PackageList getPackageList() {
		return packagelist;
	}

	public void setPackageList(PackageList packages) {
		this.packagelist = packages;
	}
}
