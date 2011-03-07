package de.chipxonio.adtech.selrunner.hosts;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Vector;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class HostList extends Vector<Host> implements ListModel {
	private static final long serialVersionUID = 5240371749382136337L;
	private Preferences preferences;
	
	private Vector<ListDataListener> listeners = new Vector<ListDataListener>();
	
	public HostList() {
		super();
	}
	
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
	public void addListDataListener(ListDataListener arg0) {
		listeners.add(arg0);
	}

	@Override
	public Object getElementAt(int arg0) {
		return this.get(arg0);
	}

	@Override
	public int getSize() {
		return this.size();
	}

	@Override
	public synchronized boolean add(Host e) {
		boolean ret = super.add(e);
		if (ret) {
			int index = this.indexOf(e);
			this.fireIntervalAdded(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index, index));
		}
		if (this.hasPreferences() && !e.hasPreferences()) {
			String nodeName;
			try {
				// damn. creating an md5 hash in java is hard work...
				byte[] md5 = MessageDigest.getInstance("MD5").digest(e.toString().getBytes("UTF-8"));
				StringBuffer hexString = new StringBuffer();
				for (int i = 0; i < md5.length; i++) hexString.append(Integer.toHexString(0xFF & md5[i]));
				nodeName = hexString.toString();
			} catch (NoSuchAlgorithmException e1) {
				nodeName = e.toString();
			} catch (UnsupportedEncodingException e1) {
				nodeName = e.toString();
			}
			e.setPreferences(this.preferences.node(nodeName));
		}
		return ret;
	}

	@Override
	public boolean remove(Object o) {
		int index = this.indexOf(o);
		this.fireIntervalRemoved(new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index, index));
		Host h = (Host) o;
		if (h.hasPreferences()) try {
				h.getPreferences().removeNode();
			} catch (BackingStoreException e) {
				e.printStackTrace();
			}
		return super.remove(o);
	}

	@Override
	public void removeListDataListener(ListDataListener arg0) {
		listeners.remove(arg0);
	}
	
	private void fireContentsChanged(ListDataEvent e) {
		Iterator<ListDataListener> i = listeners.iterator();
		while (i.hasNext()) i.next().contentsChanged(e);
	}
	
	private void fireIntervalAdded(ListDataEvent e) {
		Iterator<ListDataListener> i = listeners.iterator();
		while (i.hasNext()) i.next().intervalAdded(e);
	}
	
	private void fireIntervalRemoved(ListDataEvent e) {
		Iterator<ListDataListener> i = listeners.iterator();
		while (i.hasNext()) i.next().intervalRemoved(e);
	}
}
