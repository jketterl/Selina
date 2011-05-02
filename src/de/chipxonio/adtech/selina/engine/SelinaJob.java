package de.chipxonio.adtech.selina.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import de.chipxonio.adtech.selina.packages.PackageLoader;
import de.chipxonio.adtech.selina.results.TestResult;

public class SelinaJob extends Vector<SelinaTask> implements SelinaTaskListener, ListModel {
	private static final long serialVersionUID = 6697337614166675395L;
	transient private Vector<SelinaListener> listeners = new Vector<SelinaListener>();
	transient private Vector<ListDataListener> listListeners = new Vector<ListDataListener>();
	
	/**
	 * restore object state after unserialization
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		this.listeners = new Vector<SelinaListener>();
		this.listListeners = new Vector<ListDataListener>();
		Iterator<SelinaTask> i = iterator();
		while (i.hasNext()) i.next().addListener(this);
	}
	
	public boolean add(SelinaTask task) {
		boolean ret = super.add(task);
		if (ret) {
			task.addListener(this);
			int index = this.indexOf(task);
			this.fireIntervalAdded(new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, index, index));
		}
		return ret;
	}
	
	@Override
	public boolean remove(Object o) {
		int index = this.indexOf(o);
		boolean ret = super.remove(o);
		if (ret) {
			((SelinaTask) o).removeListener(this);
			this.fireIntervalRemoved(new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, index, index));
		}
		return ret;
	}

	public void addListener(SelinaListener l) {
		this.listeners.add(l);
	}
	
	private void fireTestingComplete(TestResult result) {
		Iterator<SelinaListener> i = this.listeners.iterator();
		while (i.hasNext()) i.next().testingComplete(result);
	}
	
	public void removeListener(SelinaListener l) {
		this.listeners.remove(l);
	}

	@Override
	public void testingComplete(TestResult result) {
		this.fireContentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, this.size()));
		this.fireTestingComplete(result);
	}
	
	public void saveToFile(File file) {
		/*
		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));
		encoder.writeObject(this);
		encoder.close();
		*/
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(this);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static SelinaJob loadFromFile(File file) {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(file)) {
				protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
					try {
						return Class.forName(desc.getName());
					} catch (ClassNotFoundException e) {
						try {
							return PackageLoader.getSharedInstance().loadClass(desc.getName());
						} catch (ClassNotFoundException e1) {
						}
						throw new ClassNotFoundException(desc.getName());
					}
				}
			};
			SelinaJob job = (SelinaJob) ois.readObject();
			ois.close();
			return job;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addListDataListener(ListDataListener arg0) {
		this.listListeners.add(arg0);
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
		this.listListeners.remove(arg0);
	}

	private void fireContentsChanged(ListDataEvent e) {
		Iterator<ListDataListener> i = listListeners.iterator();
		while (i.hasNext()) i.next().contentsChanged(e);
	}
	
	private void fireIntervalAdded(ListDataEvent e) {
		Iterator<ListDataListener> i = listListeners.iterator();
		while (i.hasNext()) i.next().intervalAdded(e);
	}
	
	private void fireIntervalRemoved(ListDataEvent e) {
		Iterator<ListDataListener> i = listListeners.iterator();
		while (i.hasNext()) i.next().intervalRemoved(e);
	}

	@Override
	public void statusUpdated(SelinaTask source, int status) {
		int index = this.indexOf(source);
		if (index < 0) return;
		this.fireContentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, index, index));
	}

	@Override
	public void resultChanged(SelinaTask source, TestResult result) {
		int index = this.indexOf(source);
		if (index < 0) return;
		this.fireContentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, index, index));
	}
}
