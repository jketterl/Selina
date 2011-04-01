package de.chipxonio.adtech.selrunner.tests;

import de.chipxonio.adtech.selrunner.util.ActiveVector;

public class TestCaseResult {
	private Class<? extends TestCase> cls;
	private ActiveVector<Pass> passes = new ActiveVector<Pass>();;
	private ActiveVector<Failure> failures = new ActiveVector<Failure>();
	
	public TestCaseResult(Class<? extends TestCase> cls) {
		this.cls = cls;
	}
	
	public String toString() {
		return this.cls.getName();
	}

	public ActiveVector<Pass> getPasses() {
		return passes;
	}

	public ActiveVector<Failure> getFailures() {
		return failures;
	}

	public void pushPass(Pass p) {
		this.passes.add(p);
		//this.fireTestResultChanged();
	}
	
	public void pushFailure(Failure f) {
		this.failures.add(f);
		//this.fireTestResultChanged();
	}
	
	public int getPassCount() {
		return this.passes.size();
	}
	
	public int getFailCount() {
		return this.failures.size();
	}
	
	public boolean isSuccessful() {
		return this.failures.isEmpty();
	}
}
