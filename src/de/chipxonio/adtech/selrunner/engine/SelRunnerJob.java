package de.chipxonio.adtech.selrunner.engine;

import java.util.Iterator;
import java.util.Vector;

import de.chipxonio.adtech.selrunner.tests.TestResult;

public class SelRunnerJob extends Vector<SelRunnerTask> implements SelRunnerTaskListener {
	private static final long serialVersionUID = 6697337614166675395L;
	private Vector<SelRunnerJobListener> listeners = new Vector<SelRunnerJobListener>();
	//private LinkedList<SelRunnerTask> tasks = new LinkedList<SelRunnerTask>();
	
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
}
