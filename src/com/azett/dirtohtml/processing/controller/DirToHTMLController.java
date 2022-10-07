package com.azett.dirtohtml.processing.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import com.azett.dirtohtml.config.Configuration;
import com.azett.dirtohtml.config.Defaults;
import com.azett.dirtohtml.processing.model.DirToHTMLModel;
import com.azett.dirtohtml.processing.view.AbstractView;
import com.azett.dirtohtml.processing.view.ConsoleView;
import com.azett.dirtohtml.processing.view.SwingView;

/**
 * Der Controller für das MVC-Pattern
 * 
 * @author azimmermann
 * 
 */
public class DirToHTMLController extends AbstractController implements Runnable {

    private AbstractView   view;
    private DirToHTMLModel model;

    private Configuration  config = Configuration.getConfiguration();

    /**
     * Startet den Einlesevorgang für das übergebene Verzeichnis
     * 
     * @param srcDir
     * @param destFile
     */
    public DirToHTMLController(File srcDir, File destFile) {
        this.model = new DirToHTMLModel(srcDir, destFile);
        addModel(this.model);

        // View abhängig vom Output-Type setzen
        switch (config.getOutputType()) {
        // "silent"
        case Defaults.OUTPUT_TYPE_NONE:
            // keine View registrieren
            break;
        // Standard-Console
        case Defaults.OUTPUT_TYPE_CONSOLE:
            this.view = new ConsoleView(this.model);
            addView(this.view);
            break;
        // Swing-UI
        case Defaults.OUTPUT_TYPE_SWING:
            this.view = new SwingView(this.model);
            addView(this.view);
            break;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Runnable#run()
     */
    public void run() {
        // Einlesen starten
        model.process();
    }

    /**
     * ActionListener für Abbruch
     * 
     * @author azimmermann
     * 
     */
    class CancelListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            model.cancelProcessing();
        }
    }

}
