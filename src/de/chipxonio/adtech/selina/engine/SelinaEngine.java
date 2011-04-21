package de.chipxonio.adtech.selina.engine;

import java.util.Hashtable;
import java.util.Iterator;

import de.chipxonio.adtech.selina.hosts.Host;

public class SelinaEngine implements HostQueueListener {
	private Hashtable<Host,HostQueue> queues = new Hashtable<Host,HostQueue>();

	public void runJob(SelinaJob job) {
		Iterator<SelinaTask> i = job.iterator();
		while (i.hasNext()) try {
			SelinaTask t = i.next();
			t.reset();
			this.getQueue(t.getHost()).add(t);
		} catch (SelinaTaskException e) {
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
		SelinaThread.shutDown();
	}
}
