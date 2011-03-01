package de.chipxonio.adtech.selrunner.hosts;

import java.util.Vector;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class HostList extends Vector<Host> implements ListModel {
	private static final long serialVersionUID = 5240371749382136337L;

	@Override
	public void addListDataListener(ListDataListener arg0) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
	}
}
