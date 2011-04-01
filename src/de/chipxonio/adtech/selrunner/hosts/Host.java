package de.chipxonio.adtech.selrunner.hosts;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.UUID;
import java.util.Vector;
import java.util.prefs.Preferences;

public class Host implements Serializable {
	public static final int DOWN = 0;
	public static final int UP = 1;
	public static final int QUERYING = 2;
	
	private static final long serialVersionUID = 5974183186503258109L;
	private String name;
	private String hostName;
	private int port = 4444;
	private String id;
	transient private Preferences preferences;
	transient private HostMonitor monitor;
	transient private int status = DOWN;
	transient private Vector<HostStatusListener> listeners = new Vector<HostStatusListener>();
	
	/**
	 * restore object state after unserialization
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		System.out.println("Host " + this.id + " restored");
	}

	public Host() {
		this.monitor = new HostMonitor(this);
	}
	
	public Host(Preferences prefs) {
		this();
		this.loadFromPreferences(prefs);
	}
	
	public void storeToPreferences(Preferences prefs) {
		this.preferences = prefs;
		this.id = prefs.name();
		this.preferences.put("name", this.name);
		this.preferences.put("hostName", this.hostName);
		this.preferences.putInt("port", this.port);
	}
	
	public void loadFromPreferences(Preferences prefs) {
		this.setName(prefs.get("name", null));
		this.setHostName(prefs.get("hostName", null));
		this.setPort(prefs.getInt("port", 4444));
		this.preferences = prefs;
		this.id = prefs.name();
	}
	
	public boolean hasPreferences() {
		return this.preferences != null;
	}
	
	public Preferences getPreferences() {
		return this.preferences;
	}
	
	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
		if (this.hasPreferences()) this.preferences.put("hostName", hostName);
		// TODO replace this with some event-driven stuff
		this.monitor.setHost(this);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
		if (this.hasPreferences()) this.preferences.putInt("post", port);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		if (this.hasPreferences()) this.preferences.put("name", name);
	}

	public String toString()
	{
		return this.getName();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
		this.fireStatusChanged(new HostStatusEvent(this, status));
	}
	
	public void addStatusListener(HostStatusListener l) {
		this.listeners.add(l);
	}
	
	public void removeStatusListener(HostStatusListener l) {
		this.listeners.remove(l);
	}
	
	private void fireStatusChanged(HostStatusEvent newStatus) {
		Iterator<HostStatusListener> i = this.listeners.iterator();
		while (i.hasNext()) i.next().statusChanged(newStatus);
	}

	public String getId() {
		if (this.id == null) {
			if (this.hasPreferences()) {
				this.id = this.preferences.name();
			} else {
				this.id = UUID.randomUUID().toString();
			}
		}
		return this.id;
	}
}
