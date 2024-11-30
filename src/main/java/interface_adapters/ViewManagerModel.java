package interface_adapters;

/**
 * Model for the View Manager. Its state is the name of the View which
 * is currently active. An initial state of "Homepage" is used.
 */
public class ViewManagerModel extends ViewModel<ViewManagerState> {
    public ViewManagerModel() {
        super("view manager", new ViewManagerState());
    }
}
