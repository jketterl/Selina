package de.chipxonio.adtech.selrunner.tests;

import java.util.Iterator;
import java.util.Vector;

import org.openqa.selenium.WebDriver;

public class TestSuite extends AbstractTest{
	private static final long serialVersionUID = -69790777830967570L;
	
	private Vector<AbstractTest> tests = new Vector<AbstractTest>();
	
	public void addTest(AbstractTest test)
	{
		test.setDriver(this.getDriver());
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
			}
		}
	}
	
	public void setDriver(WebDriver driver)
	{
		// pass on the driver to the sub-tests
		Iterator<AbstractTest> i = this.tests.iterator();
		while (i.hasNext()) i.next().setDriver(driver);
		super.setDriver(driver);
	}
}
