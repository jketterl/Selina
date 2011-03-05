package de.chipxonio.adtech.selrunner.engine;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import de.chipxonio.adtech.selrunner.hosts.Host;
import de.chipxonio.adtech.selrunner.tests.TestResult;

public class SelRunnerJob implements SelRunnerTaskListener {
	private Vector<SelRunnerTask> tasks = new Vector<SelRunnerTask>();
	private Vector<SelRunnerJobListener> listeners = new Vector<SelRunnerJobListener>();
	
	public void addTask(SelRunnerTask task) {
		this.tasks.add(task);
		task.addListener(this);
	}
	
	public void run() {
		// assort all queued tasks into per-host queues
		Hashtable<Host,HostQueue> queues = new Hashtable<Host,HostQueue>();
		Iterator<SelRunnerTask> i = this.tasks.iterator();
		while (i.hasNext()) {
			SelRunnerTask task = i.next();
			HostQueue hostQueue = queues.get(task.getHost());
			if (hostQueue == null) {
				hostQueue = new HostQueue();
				queues.put(task.getHost(), hostQueue);
			}
			hostQueue.add(task);
		}

		// start one host queue thread per host
		Enumeration<Host> hosts = queues.keys();
		while (hosts.hasMoreElements()) {
			Host host = hosts.nextElement();
			HostQueue hostQueue = queues.get(host);
			// start background thread
			hostQueue.start();
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
