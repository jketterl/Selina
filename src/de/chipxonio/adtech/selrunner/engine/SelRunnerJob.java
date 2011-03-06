package de.chipxonio.adtech.selrunner.engine;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import de.chipxonio.adtech.selrunner.hosts.Host;
import de.chipxonio.adtech.selrunner.tests.TestResult;

public class SelRunnerJob implements SelRunnerTaskListener, HostQueueListener {
	private Vector<SelRunnerJobListener> listeners = new Vector<SelRunnerJobListener>();
	private Hashtable<Host,HostQueue> queues = new Hashtable<Host,HostQueue>();
	
	public void addTask(SelRunnerTask task) {
		task.addListener(this);
		HostQueue hostQueue = queues.get(task.getHost());
		if (hostQueue == null) {
			hostQueue = new HostQueue(task.getHost());
			hostQueue.addListener(this);
			queues.put(task.getHost(), hostQueue);
		}
		hostQueue.add(task);
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

	@Override
	public void statusChanged(HostQueueEvent e) {
		if ((e.getStatus() & HostQueueEvent.RUNNING) == HostQueueEvent.STOPPED) {
			this.queues.remove(e.getSource());
		}
	}
}
