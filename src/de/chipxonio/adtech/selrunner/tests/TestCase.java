package de.chipxonio.adtech.selrunner.tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import de.chipxonio.adtech.selrunner.screenshots.Screenshot;

public abstract class TestCase extends AbstractTest {
	public void run() throws Exception {
		getResult().setTestClass(this.getClass());
		Method[] methods = this.getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			// execute the methods of this class that begin with "test"
			Pattern p = Pattern.compile("^test[A-Z].*");
			if (p.matcher(methods[i].getName()).matches()) try {
				this.setUp();
				getResult().setTestMethod(methods[i]);
				methods[i].invoke(this, new Object[0]);
				this.tearDown();
			} catch (IllegalAccessException e) {
				// NOOP
				// ignored: test methods must be public
				// TODO some kind of notification that gets passed to the user
			} catch (InvocationTargetException e) {
				if (e.getCause() instanceof Exception)
					// i know what to do with an exception
					throw (Exception) e.getCause();
				else
					// but i don't know what to do with the rest
					e.printStackTrace();
			}
		}
	}
	
	public void setUp() {
	}

	public void tearDown() {
	}

	public void pass() {
		this.getResult().pass();
	}
	
	public void fail() {
		this.getResult().fail();
	}
	
	public void assertTrue(boolean b) {
		if (b) this.pass(); else this.fail();
	}
	
	public void takeScreenshot() throws InvalidDriverException {
		if (!(this.getDriver() instanceof TakesScreenshot))
			throw new InvalidDriverException();
		this.getResult().pushScreenshot(new Screenshot(((TakesScreenshot) this.getDriver()).getScreenshotAs(OutputType.BYTES)));
	}
}
