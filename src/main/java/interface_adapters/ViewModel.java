package interface_adapters;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * A superclass for all view models.
 * @param <T> The type parameter should be a state object type.
 */
public class ViewModel<T> {
    private final String viewName;
    private final T state;
    private final PropertyChangeSupport support;

    public ViewModel(String viewName, T state) {
        this.viewName = viewName;
        this.state = state;
        this.support = new PropertyChangeSupport(this);
    }

    public String getViewName() {
        return viewName;
    }

    public T getState() {
        return this.state;
    }

    /**
     * Fire the property change of a state in general.
     */
    public void firePropertyChange() {
        this.support.firePropertyChange("state", null, state);
    }

    /**
     * Fire the property change of a property with a specific name.
     * @param propertyName the name of the property to be changed.
     */
    public void firePropertyChange(String propertyName) {
        this.support.firePropertyChange(propertyName, null, state);
    }

    /**
     * Add a property change listener to this view model
     * so that the property changes triggered by this model can be handled promptly.
     * @param listener the property change listener.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }
}
