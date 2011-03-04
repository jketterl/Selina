package de.chipxonio.adtech.selrunner.packages;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PackageList extends Vector<Package> implements ListModel {
	private static final long serialVersionUID = -6282910026609075670L;
	private Vector<ListDataListener> listeners = new Vector<ListDataListener>();

	@Override
	public void addListDataListener(ListDataListener arg0) {
		this.listeners.add(arg0);
	}

	@Override
	public Object getElementAt(int arg0) {
		return this.getElementAt(arg0);
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
		}
		return ret;
	}

	@Override
	public boolean remove(Object o) {
		int index = this.indexOf(o);
		this.fireIntervalRemoved(new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index, index));
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
}
