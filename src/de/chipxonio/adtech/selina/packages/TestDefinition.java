package de.chipxonio.adtech.selina.packages;

import java.io.Serializable;

import de.chipxonio.adtech.selina.tests.AbstractTest;

public class TestDefinition implements Serializable {
	private static final long serialVersionUID = -8772866525035765978L;
	private String name;
	private Class<? extends AbstractTest> testClass;
	private AbstractTest sampleInstance;
	
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
		return this.getTestClass().newInstance();
	}
	
	public AbstractTest getSampleInstance() throws InstantiationException, IllegalAccessException {
		if (sampleInstance == null) {
			sampleInstance = this.getInstance();
		}
		return sampleInstance;	
	}
}
