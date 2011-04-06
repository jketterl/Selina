package de.chipxonio.adtech.selrunner.tests;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.openqa.selenium.WebDriverException;

import de.chipxonio.adtech.selrunner.screenshots.MissingScreenshotException;
import de.chipxonio.adtech.selrunner.screenshots.Screenshot;
import de.chipxonio.adtech.selrunner.util.ActiveVector;

public class TestResult implements TestCaseResultListener, TableModel {
	private ActiveVector<Exception> exceptions = new ActiveVector<Exception>();
	private Vector<TestResultListener> listeners = new Vector<TestResultListener>();
	private Vector<Screenshot> screenshots = new Vector<Screenshot>();
	private Vector<TestCaseResult> results = new Vector<TestCaseResult>();
	private Vector<TableModelListener> tableListeners = new Vector<TableModelListener>();
	private long startTime = 0, endTime = 0;
	private String stringRepresentation;
	
	public TestResult() {
		this.startTime = System.currentTimeMillis();
		this.updateStringRepresentation();
	}
	
	public String toString() {
		return stringRepresentation;
	}
	
	private void updateStringRepresentation() {
		String result = "";
		if (this.startTime > 0 && this.endTime > 0) {
			result += "runtime: " + Math.round((this.endTime - this.startTime) / 1000) + "s; ";
		}
		// count passes & failures
		Iterator <TestCaseResult> i = this.results.iterator();
		int passes = 0; int failures = 0;
		while (i.hasNext()) {
			TestCaseResult r = i.next();
			passes += r.getPassCount();
			failures += r.getFailCount();
		}
		result += "passed: " + passes + ", failed: " + failures;
		if (this.exceptions.size() > 0) {
			result += ", exceptions: " + exceptions.size();
		}
		int screenshotCount = this.screenshots.size();
		if (screenshotCount > 0) result += ", screenshots: " + screenshotCount;
		stringRepresentation = result;
	}
	
	public boolean isSuccessful() {
		Iterator <TestCaseResult> i = this.results.iterator();
		boolean successful = true;
		while (i.hasNext()) successful &= i.next().isSuccessful();
		return successful && this.exceptions.isEmpty();
	}
	
	public void pushException(Exception e) {
		// e.printStackTrace();
		this.exceptions.add(e);
		if (e instanceof WebDriverException) try {
			this.screenshots.add(new Screenshot((WebDriverException) e));
		} catch (MissingScreenshotException e1) {
		}
		this.updateStringRepresentation();
		this.fireTestResultChanged();
	}
	
	public Vector<Screenshot> getScreenshots() {
		return screenshots;
	}

	public void addListener(TestResultListener l) {
		this.listeners.add(l);
	}
	
	public void removeListener(TestResultListener l) {
		this.listeners.remove(l);
	}
	
	private void fireTestResultChanged() {
		Iterator<TestResultListener> i = this.listeners.iterator();
		while (i.hasNext()) i.next().testResultChanged(this);
	}

	public void pushScreenshot(Screenshot screenshot) {
		this.screenshots.add(screenshot);
		this.updateStringRepresentation();
		this.fireTestResultChanged();
	}

	public void stopTimer() {
		this.endTime = System.currentTimeMillis();
		this.updateStringRepresentation();
		this.fireTestResultChanged();
	}
	
	public ActiveVector<Exception> getExceptions() {
		return exceptions;
	}
	
	public void pushCaseResult(TestCaseResult r) {
		this.results.add(r);
		int index = this.results.size() - 1;
		this.fireTableChanged(new TableModelEvent(this, index, index, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
		r.addListener(this);
	}

	@Override
	public void testCaseResultUpdated(TestCaseResult src) {
		this.updateStringRepresentation();
		this.fireTestResultChanged();
		this.fireTableChanged(new TableModelEvent(this, this.results.indexOf(src)));
	}

	@Override
	public void addTableModelListener(TableModelListener arg0) {
		this.tableListeners.add(arg0);
	}

	@Override
	public Class<?> getColumnClass(int arg0) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public String getColumnName(int arg0) {
		return new String[]{
			"Name of test",
			"Time",
			"Passes",
			"Failures"
		}[arg0];
	}

	@Override
	public int getRowCount() {
		return this.results.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		TestCaseResult r = this.results.get(arg0);
		switch (arg1) {
		case 0: return r.toString();
		case 1: return "";
		case 2: return r.getPassCount();
		case 3: return r.getFailCount();
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener arg0) {
		this.tableListeners.remove(arg0);
	}

	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {
		// NOOP since isCellEditable always returns false
	}
	
	private void fireTableChanged(TableModelEvent e) {
		Iterator<TableModelListener> i = tableListeners.iterator();
		while (i.hasNext()) i.next().tableChanged(e);
	}
}
