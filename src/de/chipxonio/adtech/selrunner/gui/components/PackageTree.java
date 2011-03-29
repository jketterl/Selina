package de.chipxonio.adtech.selrunner.gui.components;

import javax.swing.JTree;

import de.chipxonio.adtech.selrunner.packages.PackageList;

public class PackageTree extends JTree {
	private static final long serialVersionUID = -3226026470869291085L;
	private PackageList list;  //  @jve:decl-index=0:
	
	public PackageTree() {
		super();
		initialize();
	}

	public PackageTree(PackageList list) {
		super();
		initialize();
		this.setList(list);
	}
	
	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setRootVisible(false);
	}

	public void setList(PackageList list) {
		this.list = list;
		this.setModel(list);
	}
	
	public PackageList getList() {
		return this.list;
	}
}
