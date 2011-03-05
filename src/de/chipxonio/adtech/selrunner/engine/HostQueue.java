package de.chipxonio.adtech.selrunner.engine;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import de.chipxonio.adtech.selrunner.hosts.Host;

public class HostQueue extends Thread {
	private boolean toBeStopped = false;
	//private Host host;
	private Queue<SelRunnerTask> tasks = new AbstractQueue<SelRunnerTask>() {
		private LinkedList<SelRunnerTask> list = new LinkedList<SelRunnerTask>();
		
		@Override
		public boolean offer(SelRunnerTask arg0) {
			if (arg0 == null) return false;
			return this.list.add(arg0);
		}

		@Override
		public SelRunnerTask peek() {
			try {
				return this.list.getFirst();
			} catch (NoSuchElementException e) {
				return null;
			}
		}

		@Override
		public SelRunnerTask poll() {
			try {
				return this.list.removeFirst();
			} catch (NoSuchElementException e) {
				return null;
			}
		}

		@Override
		public Iterator<SelRunnerTask> iterator() {
			return this.list.iterator();
		}

		@Override
		public int size() {
			return this.list.size();
		}
	};
	
	public HostQueue(Host host) {
		//this.host = host;
		this.start();
	}
	
	public void add(SelRunnerTask task) {
		this.tasks.add(task);
		if (this.isAlive()) this.interrupt();
	}
	
	public void run() {
		while(!this.toBeStopped) {
			SelRunnerTask task = this.tasks.poll();
			if (task == null) {
				try {
					Thread.sleep(60000);
					this.terminate();
				} catch (InterruptedException e) {
				}
			} else {
				task.run();
			}
		}
	}
	
	public void terminate(){
		if (!this.isAlive()) return;
		this.toBeStopped = true;
		this.interrupt();
	}
}
