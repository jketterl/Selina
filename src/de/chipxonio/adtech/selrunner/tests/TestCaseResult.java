package de.chipxonio.adtech.selrunner.tests;

import java.util.Iterator;
import java.util.Vector;

import de.chipxonio.adtech.selrunner.util.ActiveVector;

public class TestCaseResult {
	private Class<? extends TestCase> cls;
	private ActiveVector<Pass> passes = new ActiveVector<Pass>();;
	private ActiveVector<Failure> failures = new ActiveVector<Failure>();
	private Vector<TestCaseResultListener> listeners = new Vector<TestCaseResultListener>();
	
	public TestCaseResult(Class<? extends TestCase> cls) {
		this.cls = cls;
	}
	
	public String toString() {
		return this.cls.getSimpleName();
	}

	public ActiveVector<Pass> getPasses() {
		return passes;
	}

	public ActiveVector<Failure> getFailures() {
		return failures;
	}

	public void pushPass(Pass p) {
		this.passes.add(p);
		this.fireTestCaseResultUpdated();
	}
	
	public void pushFailure(Failure f) {
		this.failures.add(f);
		this.fireTestCaseResultUpdated();
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
	
	public void addListener(TestCaseResultListener l) {
		this.listeners.add(l);
	}
	
	public void removeListener(TestCaseResultListener l) {
		this.listeners.remove(l);
	}
	
	private void fireTestCaseResultUpdated() {
		Iterator<TestCaseResultListener> i = this.listeners.iterator();
		while (i.hasNext()) i.next().testCaseResultUpdated(this);
	}
}
