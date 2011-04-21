package de.chipxonio.adtech.selina;

import java.io.File;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.chipxonio.adtech.selina.engine.SelinaEngine;
import de.chipxonio.adtech.selina.engine.SelinaJob;
import de.chipxonio.adtech.selina.gui.SelinaGui;
import de.chipxonio.adtech.selina.library.Library;
import de.chipxonio.adtech.selina.library.LocalLibrary;
import de.chipxonio.adtech.selina.packages.PackageLoader;

public class Selina {
	public static final void copyNode(Preferences source, Preferences target) {
		try {
			String[] keys = source.keys();
			for (int i = 0; i < keys.length; i++) {
				target.put(keys[i], source.get(keys[i], null));
			}
			String[] children = source.childrenNames();
			for (int i = 0; i < children.length; i++) {
				copyNode(source.node(children[i]), target.node(children[i]));
			}
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final SelinaEngine engine = new SelinaEngine();
		Preferences root = Preferences.userRoot();
		try {
			if (root.nodeExists("SelRunner")) {
				if (!root.nodeExists("Selina")) {
					copyNode(root.node("SelRunner"), root.node("Selina"));
				}
				root.node("SelRunner").removeNode();
			}
		} catch (BackingStoreException e) {
			e.printStackTrace();
		}
		Library l = new LocalLibrary(root.node("Selina").node("library"));
		PackageLoader.getSharedInstance().setPackageList(l.getPackageList());
		if (args.length == 0) {
			try {
				UIManager.setLookAndFeel("com.jgoodies.looks.plastic.PlasticXPLookAndFeel");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}
			SelinaGui gui = new SelinaGui();
			gui.addWindowListener(new java.awt.event.WindowAdapter() {
	        	public void windowClosed(java.awt.event.WindowEvent e) {
	        		engine.shutDown();
	        	}
	        });
			gui.setEngine(engine);
			gui.setLibrary(l);
			gui.setVisible(true);
		} else {
			SelinaJob job = SelinaJob.loadFromFile(new File(args[0]));
			engine.runJob(job);
		}
	}

	public final static String version = "0.2.0";
	public final static String versionDate = "2010-04-21";
}
