package de.chipxonio.adtech.selrunner.packages;

import de.chipxonio.adtech.selrunner.tests.AbstractTest;

public class TestDefinition {
	private String name;
	private Class<AbstractTest> testClass;
	
	public TestDefinition (String name, Class<AbstractTest> testClass) {
		this.name = name;
		this.testClass = testClass;
	}
	
	public String getName() {
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
