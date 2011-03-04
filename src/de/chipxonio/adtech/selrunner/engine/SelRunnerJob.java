package de.chipxonio.adtech.selrunner.engine;

import java.util.Iterator;
import java.util.Vector;

public class SelRunnerJob {
	private Vector<SelRunnerTask> tasks = new Vector<SelRunnerTask>();
	
	public void addTask(SelRunnerTask task) {
		this.tasks.add(task);
	}
	
	public void run() {
		Iterator<SelRunnerTask> i = tasks.iterator();
		// start background threads
		while (i.hasNext()) i.next().start();
	}
}
