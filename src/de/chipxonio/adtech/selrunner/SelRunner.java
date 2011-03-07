package de.chipxonio.adtech.selrunner;

import java.util.prefs.Preferences;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.chipxonio.adtech.library.Library;
import de.chipxonio.adtech.library.LocalLibrary;
import de.chipxonio.adtech.selrunner.engine.SelRunnerEngine;
import de.chipxonio.adtech.selrunner.gui.SelRunnerGui;
import de.chipxonio.adtech.selrunner.packages.PackageLoader;

public class SelRunner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SelRunnerEngine engine = new SelRunnerEngine();
		if (true) {
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
			SelRunnerGui gui = new SelRunnerGui();
			Library l = new LocalLibrary(Preferences.userRoot().node("library"));
			PackageLoader.getSharedInstance().setPackageList(l.getPackages());
			gui.setEngine(engine);
			gui.setLibrary(l);
			gui.setVisible(true);
		}
	}

}
