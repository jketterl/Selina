package de.chipxonio.adtech.selrunner.packages;

import java.lang.reflect.InvocationTargetException;

import de.chipxonio.adtech.selrunner.tests.AbstractTest;

public class TestDefinition {
	private String name;
	private Class<AbstractTest> testClass;
	
	public TestDefinition (Class<AbstractTest> testClass) {
		this.testClass = testClass;
	}
	
	public String getName() {
		if (this.name == null) {
			try {
				this.name = (String) this.testClass.getDeclaredMethod("getName", new Class<?>[]{}).invoke(null, new Object[]{});
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				this.name = testClass.getName();
			}
		}
		return this.name;
	}
	
	public String toString() {
		return this.getName();
	}
	
	public Class<AbstractTest> getTestClass() {
		return this.testClass;
	}

	public AbstractTest getInstance() throws InstantiationException, IllegalAccessException {
		return this.getTestClass().newInstance();
	}
}
