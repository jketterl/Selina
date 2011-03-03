package de.chipxonio.adtech.selrunner.engine;

import de.chipxonio.adtech.selrunner.hosts.Host;
import de.chipxonio.adtech.selrunner.testingthread.TestingThread;
import de.chipxonio.adtech.selrunner.tests.AbstractTest;

public class SelRunnerTask {
	private Host host;
	private AbstractTest test;
	
	public Host getHost() {
		return host;
	}
	public void setHost(Host host) {
		this.host = host;
	}
	public AbstractTest getTest() {
		return test;
	}
	public void setTest(AbstractTest test) {
		this.test = test;
	}
	
	public void run()
	{
		TestingThread t = new TestingThread(this.getHost(), this.getTest());
		/*
		t.addListener(new TestingThreadListener() {
			@Override
			public void testingComplete(TestResult result) {
				getResultTextPane().setText(result.toString());
			}
		});
		*/
		t.start();
	}
}
