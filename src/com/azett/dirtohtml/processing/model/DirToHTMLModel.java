package com.azett.dirtohtml.processing.model;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

import com.azett.dirtohtml.config.Configuration;
import com.azett.dirtohtml.config.Defaults;
import com.azett.dirtohtml.config.Language;
import com.azett.dirtohtml.util.DirToHTMLUtils;
import com.azett.dirtohtml.util.FileComparator;
import com.azett.dirtohtml.util.FileUtils;

import de.vdheide.mp3.MP3File;

/**
 * Das Model für das MVC-Pattern
 * 
 * @author azimmermann
 * 
 */
public class DirToHTMLModel extends AbstractModel {

	// Config und Sprache
	private Configuration config = Configuration.getConfiguration();
	private Language language = config.getLanguage();

	// Optionen
	private boolean createMultiDocument;
	private boolean displayHidden;
	private boolean displayFullPath;
	private boolean displayTooltip;
	private boolean displayProgramInfo;
	private boolean displayFileSize;
	private boolean displayLastChange;
	private boolean displayMp3Bitrate;
	private boolean createLinks;
	private boolean createLinksAsTargetBlank;

	// globale Werte
	private File sourceDirectory;
	private File destinationFile;
	private long numberOfDirs;
	private long numberOfFiles;
	private long duration;
	private File multidocdir;

	// Abbruch-Flag
	private boolean cancel;

	/**
	 * @param sourceDirectory
	 * @param destinationFile
	 */
	public DirToHTMLModel(File sourceDirectory, File destinationFile) {

		// Werte zuweisen
		this.sourceDirectory = sourceDirectory;
		this.destinationFile = destinationFile;

		this.createMultiDocument = this.config.getBoolean(Configuration.CREATE_MULTI_DOCUMENT);
		this.displayHidden = this.config.getBoolean(Configuration.DISPLAY_HIDDEN);
		this.displayFullPath = this.config.getBoolean(Configuration.DISPLAY_FULL_PATH);
		this.displayTooltip = this.config.getBoolean(Configuration.DISPLAY_TOOLTIP);
		this.displayProgramInfo = this.config.getBoolean(Configuration.DISPLAY_PROGRAM_INFO);
		this.displayFileSize = this.config.getBoolean(Configuration.DISPLAY_FILE_SIZE);
		this.displayLastChange = this.config.getBoolean(Configuration.DISPLAY_LAST_CHANGE);
		this.displayMp3Bitrate = this.config.getBoolean(Configuration.DISPLAY_MP3_BITRATE);
		this.createLinks = this.config.getBoolean(Configuration.CREATE_LINKS);
		this.createLinksAsTargetBlank = this.config.getBoolean(Configuration.CREATE_LINKS_AS_TARGET_BLANK);

		this.numberOfDirs = 0;
		this.numberOfFiles = 0;
		this.duration = 0;

		// Option "Eine HTML-Datei pro Verzeichnis" gewählt:
		if (createMultiDocument) {
			// Versuchen, "subdirs"-Verzeichnis zu erstellen
			this.multidocdir = new File(destinationFile.getParent(),
					Defaults.PROGRAM_NAME + "_" + FileUtils.cleanFileName(this.sourceDirectory.getName()) + "_subdirs");
		}
	}

	/**
	 * Startet den Einlesevorgang.
	 */
	public void process() {
		// Prüfung: Quellverzeichnis vorhanden?
		if (!sourceDirectory.exists()) {
			firePropertyChange("error", null,
					language.getString("error.dirDoesntExist", new String[] { sourceDirectory.getAbsolutePath() }));
			this.cancel = true;
		}
		// Prüfung: Multidoc-Verzeichnis erstellbar?
		if (createMultiDocument) {
			if (!multidocdir.mkdir()) {
				firePropertyChange("error", null,
						language.getString("error.createDir", new String[] { multidocdir.getAbsolutePath() }));
				this.cancel = true;
			}
		}

		// Anfangszeit merken
		long start = new Date().getTime();
		// Verzeichnis einlesen
		String content;
		try {
			content = "<ul>" + getContentAsHtml(this.sourceDirectory) + "</ul>";
		}
		// Zugriff verweigert
		catch (Exception ex) {
			content = "<li>" + language.getString("error.accessDenied") + "</li>";
		}
		// Bearbeitungsdauer ausrechnen
		this.duration = new Date().getTime() - start;
		// HTML erstellen und in Zieldatei schreiben
		if (!this.cancel) {
			if (!FileUtils.writeContentToFile(this.createHtmlDocument(true, content, this.sourceDirectory),
					this.destinationFile)) {
				firePropertyChange("error", null,
						language.getString("error.saveFile", new String[] { this.destinationFile.getAbsolutePath() }));
			}
			firePropertyChange("done", new Boolean(false), new Boolean(true));
		}
	}

