package com.azett.dirtohtml.util;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.azett.dirtohtml.DirToHTML;
import com.azett.dirtohtml.ui.UiUtils;

public class FileUtils {

	/**
	 * Gibt den absoluten Pfad zum DirToHTML-Programmverzeichnis (mit angehängtem
	 * Verzeichnisseparator) zurück.
	 * 
	 * @return der absolute Pfad
	 */
	public static String getProgramFolderPath() {
		String dirtohtmlPath = "";
		try {
			// Pfad holen, in dem DirToHTML.jar liegt
			dirtohtmlPath = new File(DirToHTML.class.getProtectionDomain().getCodeSource().getLocation().toURI())
					.getParent() + File.separator;
		} catch (Exception e) {
			// Wenn dabei etwas schiefgeht, fallen wir auf den relativen Pfad zurück
			dirtohtmlPath = "." + File.separator;
		}
		System.out.println("dirtohtmlPath: " + dirtohtmlPath);
		return dirtohtmlPath;
	}

	/**
	 * Schreibt den übergebenen String UTF8-kodiert in die übergebene Datei.
	 * 
	 * @param content
	 * @param file
	 * @return
	 */
	public static boolean writeContentToFile(String content, File file) {
		try {
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			out.write(content);
			out.close();
			return true;
		} catch (IOException ioex) {
			return false;
		}
	}

	/**
	 * Gibt den Inhalt der übergebenen Datei als String zurück.
	 * 
	 * @author azimmermann
	 * @param file
	 * @return
	 */
	public static String getFileContent(File file) {
		byte[] buffer = new byte[(int) file.length()];
		BufferedInputStream f = null;
		try {
			f = new BufferedInputStream(new FileInputStream(file));
			f.read(buffer);
		} catch (IOException e) {
			UiUtils.showErrorDialog("Error while reading " + file.getAbsolutePath(), e.getMessage());
		} finally {
			if (f != null)
				try {
					f.close();
				} catch (IOException ignored) {
				}
		}
		return new String(buffer);
	}

	/**
	 * Bereitet einen Dateipfad für Verlinkung auf.
	 * 
	 * @author azimmermann
	 * @param filePath der Pfad zur Datei
	 * @return der aufbereitete Dateipfad
	 */
	public static String cleanFilePathForLink(String filePath) {
		return filePath.replace('\\', '/');
		// .replace(" ", "%20"); // checkt der bekloppte IE nicht -.-
	}

	/**
	 * Bereinigt den übergebenen Namen, um das Anlegen einer Datei mit diesem Namen
	 * zu ermöglichen.
	 * 
	 * @author azimmermann
	 * @param name der zu bereinigende Name
	 * @return der bereinigte Name
	 */
	public static String cleanFileName(String name) {
		// Aktuell auf Windows spezialisiert, wo folgende Zeichen in Dateinamen
		// nicht erlaubt sind: /\*:?"<>|
		return name.replaceAll("[\\/\\\\\\*\\:\\?\\\"\\<\\>|]", "");
	}
}
