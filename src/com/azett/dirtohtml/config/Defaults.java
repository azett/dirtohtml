package com.azett.dirtohtml.config;

import java.io.File;

import com.azett.dirtohtml.util.FileUtils;

/**
 * Globale Defaultwerte
 * 
 * @author azimmermann
 */
public class Defaults {

	/**
	 * Konfigurationsverzeichnis
	 */
	public static final File DATA_DIR = new File(FileUtils.getProgramFolderPath() + "data");
	/**
	 * Konfigurationsdatei
	 */
	public static final File CONFIG_FILE = new File(DATA_DIR, "dirtohtml.properties");
	/**
	 * CSS-Templatedatei
	 */
	public static final File CSS_TEMPLATE_FILE = new File(DATA_DIR, "template.css");
	/**
	 * HTML-Templatedatei
	 */
	public static final File HTML_TEMPLATE_FILE = new File(DATA_DIR, "template.html");
	/**
	 * JS-Templatedatei
	 */
	public static final File JS_TEMPLATE_FILE = new File(DATA_DIR, "template.js");
	/**
	 * Sprachverzeichnis
	 */
	public static final File LANGUAGE_DIR = new File(FileUtils.getProgramFolderPath() + "lang");
	/**
	 * Extension der Einstellungsdatei
	 */
	public static final String PROPFILE_EXT = ".properties";

	/**
	 * Name des Programms
	 */
	public static final String PROGRAM_NAME = "DirToHTML";
	/**
	 * Programmversion
	 */
	public static final String PROGRAM_VERSION = "1.0.1";
	/**
	 * Programmautor
	 */
	public static final String AUTHOR_NAME = "Arvid Zimmermann";
	/**
	 * Website des Programmautors
	 */
	public static final String AUTHOR_WEBSITE = "http://www.arvidzimmermann.de";
	/**
	 * Website des Programmautors
	 */
	public static final String AUTHOR_WEBSITE_SHORT = "arvidzimmermann.de";

	/**
	 * Ausgabe-Varianten
	 */
	// Ohne Output ("silent")
	public static final int OUTPUT_TYPE_NONE = 0;
	// Output in die Standard-Konsole
	public static final int OUTPUT_TYPE_CONSOLE = 1;
	// Swing-GUI
	public static final int OUTPUT_TYPE_SWING = 2;
}
