package com.azett.dirtohtml.processing.view;

import java.beans.PropertyChangeEvent;

import com.azett.dirtohtml.config.Configuration;
import com.azett.dirtohtml.config.Defaults;
import com.azett.dirtohtml.config.Language;
import com.azett.dirtohtml.processing.model.AbstractModel;

/**
 * Die Console-View für das MVC-Pattern
 * 
 * @author azimmermann
 * 
 */
public class ConsoleView extends AbstractView {

    private AbstractModel model;

    // Config und Sprache
    private Configuration config   = Configuration.getConfiguration();
    private Language      language = config.getLanguage();

    /**
     * Konstruktor
     */
    public ConsoleView(AbstractModel model) {
        System.out.println(Defaults.PROGRAM_NAME + " "
                + Defaults.PROGRAM_VERSION);
        System.out.println(language.getString("msg.readFiles") + "...");
    }

    public void onCancel(final PropertyChangeEvent evt) {
    }

    public void onError(final PropertyChangeEvent evt) {
        System.err.println(language.getString("msg.cancel"));
        System.err.println(evt.getNewValue().toString());
        System.exit(0);
    }

    public void onCurrentDir(final PropertyChangeEvent evt) {
        System.out.println(evt.getNewValue().toString());
    }

    public void onCurrentFile(final PropertyChangeEvent evt) {
    }

    public void onDone(final PropertyChangeEvent evt) {
        System.out.println(language.getString("msg.done"));
    }
}
