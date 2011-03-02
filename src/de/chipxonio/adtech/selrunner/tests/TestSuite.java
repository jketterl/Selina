package de.chipxonio.adtech.selrunner.tests;

import java.util.Iterator;
import java.util.Vector;

public class TestSuite extends AbstractTest{
	private static final long serialVersionUID = -69790777830967570L;
	
	private Vector<AbstractTest> tests;
	
	public void addTest(AbstractTest test)
	{
		this.tests.add(test);
	}

	@Override
	public void run() {
		Iterator<AbstractTest> i = this.tests.iterator();
		while (i.hasNext()) {
			AbstractTest test = i.next();
			test.setResult(this.getResult());
			test.run();
		}
	}
}
