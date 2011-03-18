package de.chipxonio.adtech.selrunner.tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;

import de.chipxonio.adtech.selrunner.screenshots.Screenshot;

public abstract class TestCase extends AbstractTest {
	private Method currentMethod;
	
	private class ElementPresentCondition implements ExpectedCondition<Boolean> {
		private WebElement result;
		private By by;
		private WebElement container;
		public ElementPresentCondition(By by) {
			this.by = by;
		}
		public ElementPresentCondition(By by, WebElement container) {
			this(by);
			this.container = container;
		}
		@Override
		public Boolean apply(WebDriver arg0) {
			if (container == null) {
				result = arg0.findElement(by);
			} else {
				result = container.findElement(by);
			}
			return true;
		}
		public WebElement getResult() {
			return result;
		}
	}

	public void run() throws Exception {
		Method[] methods = this.getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			// execute the methods of this class that begin with "test"
			Pattern p = Pattern.compile("^test[A-Z].*");
			if (p.matcher(methods[i].getName()).matches()) try {
				currentMethod = methods[i];
				this.setUp();
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
		this.getResult().pushPass(new Pass(this.getClass(), currentMethod));
	}
	
	public void fail() {
		this.getResult().pushFailure(new Failure(this.getClass(), currentMethod));
	}
	
	public void pass(String message) {
		this.getResult().pushPass(new Pass(this.getClass(), currentMethod, message));
	}

	public void fail(String message) {
		this.getResult().pushFailure(new Failure(this.getClass(), currentMethod, message));
	}
	
	public void assertTrue(boolean b) {
		if (b) this.pass(); else this.fail();
	}
	
	public void assertTrue(boolean b, String message) {
		if (b) this.pass(message); else this.fail(message);
	}
	
	public void takeScreenshot() throws InvalidDriverException {
		if (!(this.getDriver() instanceof TakesScreenshot))
			throw new InvalidDriverException();
		this.getResult().pushScreenshot(new Screenshot(((TakesScreenshot) this.getDriver()).getScreenshotAs(OutputType.BYTES)));
	}
	
	public WebElement getElement(By by) {
		WebDriverWait w = new WebDriverWait(getDriver(), 30);
		ElementPresentCondition c = new ElementPresentCondition(by);
		try {
			w.until(c);
			return c.getResult();
		} catch (TimeoutException e) {
			return null;
		}
	}
	
	public WebElement getElement(By by, WebElement container) {
		WebDriverWait w = new WebDriverWait(getDriver(), 30);
		ElementPresentCondition c = new ElementPresentCondition(by, container);
		try {
			w.until(c);
			return c.getResult();
		} catch (TimeoutException e) {
			return null;
		}
	}
}
