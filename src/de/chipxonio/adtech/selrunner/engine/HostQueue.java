package de.chipxonio.adtech.selrunner.engine;

import java.util.Iterator;
import java.util.Vector;

public class HostQueue extends Thread {
	private Vector<SelRunnerTask> tasks = new Vector<SelRunnerTask>();
	
	public void add(SelRunnerTask task) {
		this.tasks.add(task);
	}
	
	public void run() {
		Iterator<SelRunnerTask> i = this.tasks.iterator();
		while (i.hasNext()) i.next().run();
	}
}
