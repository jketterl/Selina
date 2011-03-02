package de.chipxonio.adtech.selrunner.hosts;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class HostList extends Vector<Host> implements ListModel {
	private static final long serialVersionUID = 5240371749382136337L;
	
	private Vector<ListDataListener> listeners = new Vector<ListDataListener>();

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
		return ret;
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
