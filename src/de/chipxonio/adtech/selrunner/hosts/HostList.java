package de.chipxonio.adtech.selrunner.hosts;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import de.chipxonio.adtech.selrunner.util.ActiveVector;

public class HostList extends ActiveVector<Host>  {
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
		return super.remove(o);
	}
}
