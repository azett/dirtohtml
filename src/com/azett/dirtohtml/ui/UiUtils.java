package com.azett.dirtohtml.ui;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.azett.dirtohtml.config.Configuration;
import com.azett.dirtohtml.config.Defaults;
import com.azett.dirtohtml.util.FileComparator;

/**
 * @author azimmermann
 */
public class UiUtils {

	/**
	 * Zeigt den Sprachauswahl-Dialog und gibt die gewählte Sprache zurück.
	 * 
	 * @author azimmermann
	 * @return
	 */
	public static String showLanguageDialog(boolean writeToConfig) {
		// System-UI holen
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		// Vorhandene Sprachdateien einlesen
		HashMap langMap = new HashMap();
		Properties currentLang = new Properties();

		File[] fileList = Defaults.LANGUAGE_DIR.listFiles();
		Arrays.sort(fileList, new FileComparator());
		Object[] possibilities = new Object[fileList.length];
		for (int i = 0; i < fileList.length; i++) {
			try {
				currentLang.load(new FileInputStream(fileList[i]));
			} catch (IOException e) {
				showErrorDialog("Error!", "Error reading file " + fileList[i]);
				System.exit(0);
			}
			langMap.put(currentLang.get("lang.name"),
					fileList[i].getName().substring(0, fileList[i].getName().lastIndexOf('.')));
			possibilities[i] = currentLang.get("lang.name");
		}

		String s = (String) JOptionPane.showInputDialog(null,
				"<html><b>Welcome to " + Defaults.PROGRAM_NAME + "!</b></html>\n\nPlease select your language:",
				Defaults.PROGRAM_NAME, JOptionPane.QUESTION_MESSAGE, null, possibilities, possibilities[0]);

		// If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			if (writeToConfig) {
				Configuration.getConfiguration().setString("language", langMap.get(s).toString());
				Configuration.getConfiguration().saveToFile();
			}
			return langMap.get(s).toString();
		}

		else {
			System.exit(0);
			return "";
		}
	}

	/**
	 * Zeigt einen Fehlerdialog an.
	 * 
	 * @author azimmermann
	 * @param title
	 * @param msg
	 */
	public static void showErrorDialog(String title, String msg) {
		showErrorDialog(null, title, msg);
	}

	/**
	 * Zeigt einen Fehlerdialog auf dem übergebenen Parent-{@link Component} an.
	 * 
	 * @author azimmermann
	 * @param parent
	 * @param title
	 * @param msg
	 */
	public static void showErrorDialog(Component parent, String title, String msg) {
		JOptionPane.showMessageDialog(parent, msg, title, JOptionPane.ERROR_MESSAGE);
	}

}
