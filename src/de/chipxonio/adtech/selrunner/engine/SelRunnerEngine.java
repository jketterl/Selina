package de.chipxonio.adtech.selrunner.engine;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import de.chipxonio.adtech.selrunner.hosts.Host;
import de.chipxonio.adtech.selrunner.tests.TestResult;

public class SelRunnerEngine implements SelRunnerJobListener, HostQueueListener {
	private SelRunnerJob job;
	private Vector<SelRunnerEngineListener> listeners = new Vector<SelRunnerEngineListener>();
	private Hashtable<Host,HostQueue> queues = new Hashtable<Host,HostQueue>();

	public void setJob(SelRunnerJob job) {
		if (this.job != null) this.job.removeListener(this);
		this.job = job;
		this.job.addListener(this);
		this.job.setEngine(this);
	}
	
	public SelRunnerJob getJob() {
		return this.job;
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
	
	@Override
	public void statusChanged(HostQueueEvent e) {
		if ((e.getStatus() & HostQueueEvent.RUNNING) == HostQueueEvent.STOPPED) {
			this.queues.remove(e.getSource().getHost());
		}
	}
	
	public HostQueue getQueue(Host host) {
		HostQueue hostQueue = this.queues.get(host);
		if (hostQueue == null) {
			hostQueue = new HostQueue(host);
			hostQueue.addListener(this);
			queues.put(host, hostQueue);
		}
		return hostQueue;
	}
}
