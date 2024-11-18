package interface_adapters.reset_portfolio;

public class ClearAllState {
    private boolean cleared;
    public ClearAllState() {
        this.cleared = false;
    }
    public boolean checkIfCleared(){
        return cleared;
    }
    public void clear() {
        cleared = true;
    }
    public void unclear() {
        cleared = false;
    }
}
