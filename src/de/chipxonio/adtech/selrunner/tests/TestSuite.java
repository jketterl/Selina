package de.chipxonio.adtech.selrunner.tests;

import java.util.Iterator;
import java.util.Vector;

public class TestSuite extends AbstractTest{
	private static final long serialVersionUID = -69790777830967570L;
	
	private Vector<AbstractTest> tests = new Vector<AbstractTest>();
	
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
			try {
				test.run();
			} catch (Exception e) {
				this.getResult().pushException(e);
				e.printStackTrace();
			}
		}
	}
}
