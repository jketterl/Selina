package de.chipxonio.adtech.selina.engine;

import java.util.Iterator;

import de.chipxonio.adtech.selina.util.ActiveVector;

abstract public class SelinaThread extends Thread {
	protected static ActiveVector<SelinaThread> runningThreads = new ActiveVector<SelinaThread>();
	
	public static void registerThread(SelinaThread thread) {
		runningThreads.add(thread);
	}
	
	public static void unregisterThread(SelinaThread thread) {
		runningThreads.remove(thread);
	}
	
	public static void shutDown() {
		Iterator<SelinaThread> i = runningThreads.iterator();
		while (i.hasNext()) i.next().terminate();
	}

	@Override
	public synchronized void start() {
		registerThread(this);
		super.start();
	}
	
	abstract public void terminate();
}
