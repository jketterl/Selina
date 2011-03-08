package de.chipxonio.adtech.selrunner.packages;

import java.io.IOException;
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
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class PackageList extends Vector<Package> implements ListModel, TreeModel {
	private static final long serialVersionUID = -6282910026609075670L;
	private Vector<ListDataListener> listeners = new Vector<ListDataListener>();
	private Vector<TreeModelListener> treeListeners = new Vector<TreeModelListener>();
	private Preferences preferences;
	
	public PackageList() {
		super();
	}
	
	public PackageList(Preferences prefs) {
		this.preferences = prefs;
		try {
			String[] keys = this.preferences.childrenNames();
			for (int i = 0; i < keys.length; i++) {
				try {
					this.add(new Package(this.preferences.node(keys[i])));
				} catch (IOException e) {
				} catch (PackageLoaderException e) {
				}
			}
		} catch (BackingStoreException e) {
		}
	}
	
	@Override
	public void addListDataListener(ListDataListener arg0) {
		this.listeners.add(arg0);
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
	public void removeListDataListener(ListDataListener arg0) {
		this.listeners.remove(arg0);
	}

	@Override
	public synchronized boolean add(Package e) {
		boolean ret = super.add(e);
		if (ret) {
			int index = this.indexOf(e);
			this.fireIntervalAdded(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index, index));
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

	private boolean hasPreferences() {
		return this.preferences != null;
	}

	@Override
	public boolean remove(Object o) {
		int index = this.indexOf(o);
		this.fireIntervalRemoved(new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index, index));
		Package p = (Package) o;
		if (p.hasPreferences()) try {
			p.getPreferences().removeNode();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		return super.remove(o);
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

	@Override
	public void addTreeModelListener(TreeModelListener arg0) {
		this.treeListeners.add(arg0);
	}

	@Override
	public Object getChild(Object arg0, int arg1) {
		if (arg0 == this)
			return this.get(arg1);
		else
			return ((TreeModel) arg0).getChild(arg0, arg1);
	}

	@Override
	public int getChildCount(Object arg0) {
		if (arg0 == this)
			return this.size();
		else
			return ((TreeModel) arg0).getChildCount(arg0);
	}

	@Override
	public int getIndexOfChild(Object arg0, Object arg1) {
		if (arg0 == this)
			return this.indexOf(arg1);
		else
			return ((TreeModel) arg0).getIndexOfChild(arg0, arg1);
	}

	@Override
	public Object getRoot() {
		return this;
	}

	@Override
	public boolean isLeaf(Object arg0) {
		if (arg0 == this)
			return false;
		else
			return ((TreeModel) arg0).isLeaf(arg0);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener arg0) {
		this.treeListeners.remove(arg0);
	}

	@Override
	public void valueForPathChanged(TreePath arg0, Object arg1) {
	}
}
