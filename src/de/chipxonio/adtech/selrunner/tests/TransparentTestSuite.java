package de.chipxonio.adtech.selrunner.tests;

import de.chipxonio.adtech.selrunner.packages.TestDefinition;

abstract public class TransparentTestSuite extends TestSuite {
	private TestDefinition[] testDefinitions;
	
	public TestDefinition[] getTests() {
		if (testDefinitions == null) {
			testDefinitions = new TestDefinition[this.tests.size()];
			for (int i = 0; i < this.tests.size(); i++) testDefinitions[i] = new TestDefinition(this.tests.get(i));
		}
		return testDefinitions;
	}
}
