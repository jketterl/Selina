package de.chipxonio.adtech.selina.tests;

import java.util.Iterator;
import java.util.Vector;

import de.chipxonio.adtech.selina.tests.outcomes.Failure;
import de.chipxonio.adtech.selina.tests.outcomes.Pass;
import de.chipxonio.adtech.selina.util.ActiveVector;

public class TestCaseResult {
	private String name;
	private ActiveVector<Pass> passes = new ActiveVector<Pass>();;
	private ActiveVector<Failure> failures = new ActiveVector<Failure>();
	private Vector<TestCaseResultListener> listeners = new Vector<TestCaseResultListener>();
	
	public TestCaseResult(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
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
