package de.chipxonio.adtech.selina.packages;

import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;
import java.util.Vector;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import de.chipxonio.adtech.selina.tests.TransparentTestSuite;
import de.chipxonio.adtech.selina.util.ActiveVector;

public class PackageList extends ActiveVector<Package> implements TreeModel {
	private static final long serialVersionUID = -6282910026609075670L;
	private Vector<TreeModelListener> treeListeners = new Vector<TreeModelListener>();
	private Preferences preferences;
	
	public PackageList() {
		super();
	}
	
	public PackageList(Preferences prefs) {
		this.preferences = prefs;
		try {
			String[] keys = this.preferences.childrenNames();
			for (int i = 0; i < keys.length; i++) {
				try {
					this.add(new Package(this.preferences.node(keys[i])));
				} catch (IOException e) {
				} catch (PackageLoaderException e) {
				}
			}
		} catch (BackingStoreException e) {
		}
	}

	@Override
	public synchronized boolean add(Package e) {
		boolean ret = super.add(e);
		if (ret) {
			if (this.hasPreferences() && !e.hasPreferences()) {
				e.setPreferences(this.preferences.node(UUID.nameUUIDFromBytes(e.toString().getBytes()).toString()));
			}
			//fireTreeNodesInserted(new TreeModelEvent(this, new TreePath(new Object[]{this}), indices, objects));
			this.fireTreeStructureChanged(new TreeModelEvent(this, new TreePath(this)));
		}
		return ret;
	}

	private boolean hasPreferences() {
		return this.preferences != null;
	}

	@Override
	public boolean remove(Object o) {
		Package p = (Package) o;
		if (p.hasPreferences()) try {
			p.getPreferences().removeNode();
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		boolean ret = super.remove(o);
		if (ret) {
			//this.fireTreeNodesRemoved(new TreeModelEvent(this, new TreePath(getRoot()), indices, objects));
			this.fireTreeStructureChanged(new TreeModelEvent(this, new TreePath(this)));
		}
		return ret;
	}
	
	@Override
	public void addTreeModelListener(TreeModelListener arg0) {
		this.treeListeners.add(arg0);
	}

	@Override
	public Object getChild(Object arg0, int arg1) {
		if (arg0 == this)
			return this.get(arg1);
		else if (arg0 instanceof Package || arg0 instanceof TestDefinition) {
			try {
				TestDefinition test;
				if (arg0 instanceof Package) {
					test = ((Package) arg0).getRootTest();
				} else {
					test = (TestDefinition) arg0;
				}
				if (TransparentTestSuite.class.isAssignableFrom(test.getTestClass())) {
					return ((TransparentTestSuite) test.getSampleInstance()).getTests()[arg1];
				}
			} catch (PackageLoaderException e) {
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
			return null;
		} else
			return null;
	}

	@Override
	public int getChildCount(Object arg0) {
		if (arg0 == this)
			return this.size();
		else if (arg0 instanceof Package || arg0 instanceof TestDefinition) {
			try {
				TestDefinition test;
				if (arg0 instanceof Package) {
					test = ((Package) arg0).getRootTest();
				} else {
					test = (TestDefinition) arg0;
				}
				if (TransparentTestSuite.class.isAssignableFrom(test.getTestClass())) {
					return ((TransparentTestSuite) test.getSampleInstance()).getTests().length;
				}
			} catch (PackageLoaderException e) {
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
			return 0;
		} else
			return 0;
	}

	@Override
	public int getIndexOfChild(Object arg0, Object arg1) {
		if (arg0 == this)
			return this.indexOf(arg1);
		else if (arg0 instanceof Package || arg0 instanceof TestDefinition) {
			try {
				TestDefinition test;
				if (arg0 instanceof Package) {
					test = ((Package) arg0).getRootTest();
				} else {
					test = (TestDefinition) arg0;
				}
				if (TransparentTestSuite.class.isAssignableFrom(test.getTestClass())) {
					System.out.println("searching " + arg1 + " in " + test);
					TestDefinition[] tests = ((TransparentTestSuite) test.getSampleInstance()).getTests();
					for (int i = 0; i < tests.length; i++) {
						if (tests[i] == arg1) return i;
					}
				}
			} catch (PackageLoaderException e) {
			} catch (InstantiationException e) {
			} catch (IllegalAccessException e) {
			}
			System.out.println("not found");
			return -1;
		} else
			return -1;
	}

	@Override
	public Object getRoot() {
		return this;
	}

	@Override
	public boolean isLeaf(Object arg0) {
		if (arg0 == this)
			return false;
		else if (arg0 instanceof Package) {
			try {
				TestDefinition test = ((Package) arg0).getRootTest();
				return !TransparentTestSuite.class.isAssignableFrom(test.getTestClass());
			} catch (PackageLoaderException e) {
			}
			return true;
		} else if (arg0 instanceof TestDefinition) {
			return !TransparentTestSuite.class.isAssignableFrom(((TestDefinition) arg0).getTestClass());
		} else
			return true;
	}

	@Override
	public void removeTreeModelListener(TreeModelListener arg0) {
		this.treeListeners.remove(arg0);
	}

	@Override
	public void valueForPathChanged(TreePath arg0, Object arg1) {
	}
	
	/*
	private void fireTreeNodesInserted(TreeModelEvent e) {
		Iterator<TreeModelListener> i = this.treeListeners.iterator();
		while (i.hasNext()) i.next().treeNodesInserted(e);
	}
	
	private void fireTreeNodesRemoved(TreeModelEvent e) {
		Iterator<TreeModelListener> i = this.treeListeners.iterator();
		while (i.hasNext()) i.next().treeNodesRemoved(e);
	}
	*/
	
	private void fireTreeStructureChanged(TreeModelEvent e) {
		Iterator<TreeModelListener> i = this.treeListeners.iterator();
		while (i.hasNext()) i.next().treeStructureChanged(e);
	}

	@Override
	public synchronized String toString() {
		return "Package List";
	}
}
