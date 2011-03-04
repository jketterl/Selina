package de.chipxonio.adtech.selrunner.engine;

import java.util.Iterator;
import java.util.Vector;

import de.chipxonio.adtech.selrunner.tests.TestResult;

public class SelRunnerEngine implements SelRunnerJobListener {
	private SelRunnerJob job;
	private Vector<SelRunnerEngineListener> listeners = new Vector<SelRunnerEngineListener>();

	public void setJob(SelRunnerJob job) {
		if (this.job != null) this.job.removeListener(this);
		this.job = job;
		this.job.addListener(this);
	}
	
	public SelRunnerJob getJob() {
		return this.job;
	}
	
	public void run() {
		this.job.run();
	}

	public void addListener(SelRunnerEngineListener l) {
		this.listeners.add(l);
	}
	
	private void fireTestingComplete(TestResult result) {
		Iterator<SelRunnerEngineListener> i = this.listeners.iterator();
		while (i.hasNext()) i.next().testingComplete(result);
	}
	
	public void removeListener(SelRunnerEngineListener l) {
		this.listeners.remove(l);
	}

	@Override
	public void testingComplete(TestResult result) {
		this.fireTestingComplete(result);
	}
}
