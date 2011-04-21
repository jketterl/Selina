package de.chipxonio.adtech.selina.engine;

import java.util.Iterator;

import de.chipxonio.adtech.selina.util.ActiveVector;

abstract public class SelRunnerThread extends Thread {
	protected static ActiveVector<SelRunnerThread> runningThreads = new ActiveVector<SelRunnerThread>();
	
	public static void registerThread(SelRunnerThread thread) {
		runningThreads.add(thread);
	}
	
	public static void unregisterThread(SelRunnerThread thread) {
		runningThreads.remove(thread);
	}
	
	public static void shutDown() {
		Iterator<SelRunnerThread> i = runningThreads.iterator();
		while (i.hasNext()) i.next().terminate();
	}

	@Override
	public synchronized void start() {
		registerThread(this);
		super.start();
	}
	
	abstract public void terminate();
}
