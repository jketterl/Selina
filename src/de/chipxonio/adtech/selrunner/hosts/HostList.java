package de.chipxonio.adtech.selrunner.hosts;

import java.util.Iterator;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.event.ListDataEvent;

import de.chipxonio.adtech.selrunner.util.ActiveVector;

public class HostList extends ActiveVector<Host> implements HostStatusListener {
	private static final long serialVersionUID = 5240371749382136337L;
	private Preferences preferences;
	
	public HostList(Preferences p) {
		super();
		this.setPreferences(p);
	}
	
	public boolean hasPreferences() {
		return this.preferences != null;
	}
	
	public void setPreferences(Preferences p) {
		this.clear();
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
	@Override
	public synchronized boolean add(Host e) {
		boolean ret = super.add(e);
		if (ret) {
			e.addStatusListener(this);
			if (this.hasPreferences() && !e.hasPreferences()) {
				e.storeToPreferences(this.preferences.node(e.getId()));
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

	public Host getHostById(String id) {
		Iterator<Host> i = iterator();
		while (i.hasNext()) {
			Host h = i.next();
			if (h.getId().equals(id)) return h;
		}
		return null;
	}
}