	/**
	 * Liest das übergebene Verzeichnis ein und gibt es als HTML-String zurück.
	 * 
	 * @author azimmermann
	 * @param src Quellverzeichnis
	 * @return der fertige HTML-String
	 * @throws Exception wenn etwas schiefgeht
	 */
	private String getContentAsHtml(File src) throws Exception {

		String files = "";
		String dirs = "";
		String path = "";
		String tooltip = "";
		String bitrate = "";
		String info = "";
		String emclass = "";
		String linkbegin = "";
		String targetblank = "";
		String linkend = "";
		boolean isMp3 = false;

		String content = "";

		// Aufklapp-Script
		String onclick = "";
		if (!createMultiDocument)
			onclick = " onclick=\"toggle(this.parentNode);\"";

		// Darstellung des Pfades absolut/relativ
		if (displayFullPath)
			path = src.getPath();
		else
			path = this.sourceDirectory.getName()
					+ src.getPath().substring(this.sourceDirectory.getPath().length(), src.getPath().length());

		// Darstellung der MouseOver-Infos
		if (displayTooltip)
			tooltip = " title=\"" + path + "\"";

		// Darstellung als normales/verstecktes Verzeichnis
		if (src.isHidden())
			emclass = "hiddendirectory";
		else
			emclass = "directory";

		// Verzeichnisnamen zusammenbauen
		content += "<li" + tooltip + "><em class=\"" + emclass + "\"" + onclick + ">" + src.getName() + "</em>\n<ul>\n";

		// jeden Eintrag im Verzeichnis überprüfen
		File currentFile;
		File[] fileList = src.listFiles();
		firePropertyChange("currentdir", null, src.getAbsolutePath());
		Arrays.sort(fileList, new FileComparator());
		// String[] thisDir = src.list();
		int counter = 0; // zählt lesbare und nicht versteckte Dateien+Ordner im
							// aktuellen Verzeichnis
		for (int i = 0; i < fileList.length; i++) {
			currentFile = fileList[i];
			firePropertyChange("currentfile", null, currentFile.getName());

			path = "";
			tooltip = "";
			bitrate = "";
			info = "";
			emclass = "";
			isMp3 = false;
			linkbegin = "";
			targetblank = "";
			linkend = "";

			// Pfadangabe absolut/relativ
			path = displayFullPath ? currentFile.getPath()
					: this.sourceDirectory.getName() + currentFile.getPath()
							.substring(this.sourceDirectory.getPath().length(), currentFile.getPath().length());

			// eigene CSS-Klasse, wenn versteckt
			emclass = currentFile.isHidden() ? " class=\"hiddenfile\"" : "";

			// Ausgabezeile erstellen, wenn Datei
			if (currentFile.isFile() && currentFile.canRead() && !(currentFile.isHidden() && !displayHidden)) {
				this.numberOfFiles++;

				// mp3?
				isMp3 = currentFile.getName().toLowerCase().endsWith(".mp3");

				// Bitrate
				if (this.displayMp3Bitrate && isMp3) {
					try {
						bitrate = String.valueOf((new MP3File(src, currentFile.getName())).getBitrate()) + " kbps";
					} catch (Exception e) {
						bitrate = language.getString("msg.unknownBitrate");
					}
				}

				// Dateigröße, letzte Änderung und Bitrate zusammenbauen
				if (displayFileSize || displayLastChange || (isMp3 && displayMp3Bitrate)) {
					info = " (" //
							+ ((displayFileSize) ? DirToHTMLUtils.byteToString(currentFile.length())//
									+ ((displayLastChange || (isMp3 && displayMp3Bitrate)) ? " / " : "")//
									: "")
							+ ((displayLastChange)
									? DirToHTMLUtils.formatTime(currentFile.lastModified(),
											language.getString("lang.timeformat")) //
											+ ((isMp3 && displayMp3Bitrate) ? " / " : "")//
									: "") //
							+ ((isMp3 && displayMp3Bitrate) ? bitrate : "")//
							+ ")";
				}

				// Tooltip
				if (displayTooltip)
					tooltip = " title=\"" + path + info + "\"";
				else
					tooltip = "";

				// Dateilinks
				if (this.createLinks) {
					if (this.createLinksAsTargetBlank)
						targetblank = " target=\"_blank\"";
					linkbegin = "<a href=\"" + (displayFullPath ? "file://" : "") + FileUtils.cleanFilePathForLink(path)
							+ "\"" + targetblank + ">";
					linkend = "</a>";
				} else {
					linkbegin = "";
					linkend = "";
				}

				// Komplette Zeile zusammensetzen
				files += "<li" + emclass + tooltip + ">" //
						+ linkbegin //
						+ currentFile.getName() //
						+ linkend + (info.equals("") ? "" : "<em class=\"info\">" + info + "</em>") //
						+ "</li>\n";
				counter++;
			}

			// Rekursiver Aufruf, wenn Verzeichnis
			else if (currentFile.isDirectory() && currentFile.canRead()
					&& !(currentFile.isHidden() && !displayHidden)) {
				// Tooltip
				if (displayTooltip)
					tooltip = " title=\"" + path + "\"";
				else
					tooltip = "";
				this.numberOfDirs++;
				// Wenn "Eine HTML-Datei pro Verzeichnis" gewählt wurde
				if (this.createMultiDocument) {
					// Link zusammenbauen
					String linkpath = "";
					if (src.getPath() == this.sourceDirectory.getPath())
						linkpath = this.multidocdir.getName() + "/";
					dirs += "<li" + emclass + tooltip + "><a class=\"directory\" href=\"" + linkpath
							+ String.valueOf(numberOfDirs) + ".html\">" + currentFile.getName() + "</a></li>\n";
					File subdirfile = new File(this.multidocdir, String.valueOf(numberOfDirs) + ".html");
					// Inhalt des Unterverzeichnis einlesen (rekursiver
					// Funktionsaufruf) und als eigene Datei speichern
					String currentdircontent;
					try {
						currentdircontent = "<ul>" + getContentAsHtml(currentFile) + "</ul>";
					}
					// Zugriff verweigert
					catch (Exception ex) {
						currentdircontent = "<li" + emclass + tooltip + ">" + path + ": "
								+ language.getString("error.accessDenied") + "</li>\n";
					}
					FileUtils.writeContentToFile(createHtmlDocument(false, currentdircontent, src), subdirfile);
				}
				// Sonst: Unterverzeichnis einlesen (rekursiver Funktionsaufruf)
				// und Inhalte an bestehenden HTML-String anhängen
				else {
					content += getContentAsHtml(currentFile);
				}
				counter++;
			}
			// Abbruch!
			if (this.cancel) {
				break;
			}
		}
		// leeres Listenelement, wenn keine lesbaren und nicht-versteckten
		// Dateien/Ordner im aktuellen Verzeichnis
		if (counter == 0)
			content += "<li style=\"display:none;\">&nbsp;</li>";
		// sonst: Verzeichnisse vor Dateien
		else
			content += dirs + files;
		// Liste schließen
		content += "</ul>\n</li>\n";
		// Rückgabe des HTML-Strings des aktuellen Verzeichnisses
		return content;
	}

