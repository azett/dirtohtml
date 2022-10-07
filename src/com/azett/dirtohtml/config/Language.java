package com.azett.dirtohtml.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import com.azett.dirtohtml.ui.UiUtils;

/**
 * Verwaltung der Lokalisierung
 * 
 * @author azimmermann
 */
public class Language {

    private Properties properties;

    /**
     * @author azimmermann
     */
    public Language() {
        this.properties = new Properties();
        String language = Configuration.getConfiguration().getString("language");
        // Keine Sprache konfiguriert? Sprachwahl-Dialog zeigen
        if ((language == null) || language.equals("")) {
            language = UiUtils.showLanguageDialog(true);
        }
        // Sprachdatei laden
        File languageFile = new File(Defaults.LANGUAGE_DIR, Configuration.getConfiguration().getString("language")
                + Defaults.PROPFILE_EXT);
        try {
            this.properties.load(new FileInputStream(languageFile));
        } catch (FileNotFoundException e) {
            UiUtils.showErrorDialog(Defaults.PROGRAM_NAME + " - Error on startup", "Language file "
                    + languageFile.getAbsolutePath() + " not found! Please check your configuration.");
            System.exit(0);
        } catch (IOException e) {
            UiUtils.showErrorDialog(Defaults.PROGRAM_NAME + " - Error on startup", "Unable to read language file "
                    + languageFile.getAbsolutePath() + "! Please check accessibility.");
            System.exit(0);
        }
    }

    /**
     * Gibt lokalisierte Phrase zum übergebenen Key zurück.
     * 
     * @author azimmermann
     * @param key
     * @return
     */
    public String getString(String key) {
        return this.properties.getProperty(key, "LANG_ERROR").trim();
    }

    /**
     * Gibt lokalisierte Phrase zum übergebenen Key zurück. Die Parameter ersetzen die Platzhalter ({0}, {1}, ...).
     * 
     * @author azimmermann
     * @param key
     * @param params
     * @return
     */
    public String getString(String key, String[] params) {
        return MessageFormat.format(this.properties.getProperty(key, "LANG_ERROR").trim(), params);
    }

}
