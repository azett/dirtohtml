package com.azett.dirtohtml.processing.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.azett.dirtohtml.processing.model.AbstractModel;
import com.azett.dirtohtml.processing.view.AbstractView;

/**
 * Abstrakte Controller-Klasse für das MVC-Pattern
 * 
 * @author azimmermann
 * 
 */
public abstract class AbstractController implements PropertyChangeListener {

    private ArrayList registeredViews;
    private ArrayList registeredModels;

    public AbstractController() {
        registeredViews = new ArrayList();
        registeredModels = new ArrayList();
    }

    public void addModel(AbstractModel model) {
        registeredModels.add(model);
        model.addPropertyChangeListener(this);
    }

    public void removeModel(AbstractModel model) {
        registeredModels.remove(model);
        model.removePropertyChangeListener(this);
    }

    public void addView(AbstractView view) {
        registeredViews.add(view);
    }

    public void removeView(AbstractView view) {
        registeredViews.remove(view);
    }

    // Use this to observe property changes from registered models
    // and propagate them on to all the views.

    public void propertyChange(PropertyChangeEvent evt) {
        for (int i = 0; i < registeredViews.size(); i++) {
            ((AbstractView) registeredViews.get(i)).modelPropertyChange(evt);
        }

    }

    /**
     * This is a convenience method that subclasses can call upon to fire
     * property changes back to the models. This method uses reflection to
     * inspect each of the model classes to determine whether it is the owner of
     * the property in question. If it isn't, a NoSuchMethodException is thrown,
     * which the method ignores.
     * 
     * @param propertyName
     *            = The name of the property.
     * @param newValue
     *            = An object that represents the new value of the property.
     */
    protected void setModelProperty(String propertyName, Object newValue) {

        for (int i = 0; i < registeredModels.size(); i++) {
            AbstractModel model = (AbstractModel) registeredModels.get(i);
            try {

                Method method = model.getClass().getMethod(
                        "set" + propertyName,
                        new Class[] { newValue.getClass() }

                );
                method.invoke(model, new Object[] { newValue });

            } catch (Exception ex) {
                // Handle exception.
            }
        }
    }
}
