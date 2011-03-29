package de.chipxonio.adtech.selrunner.gui.components;

import javax.swing.JList;
import javax.swing.ListModel;

import de.chipxonio.adtech.selrunner.browsers.BrowserList;

public class BrowserLibrary extends JList {
	private static final long serialVersionUID = 1L;

	public BrowserLibrary() {
		this.setModel((ListModel) new BrowserList());
	}
}
