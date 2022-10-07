package com.azett.dirtohtml.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.azett.dirtohtml.ui.UiUtils;

/**
 * @author azimmermann
 */
public class Configuration {

    private static Configuration configuration;
    private Properties           properties;

    // i18n
    private Language             language;

    // UI zeigen?
    private int                  outputType;

    // Optionen
    public static final String   CREATE_MULTI_DOCUMENT        = "createMultiDocument";
    public static final String   DISPLAY_HIDDEN               = "displayHidden";
    public static final String   DISPLAY_FULL_PATH            = "displayFullPath";
    public static final String   DISPLAY_TOOLTIP              = "displayTooltip";
    public static final String   DISPLAY_PROGRAM_INFO         = "displayProgramInfo";
    public static final String   DISPLAY_FILE_SIZE            = "displayFileSize";
    public static final String   DISPLAY_LAST_CHANGE          = "displayLastChange";
    public static final String   DISPLAY_MP3_BITRATE          = "displayMp3Bitrate";
    public static final String   CREATE_LINKS                 = "createLinks";
    public static final String   CREATE_LINKS_AS_TARGET_BLANK = "createLinksAsTargetBlank";

    /**
     * Gibt die Singleton-Instanz der Configuration zurück.
     * 
     * @author azimmermann
     * @return
     */
    public static Configuration getConfiguration() {
        if (configuration == null) {
            configuration = new Configuration();
        }
        return configuration;
    }

    /**
     * @author azimmermann
     */
    public Configuration() {
        this.initialize();
    }

    /**
     * Initialisiert das lokale {@link Properties}-Objekt.
     */
    private void initialize() {
        this.properties = new Properties();
        try {
            this.properties.load(new FileInputStream(Defaults.CONFIG_FILE));
        }
        // Noch kein Config-File vorhanden?
        catch (Exception e) {
            // Defaultwerte setzen
            this.setString("language", UiUtils.showLanguageDialog(false));
            this.saveToFile();
            // Config schreiben
            this.initialize();
        }
    }

    /**
     * Gibt die Option zum übergebenen Key als String zurück.
     * 
     * @author azimmermann
     * @param key
     * @return
     */
    public String getString(String key) {
        return this.properties.getProperty(key);
    }

    /**
     * Gibt die Option zum übergebenen Key als Boolean zurück.
     * 
     * @author azimmermann
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
        return (this.properties.containsKey(key) && (this.properties.getProperty(key).equals("true")
                || this.properties.getProperty(key).equals("on") || this.properties.getProperty(key).equals("yes") || this.properties
                .getProperty(key).equals("1"))) ? true : false;
    }

    /**
     * Schreibt die String-Option mit dem übergebenen Key.
     * 
     * @author azimmermann
     * @param key
     * @param value
     * @return
     */
    public void setString(String key, String value) {
        this.properties.setProperty(key, value);
    }

    /**
     * Schreibt die Boolean-Option mit dem übergebenen Key.
     * 
     * @author azimmermann
     * @param key
     * @param value
     * @return
     */
    public void setBoolean(String key, boolean value) {
        String valueString = value ? "true" : "false";
        this.properties.setProperty(key, valueString);
    }

    /**
     * Speichert die Konfiguration ins Config-File.
     * 
     * @author azimmermann
     */
    public void saveToFile() {
        try {
            this.properties.store(new FileOutputStream(Defaults.CONFIG_FILE), Defaults.PROGRAM_NAME + " "
                    + Defaults.PROGRAM_VERSION);
        } catch (IOException e) {
            UiUtils.showErrorDialog(language.getString("error.ioError"), language.getString("error.saveFile",
                    new String[] { Defaults.CONFIG_FILE.getAbsolutePath() }));
        }
    }

    /**
     * Gibt das Language-Objekt zurück.
     * 
     * @author azimmermann
     * @return
     */
    public Language getLanguage() {
        return this.language;
    }

    /**
     * Setzt das Language-Objekt.
     * 
     * @author azimmermann
     * @param language
     */
    public void setLanguage(Language language) {
        this.language = language;
    }

    /**
     * Setzt den Output-Type.
     * 
     * @param outputType
     */
    public void setOutputType(int outputType) {
        this.outputType = outputType;
    }

    /**
     * Gibt den Output-Type zurück.
     * 
     * @return
     */
    public int getOutputType() {
        return outputType;
    }

}
