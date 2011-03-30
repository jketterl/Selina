package de.chipxonio.adtech.selrunner.hosts;

import java.util.Iterator;
import java.util.UUID;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.event.ListDataEvent;

import de.chipxonio.adtech.selrunner.util.ActiveVector;

public class HostList extends ActiveVector<Host> implements HostStatusListener {
	private static final long serialVersionUID = 5240371749382136337L;
	private Preferences preferences;
	
	public HostList(Preferences p) {
		super();
		this.preferences = p;
		try {
			String[] names = this.preferences.childrenNames();
			for (int i = 0; i < names.length; i++) {
				this.add(new Host(this.preferences.node(names[i])));
			}
		} catch (BackingStoreException e) {
			try {
				this.preferences.clear();
			} catch (BackingStoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public boolean hasPreferences() {
		return this.preferences != null;
	}
	
	public void setPreferences(Preferences p) {
		this.preferences = p;
		Iterator<Host> i = this.iterator();
		while (i.hasNext()) {
			Host host = i.next();
			host.setPreferences(this.preferences.node(host.getHostName()));
		}
	}
	@Override
	public synchronized boolean add(Host e) {
		boolean ret = super.add(e);
		if (ret) {
			e.addStatusListener(this);
			if (this.hasPreferences() && !e.hasPreferences()) {
				e.setPreferences(this.preferences.node(UUID.nameUUIDFromBytes(e.toString().getBytes()).toString()));
			}
		}
		return ret;
	}

	@Override
	public boolean remove(Object o) {
		Host h = (Host) o;
		if (h.hasPreferences()) try {
				h.getPreferences().removeNode();
			} catch (BackingStoreException e) {
				e.printStackTrace();
			}
		boolean ret = super.remove(o);
		if (ret) h.removeStatusListener(this);
		return ret;
	}

	@Override
	public void statusChanged(HostStatusEvent newStatus) {
		int index = this.indexOf(newStatus.getSource());
		if (index < 0) return;
		this.fireContentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, index, index));
	}
}
