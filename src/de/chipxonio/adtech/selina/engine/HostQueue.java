package de.chipxonio.adtech.selina.engine;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Vector;

import de.chipxonio.adtech.selina.hosts.Host;

public class HostQueue extends SelinaThread {
	private boolean toBeStopped = false;
	private Vector<HostQueueListener> listeners = new Vector<HostQueueListener>();
	private Host host;
	private boolean sleeping = false;
	private Queue<SelinaTask> tasks = new AbstractQueue<SelinaTask>() {
		private LinkedList<SelinaTask> list = new LinkedList<SelinaTask>();
		
		@Override
		public boolean offer(SelinaTask arg0) {
			if (arg0 == null) return false;
			return this.list.add(arg0);
		}

		@Override
		public SelinaTask peek() {
			try {
				return this.list.getFirst();
			} catch (NoSuchElementException e) {
				return null;
			}
		}

		@Override
		public SelinaTask poll() {
			try {
				return this.list.removeFirst();
			} catch (NoSuchElementException e) {
				return null;
			}
		}

		@Override
		public Iterator<SelinaTask> iterator() {
			return this.list.iterator();
		}

		@Override
		public int size() {
			return this.list.size();
		}
	};
	
	public HostQueue(Host host) {
		this.host = host;
		this.start();
	}
	
	public void add(SelinaTask task) {
		this.tasks.add(task);
		this.interrupt();
	}
	
	public void run() {
		this.fireStatusChanged(new HostQueueEvent(this, HostQueueEvent.RUNNING | HostQueueEvent.INACTIVE));
		while(!this.toBeStopped) {
			SelinaTask task = this.tasks.poll();
			if (task == null) {
				try {
					this.sleeping = true;
					Thread.sleep(60000);
					this.terminate();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				this.sleeping = false;
			} else try {
				// clear interruption state (this helps with the screenshots, they must not be interrupted);
				interrupted();
				this.fireStatusChanged(new HostQueueEvent(this, HostQueueEvent.RUNNING | HostQueueEvent.ACTIVE));
				task.run();
				this.fireStatusChanged(new HostQueueEvent(this, HostQueueEvent.RUNNING | HostQueueEvent.INACTIVE));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.fireStatusChanged(new HostQueueEvent(this, HostQueueEvent.STOPPED | HostQueueEvent.INACTIVE));
	}
	
	@Override
	public void terminate(){
		if (!this.isAlive()) return;
		this.toBeStopped = true;
		this.interrupt();
	}
	
	public void addListener(HostQueueListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(HostQueueListener listener) {
		this.listeners.remove(listener);
	}
	
	private void fireStatusChanged(HostQueueEvent e) {
		Iterator<HostQueueListener> i = this.listeners.iterator();
		while (i.hasNext()) {
			i.next().statusChanged(e);
		}
	}
	
	public Host getHost() {
		return this.host;
	}

	@Override
	public void interrupt() {
		if (!this.sleeping) return;
		super.interrupt();
	}
}
