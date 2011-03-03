package de.chipxonio.adtech.selrunner.tests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;


public abstract class TestCase extends AbstractTest {
	public void run() throws Exception {
		Method[] methods = this.getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			// execute the methods of this class that begin with "test"
			Pattern p = Pattern.compile("^test[A-Z].*");
			if (p.matcher(methods[i].getName()).matches()) try {
				methods[i].invoke(this, new Object[0]);
			} catch (IllegalAccessException e) {
				// NOOP
				// ignored: test methods must be public
				// TODO some kind of notification that gets passed to the user
			} catch (InvocationTargetException e) {
				if (e.getCause() instanceof Exception)
					throw (Exception) e.getCause();
				else
					e.printStackTrace();
			}
		}
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
}
