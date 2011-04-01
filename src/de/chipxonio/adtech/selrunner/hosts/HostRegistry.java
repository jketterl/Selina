package de.chipxonio.adtech.selrunner.hosts;

import java.util.HashMap;

public class HostRegistry extends HashMap<String, Host> {
	private static final long serialVersionUID = -7949008903565663181L;
	protected static HostRegistry sharedInstance;
	private static Object sync = new Object();

	public static HostRegistry getSharedInstance() {
		if (sharedInstance == null) {
			synchronized(sync) {
				if (sharedInstance == null) {
					sharedInstance = new HostRegistry();
				}
			}
		}
		return sharedInstance;
	}
	
	private HostRegistry(){
		super();
	}
}
