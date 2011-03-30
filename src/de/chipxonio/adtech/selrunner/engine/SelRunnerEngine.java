package de.chipxonio.adtech.selrunner.engine;

import java.util.Hashtable;
import java.util.Iterator;

import de.chipxonio.adtech.selrunner.hosts.Host;

public class SelRunnerEngine implements HostQueueListener {
	private Hashtable<Host,HostQueue> queues = new Hashtable<Host,HostQueue>();

	public void runJob(SelRunnerJob job) {
		Iterator<SelRunnerTask> i = job.iterator();
		while (i.hasNext()) try {
			SelRunnerTask t = i.next();
			t.reset();
			this.getQueue(t.getHost()).add(t);
		} catch (SelRunnerTaskException e) {
			e.printStackTrace();
		}
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
	
	public void shutDown() {
		SelRunnerThread.shutDown();
	}
}
