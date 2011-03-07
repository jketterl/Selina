package de.chipxonio.adtech.selrunner.engine;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Iterator;
import java.util.Vector;

import de.chipxonio.adtech.selrunner.tests.TestResult;

public class SelRunnerJob extends Vector<SelRunnerTask> implements SelRunnerTaskListener {
	private static final long serialVersionUID = 6697337614166675395L;
	private Vector<SelRunnerJobListener> listeners = new Vector<SelRunnerJobListener>();
	
	public boolean add(SelRunnerTask task) {
		task.addListener(this);
		return super.add(task);
	}
	
	public void addListener(SelRunnerJobListener l) {
		this.listeners.add(l);
	}
	
	private void fireTestingComplete(TestResult result) {
		Iterator<SelRunnerJobListener> i = this.listeners.iterator();
		while (i.hasNext()) i.next().testingComplete(result);
	}
	
	public void removeListener(SelRunnerJobListener l) {
		this.listeners.remove(l);
	}

	@Override
	public void testingComplete(TestResult result) {
		this.fireTestingComplete(result);
	}
	
	public void saveToFile(File file) {
		try {
			XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));
			encoder.writeObject(this);
			encoder.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		oos.writeObject(this);
		oos.close();
		*/
	}
	
	public static SelRunnerJob loadFromFile(File file) {
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(file));
			SelRunnerJob job = (SelRunnerJob) ois.readObject();
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
}
