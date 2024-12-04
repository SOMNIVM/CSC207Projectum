package interface_adapters;

/**
 * The object characterizing the state of the view manager.
 */
public class ViewManagerState {
    private String curViewName;

    public ViewManagerState() {
        this.curViewName = "homepage";
    }

    public String getCurViewName() {
        return curViewName;
    }

    public void setCurViewName(String newViewName) {
        curViewName = newViewName;
    }
}
