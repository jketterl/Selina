package de.chipxonio.adtech.selina.tests;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

import org.openqa.selenium.WebDriverException;

import de.chipxonio.adtech.selina.gui.components.treetable.TreeTableModel;
import de.chipxonio.adtech.selina.screenshots.MissingScreenshotException;
import de.chipxonio.adtech.selina.screenshots.Screenshot;
import de.chipxonio.adtech.selina.util.ActiveVector;

public class TestResult implements TestCaseResultListener, TreeTableModel {
	private ActiveVector<Exception> exceptions = new ActiveVector<Exception>();
	private Vector<TestResultListener> listeners = new Vector<TestResultListener>();
	private Vector<Screenshot> screenshots = new Vector<Screenshot>();
	private Vector<TestCaseResult> results = new Vector<TestCaseResult>();
	private Vector<TreeModelListener> treeListeners = new Vector<TreeModelListener>();
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
		int index = this.results.size();
		this.results.add(index, r);
		this.fireTreeNodesInserted(new TreeModelEvent(this, new Object[] { this }, new int[] { index }, new Object[] { r }));
		r.addListener(this);
	}

	@Override
	public void testCaseResultUpdated(TestCaseResult src) {
		this.updateStringRepresentation();
		this.fireTestResultChanged();
		int index = this.results.indexOf(src);
		this.fireTreeNodesChanged(new TreeModelEvent(this, new Object[] { this }, new int[] { index }, new Object[] { src }));
	}
	
	private void fireTreeNodesChanged(TreeModelEvent e) {
		Iterator <TreeModelListener> i = this.treeListeners.iterator();
		while (i.hasNext()) i.next().treeNodesChanged(e);
	}
	
	private void fireTreeNodesInserted(TreeModelEvent e) {
		Iterator <TreeModelListener> i = this.treeListeners.iterator();
		while (i.hasNext()) i.next().treeNodesInserted(e);
	}
	
	/*
	private void fireTreeStructureChanged(TreeModelEvent e) {
		Iterator <TreeModelListener> i = this.treeListeners.iterator();
		while (i.hasNext()) i.next().treeStructureChanged(e);
	}
	*/

	@Override
	public Class<?> getColumnClass(int arg0) {
		if (arg0 == 0) return TreeTableModel.class;
		return TestCaseResult.class;
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
	public void addTreeModelListener(TreeModelListener arg0) {
		this.treeListeners.add(arg0);
	}

	@Override
	public Object getChild(Object arg0, int arg1) {
		if (arg0 == this) return this.results.get(arg1);
		if (arg0 instanceof TestCaseResult) {
			TestCaseResult r = (TestCaseResult) arg0;
			return r.getFailures().get(arg1);
		}
		return null;
	}

	@Override
	public int getChildCount(Object arg0) {
		if (arg0 == this) return this.results.size();
		if (arg0 instanceof TestCaseResult) {
			TestCaseResult r = (TestCaseResult) arg0;
			return r.getFailCount();
		}
		return 0;
	}

	@Override
	public int getIndexOfChild(Object arg0, Object arg1) {
		if (arg0 == this) this.results.indexOf(arg1);
		if (arg0 instanceof TestCaseResult) {
			TestCaseResult r = (TestCaseResult) arg0;
			return r.getFailures().indexOf(arg1);
		}
		return -1;
	}

	@Override
	public Object getRoot() {
		return this;
	}

	@Override
	public boolean isLeaf(Object arg0) {
		if (arg0 == this) return false;
		if (arg0 instanceof TestCaseResult) return ((TestCaseResult) arg0).getFailCount() == 0;
		return true;
	}

	@Override
	public void removeTreeModelListener(TreeModelListener arg0) {
		this.treeListeners.remove(arg0);
	}

	@Override
	public void valueForPathChanged(TreePath arg0, Object arg1) {
	}

	@Override
	public Object getValueAt(Object node, int column) {
		return node;
	}

	@Override
	public boolean isCellEditable(Object node, int column) {
		if (getColumnClass(column) == TreeTableModel.class) return true;
		return false;
	}

	@Override
	public void setValueAt(Object aValue, Object node, int column) {
	}
}
