package de.chipxonio.adtech.selrunner.util;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class ActiveVector<E> extends Vector<E> implements ListModel {
	private static final long serialVersionUID = 4720109235143268581L;
	private Vector<ListDataListener> listeners = new Vector<ListDataListener>();

	@Override
	public void addListDataListener(ListDataListener arg0) {
		listeners.add(arg0);
	}

	@Override
	public Object getElementAt(int arg0) {
		return get(arg0);
	}

	@Override
	public int getSize() {
		return size();
	}

	@Override
	public void removeListDataListener(ListDataListener arg0) {
		listeners.remove(arg0);
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
	public synchronized boolean add(E e) {
		boolean result = super.add(e);
		if (!result) return result;
		int index = this.indexOf(e);
		this.fireIntervalAdded(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index, index));
		return result;
	}

	@Override
	public boolean remove(Object o) {
		int index = this.indexOf(o);
		boolean result = super.remove(o);
		if (!result) return result;
		this.fireIntervalRemoved(new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index, index));
		return result;
	}
}
