package interface_adapters;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

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
    public void firePropertyChange() {
        this.support.firePropertyChange("state", null, state);
    }
    public void firePropertyChange(String propertyName) {
        this.support.firePropertyChange(propertyName, null, state);
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }
}