package de.chipxonio.adtech.selina.gui.components;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import de.chipxonio.adtech.selina.packages.PackageList;

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
        this.setCellRenderer(new DefaultTreeCellRenderer() {
			private static final long serialVersionUID = 1302276464641623798L;

			@Override
			public Component getTreeCellRendererComponent(JTree tree, Object value,
					boolean selected, boolean expanded, boolean leaf, int row,
					boolean hasFocus) {
				Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
				if (leaf) ((JLabel) c).setIcon(new ImageIcon(getClass().getClassLoader().getResource("de/chipxonio/adtech/selrunner/resources/cog.png")));
				return c;
			}
		});
	}

	public void setList(PackageList list) {
		this.list = list;
		this.setModel(list);
	}
	
	public PackageList getList() {
		return this.list;
	}
}
