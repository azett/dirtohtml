package com.azett.dirtohtml;

import java.io.File;

import com.azett.dirtohtml.config.Configuration;
import com.azett.dirtohtml.config.Defaults;
import com.azett.dirtohtml.config.Language;
import com.azett.dirtohtml.processing.controller.DirToHTMLController;
import com.azett.dirtohtml.ui.MainWindow;

/**
 * @author azimmermann
 */
public class DirToHTML {

	/**
	 * Startet das Programm.
	 * 
	 * @author azimmermann
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// Als erstes: Prüfen, ob Programmumgebung intakt ist
		DirToHTML.checkEnvironment();

		// Config und Sprache initialisieren
		Configuration config = Configuration.getConfiguration();
		Language lang = new Language();
		config.setLanguage(lang);

		// TODO: Argumente prüfen, ggfs. Hilfetext ausgeben
		// Anzahl Argumente
		switch (args.length) {
		case 0:
			// Aufruf des UIs
			config.setOutputType(Defaults.OUTPUT_TYPE_SWING);
			new MainWindow();
			break;
		case 1:
		case 2:
			printParameterHelp();
			break;
		case 3:
			File src = new File(args[1]);
			File dest = new File(args[2]);
			// Console-Output
			if (args[0].equals("-console")) {
				config.setOutputType(Defaults.OUTPUT_TYPE_CONSOLE);
				DirToHTMLController processing = new DirToHTMLController(src, dest);
				Thread t = new Thread(processing);
				t.start();
			}
			// Kein Output
			else if (args[0].equals("-silent")) {
				config.setOutputType(Defaults.OUTPUT_TYPE_NONE);
				DirToHTMLController processing = new DirToHTMLController(src, dest);
				Thread t = new Thread(processing);
				t.start();
			}
			// unbekannter Parameter
			else {
				printParameterHelp();
			}
			break;

		default:
			printParameterHelp();
			break;
		}
	}

	private static void checkEnvironment() throws Exception {
		System.out.println(Defaults.LANGUAGE_DIR);
		if (!Defaults.LANGUAGE_DIR.exists()) {
			throw new Exception("Kann Verzeichnis " + Defaults.LANGUAGE_DIR + " nicht finden");
		}
	}

	/**
	 * Gibt die Parameter auf Konsole aus.
	 */
	private static void printParameterHelp() {
		System.err.println(Defaults.PROGRAM_NAME + " " + Defaults.PROGRAM_VERSION);
		System.err.println("Usage: java -jar DirToHTML.jar [-TYPE SRC DEST]");
		System.err.println();
		System.err.println(
				"Called without arguments, the graphical UI will open. Use the following arguments to supress the GUI:");
		System.err.println("TYPE");
		System.err.println("    silent");
		System.err.println("        - no program output at all");
		System.err.println("    console");
		System.err.println("        - program output on standard console");
		System.err.println();
		System.err.println("SRC");
		System.err.println("    The source directory to process.");
		System.err.println();
		System.err.println("DEST");
		System.err.println("    The destination HTML file to store the result in.");
	}
}
