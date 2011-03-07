package de.chipxonio.adtech.selrunner.packages;

import java.util.Iterator;

public class PackageLoader extends ClassLoader {
	private static PackageLoader sharedInstance;
	private PackageList packages = new PackageList();
	
	public void registerPackage(Package p) {
		packages.add(p);
	}
	
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		Iterator<Package> i = packages.iterator();
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
}
