package com.azett.dirtohtml.processing.view;

import java.beans.PropertyChangeEvent;

/**
 * Abstrakte View-Klasse für das MVC-Pattern
 * 
 * @author azimmermann
 * 
 */
public abstract class AbstractView {

    /**
     * @param evt
     */
    public void modelPropertyChange(final PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("error")) {
            this.onError(evt);
        } else if (evt.getPropertyName().equals("cancel")) {
            this.onCancel(evt);
        } else if (evt.getPropertyName().equals("done")) {
            this.onDone(evt);
        } else if (evt.getPropertyName().equals("currentdir")) {
            this.onCurrentDir(evt);
        } else if (evt.getPropertyName().equals("currentfile")) {
            this.onCurrentFile(evt);
        }
        // Remainder of the code omitted
    }

    public void onCancel(final PropertyChangeEvent evt) {
    }

    public void onError(final PropertyChangeEvent evt) {
    }

    public void onCurrentDir(final PropertyChangeEvent evt) {
    }

    public void onCurrentFile(final PropertyChangeEvent evt) {
    }

    public void onDone(final PropertyChangeEvent evt) {
    }
}
