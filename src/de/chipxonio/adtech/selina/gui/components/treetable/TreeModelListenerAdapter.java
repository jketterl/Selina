package de.chipxonio.adtech.selina.gui.components.treetable;

import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

public class TreeModelListenerAdapter implements TreeModelListener {
	private JTree tree;
	private TreeTableModelAdapter adapter;
	
	public TreeModelListenerAdapter(JTree tree, TreeTableModelAdapter adapter) {
		this.tree = tree;
		this.adapter = adapter;
	}
	
	@Override
	public void treeNodesChanged(TreeModelEvent e) {
		TreePath p = e.getTreePath();
		Object[] children = e.getChildren();
		for (int i = 0; i < children.length; i++) {
			int index = tree.getRowForPath(p.pathByAddingChild(children[i]));
			if (index >= 0) adapter.fireTableRowsUpdated(index, index);
		}
	}

	@Override
	public void treeNodesInserted(TreeModelEvent e) {
		TreePath p = e.getTreePath();
		Object[] children = e.getChildren();
		for (int i = 0; i < children.length; i++) {
			int index = tree.getRowForPath(p.pathByAddingChild(children[i]));
			if (index >= 0) adapter.fireTableRowsInserted(index, index);
		}
	}

	@Override
	public void treeNodesRemoved(TreeModelEvent e) {
		TreePath p = e.getTreePath();
		Object[] children = e.getChildren();
		for (int i = 0; i < children.length; i++) {
			int index = tree.getRowForPath(p.pathByAddingChild(children[i]));
			if (index >= 0) adapter.fireTableRowsDeleted(index, index);
		}
	}

	@Override
	public void treeStructureChanged(TreeModelEvent e) {
		adapter.fireTableDataChanged();
	}
}
