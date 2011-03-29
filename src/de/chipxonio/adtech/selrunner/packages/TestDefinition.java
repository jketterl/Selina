package de.chipxonio.adtech.selrunner.packages;

import de.chipxonio.adtech.selrunner.tests.AbstractTest;

public class TestDefinition {
	private String name;
	private Class<? extends AbstractTest> testClass;
	private AbstractTest instance;
	
	public TestDefinition (Class<? extends AbstractTest> testClass) {
		this.testClass = testClass;
	}
	
	public String getName() {
		if (this.name == null) {
			try {
				AbstractTest test = this.getInstance();
				this.name = test.getTestName();
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
		}
		return this.name;
	}
	
	public String toString() {
		return this.getName();
	}
	
	public Class<? extends AbstractTest> getTestClass() {
		return this.testClass;
	}

	public AbstractTest getInstance() throws InstantiationException, IllegalAccessException {
		if (instance == null) {
			instance = this.getTestClass().newInstance();
		}
		return instance;	
	}
}
