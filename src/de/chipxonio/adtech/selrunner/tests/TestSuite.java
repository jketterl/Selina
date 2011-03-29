package de.chipxonio.adtech.selrunner.tests;

import java.util.Iterator;
import java.util.Vector;

public abstract class TestSuite extends AbstractTest {
	private static final long serialVersionUID = -69790777830967570L;
	
	private Vector<Class<? extends AbstractTest>> tests = new Vector<Class<? extends AbstractTest>>();
	
	public void addTest(Class<? extends AbstractTest> test)
	{
		this.tests.add(test);
	}

	@Override
	public void run() {
		Iterator<Class<? extends AbstractTest>> i = this.tests.iterator();
		while (i.hasNext()) {
			try {
				AbstractTest test = i.next().newInstance();
				test.setResult(this.getResult());
				test.setDriver(this.getDriver());
				test.run();
			} catch (InstantiationException e1) {
			} catch (IllegalAccessException e1) {
			} catch (Exception e) {
				this.getResult().pushException(e);
			}
		}
	}
}
