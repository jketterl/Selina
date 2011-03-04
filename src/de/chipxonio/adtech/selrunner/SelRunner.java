package de.chipxonio.adtech.selrunner;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.chipxonio.adtech.selrunner.engine.SelRunnerEngine;
import de.chipxonio.adtech.selrunner.gui.SelRunnerGui;

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
			gui.setEngine(engine);
			gui.setVisible(true);
		}
	}

}
