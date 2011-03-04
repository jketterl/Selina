package de.chipxonio.adtech.selrunner.engine;

import java.util.Iterator;
import java.util.Vector;

import de.chipxonio.adtech.selrunner.tests.TestResult;

public class SelRunnerJob implements SelRunnerTaskListener {
	private Vector<SelRunnerTask> tasks = new Vector<SelRunnerTask>();
	private Vector<SelRunnerJobListener> listeners = new Vector<SelRunnerJobListener>();
	
	public void addTask(SelRunnerTask task) {
		this.tasks.add(task);
		task.addListener(this);
	}
	
	public void run() {
		Iterator<SelRunnerTask> i = tasks.iterator();
		// start background threads
		while (i.hasNext()) i.next().start();
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
