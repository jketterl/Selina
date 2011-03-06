package de.chipxonio.adtech.selrunner.engine;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import de.chipxonio.adtech.selrunner.tests.TestResult;

public class SelRunnerJob implements SelRunnerTaskListener {
	private Vector<SelRunnerJobListener> listeners = new Vector<SelRunnerJobListener>();
	private LinkedList<SelRunnerTask> unstartedTasks = new LinkedList<SelRunnerTask>();
	private SelRunnerEngine engine;
	
	public void addTask(SelRunnerTask task) {
		task.addListener(this);
		if (this.hasEngine()) {
			this.engine.getQueue(task.getHost()).add(task);
		} else {
			this.unstartedTasks.add(task);
		}
	}
	
	public boolean hasEngine() {
		return this.engine != null;
	}
	
	public SelRunnerEngine getEngine() {
		return engine;
	}

	public void setEngine(SelRunnerEngine engine) {
		this.engine = engine;
		SelRunnerTask task;
		while ((task = this.unstartedTasks.poll()) != null) {
			this.engine.getQueue(task.getHost()).add(task);
		}
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
