package de.chipxonio.adtech.selrunner.tests;

import java.util.Iterator;
import java.util.Vector;

public class TestSuite implements TestInterface{
	private static final long serialVersionUID = -69790777830967570L;
	
	private Vector<TestInterface> tests;

	@Override
	public TestResult run() {
		Iterator<TestInterface> i = this.tests.iterator();
		while (i.hasNext()) i.next().run();
		return new TestResult();
	}
}
