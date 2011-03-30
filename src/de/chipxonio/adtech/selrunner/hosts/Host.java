package de.chipxonio.adtech.selrunner.hosts;

import java.io.Serializable;
import java.util.Iterator;
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
	private Preferences preferences;
	private HostMonitor monitor;
	private int status = DOWN;
	private Vector<HostStatusListener> listeners = new Vector<HostStatusListener>();
	
	public Host() {
		this.monitor = new HostMonitor(this);
	}
	
	public Host(Preferences prefs) {
		this();
		this.preferences = prefs;
		this.setName(this.preferences.get("name", null));
		this.setHostName(this.preferences.get("hostName", null));
		this.setPort(this.preferences.getInt("port", 4444));
	}
	
	public void setPreferences(Preferences prefs) {
		this.preferences = prefs;
		this.preferences.put("name", this.name);
		this.preferences.put("hostName", this.hostName);
		this.preferences.putInt("port", this.port);
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
}
