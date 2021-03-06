package de.chipxonio.adtech.selina.tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;

import de.chipxonio.adtech.selina.results.TestCaseResult;
import de.chipxonio.adtech.selina.results.outcomes.ExceptionFailure;
import de.chipxonio.adtech.selina.results.outcomes.Failure;
import de.chipxonio.adtech.selina.results.outcomes.Pass;
import de.chipxonio.adtech.selina.screenshots.Screenshot;

public abstract class TestCase extends AbstractTest {
	private Method currentMethod;
	private TestCaseResult result;
	
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

	public void run() {
		result = new TestCaseResult(this.getTestName());
		this.getOverallResult().pushCaseResult(result);
		Method[] methods = this.getClass().getMethods();
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
				// according to the documentation, Class.getMethods() only returns public methods
				// see http://download.oracle.com/javase/1.5.0/docs/api/java/lang/Class.html#getMethods%28%29
			} catch (InvocationTargetException e) {
				if (e.getCause() instanceof Exception) {
					// i know what to do with an exception
					URL url = null;
					try {
						url = new URL(getDriver().getCurrentUrl());
					} catch (Exception e1) {
					}
					result.pushFailure(new ExceptionFailure(this.getClass(), currentMethod, url, (Exception) e.getCause()));
				} else {
					// but i don't know what to do with the rest
					System.out.println("don't know how to handle this:");
					e.printStackTrace();
				}
			} catch (Exception e) {
				System.out.println("unknown exception:");
				e.printStackTrace();
			}
		}
	}
	
	public void setUp() {
	}

	public void tearDown() {
	}

	public void pass() {
		URL url = null;
		try {
			url = new URL(getDriver().getCurrentUrl());
		} catch (MalformedURLException e1) {
		}
		this.getResult().pushPass(new Pass(this.getClass(), currentMethod, url));
	}
	
	/**
	 * @deprecated failing without a message is discouraged.
	 * @see fail(String message) instead.
	 */
	public void fail() {
		URL url = null;
		try {
			url = new URL(getDriver().getCurrentUrl());
		} catch (MalformedURLException e1) {
		}
		this.getResult().pushFailure(new Failure(this.getClass(), currentMethod, url));
	}
	
	public void fail(String message) {
		URL url = null;
		try {
			url = new URL(getDriver().getCurrentUrl());
		} catch (Exception e1) {
		}
		this.getResult().pushFailure(new Failure(this.getClass(), currentMethod, url, message));
	}
	
	/**
	 * @deprecated failing without a message is discouraged.
	 * @see fail(String message) instead.
	 */
	public void assertTrue(boolean b) {
		this.assertTrue(b, "Failed asserting that '" + b + "' is true.");
	}
	
	public void assertTrue(boolean b, String message) {
		if (b) this.pass(); else this.fail(message);
	}
	
	public void takeScreenshot() throws InvalidDriverException {
		if (!(this.getDriver() instanceof TakesScreenshot))
			throw new InvalidDriverException();
		this.getOverallResult().pushScreenshot(new Screenshot(((TakesScreenshot) this.getDriver()).getScreenshotAs(OutputType.BYTES)));
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
	
	public TestCaseResult getResult() {
		return result;
	}

	protected void resetDriver() {
		this.task.resetDriver(this);
	}
}