	/**
	 * Erstellt ein valides HTML-Dokument mit den Inhalten des übergebenen
	 * Verzeichnisses.
	 * 
	 * @author azimmermann
	 * @param isrootdir Wurzelverzeichnis?
	 * @param content
	 * @param src       Quellverzeichnis
	 * @return das fertige HTML-Dokument
	 */
	private String createHtmlDocument(boolean isrootdir, String content, File src) {
		String sourcedir;
		String dir = "";
		String dirlink = "";
		// Aufklapp-JavaScript
		String togglescript = "";
		if (!this.createMultiDocument) {
			togglescript = DirToHTMLUtils.getJsTemplate();
		}
		// Pfad absolut/relativ
		if (this.displayFullPath) {
			dir = src.getPath();
			sourcedir = this.sourceDirectory.getPath();
		} else {
			dir = src.getName();
			sourcedir = this.sourceDirectory.getName();
		}
		// im Wurzelverzeichnis: nur Überschrift
		if (isrootdir) {
			dirlink = language.getString("msg.contentOf", new String[] { dir });
		}
		// in Unterverzeichnissen: Link "Eine Ebene nach oben"
		else {
			dirlink = "<a href=\"javascript:history.back()\" title=\""
					+ language.getString("msg.oneLevelUp", new String[] { dir }) + "\">"
					+ language.getString("msg.oneLevelUp", new String[] { dir }) + "</a>\n";
		}
		String programInfo = "";
		if (this.displayProgramInfo) {
			programInfo = "<br /><br /><hr /><div class=\"info\">" + this.getInfoTable(isrootdir) + "</div>\n";
		}
		// Rückgabe des kompletten HTML-Dokuments
		return DirToHTMLUtils.getHtmlTemplate(sourcedir, DirToHTMLUtils.getCssTempate(), togglescript, dirlink, content,
				programInfo);
	}

	/**
	 * Gibt die Programminfos als HTML zurück.
	 * 
	 * @param isrootdir
	 * @return
	 */
	private String getInfoTable(boolean isrootdir) {
		// Infotabelle bauen
		String infotable = "| <a class=\"info\" href=\"" + Defaults.AUTHOR_WEBSITE + "\" target=\"_blank\">"
				+ Defaults.PROGRAM_NAME + " " + Defaults.PROGRAM_VERSION + "</a> |";
		if (isrootdir) {
			String durationDesc = this.cancel
					? language.getString("msg.processCancelledAfter",
							new String[] { DirToHTMLUtils.millisecondToSecond(this.duration) })
					: language.getString("msg.processDuration",
							new String[] { DirToHTMLUtils.millisecondToSecond(this.duration) });
			infotable += " " //
					+ language.getString("msg.createDate",
							new String[] { DirToHTMLUtils.formatTime((new Date()).getTime(),
									language.getString("lang.timeformat")) })
					+ " | " //
					+ durationDesc + " | " //
					+ language.getString("msg.directories", new String[] { String.valueOf(numberOfDirs) }) + " | " //
					+ language.getString("msg.files", new String[] { String.valueOf(numberOfFiles) }) + " |";
		}
		// Rückgabe der Infotabelle
		return infotable;
	}

	/**
	 * Bricht den Einlesevorgang ab.
	 */
	public void cancelProcessing() {
		this.cancel = true;
	}
}
