package interface_adapters;

public class ViewManagerState {
    private String curViewName;
    public ViewManagerState() {
        this.curViewName = "Homepage";
    }
    public String getCurViewName() {
        return curViewName;
    }
    public void setCurViewName(String newViewName) {
        curViewName = newViewName;
    }
}
