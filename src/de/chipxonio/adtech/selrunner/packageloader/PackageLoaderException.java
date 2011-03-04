package de.chipxonio.adtech.selrunner.packageloader;

public class PackageLoaderException extends Exception {
	// why do i have to do this? those constructors are defined by the parent :(
	public PackageLoaderException(String string, Throwable t) {
		super(string, t);
	}

	public PackageLoaderException(String string) {
		super(string);
	}

	private static final long serialVersionUID = -6892078119246948657L;
}
