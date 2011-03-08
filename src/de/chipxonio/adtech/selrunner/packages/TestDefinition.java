package de.chipxonio.adtech.selrunner.packages;

import java.util.Vector;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import de.chipxonio.adtech.selrunner.tests.AbstractTest;

public class TestDefinition implements TreeModel {
	private String name;
	private Class<AbstractTest> testClass;
	private Vector<TreeModelListener> listeners = new Vector<TreeModelListener>();
	
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

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		this.listeners.add(l);
	}

	@Override
	public Object getChild(Object parent, int index) {
		if (parent == this)
			return null;
		else
			return ((TreeModel) parent).getChild(parent, index);
	}

	@Override
	public int getChildCount(Object parent) {
		if (parent == this)
			return 0;
		else
			return ((TreeModel) parent).getChildCount(parent);
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if (parent == this)
			return 0;
		else
			return ((TreeModel) parent).getIndexOfChild(parent, child);
	}

	@Override
	public Object getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isLeaf(Object node) {
		if (node == this)
			return true;
		else
			return ((TreeModel) node).isLeaf(node);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		this.listeners.remove(l);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
	}

	public AbstractTest getInstance() throws InstantiationException, IllegalAccessException {
		return this.getTestClass().newInstance();
	}
}
