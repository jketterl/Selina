package de.chipxonio.adtech.selina;

import java.io.File;
import java.util.prefs.Preferences;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.chipxonio.adtech.selina.engine.SelRunnerEngine;
import de.chipxonio.adtech.selina.engine.SelRunnerJob;
import de.chipxonio.adtech.selina.gui.SelinaGui;
import de.chipxonio.adtech.selina.library.Library;
import de.chipxonio.adtech.selina.library.LocalLibrary;
import de.chipxonio.adtech.selina.packages.PackageLoader;

public class Selina {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final SelRunnerEngine engine = new SelRunnerEngine();
		Library l = new LocalLibrary(Preferences.userRoot().node("SelRunner").node("library"));
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
			SelRunnerJob job = SelRunnerJob.loadFromFile(new File(args[0]));
			engine.runJob(job);
		}
	}

	public final static String version = "0.1.0";
	public final static String versionDate = "2010-03-17";
}
