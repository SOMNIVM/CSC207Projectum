package interface_adapters.reset_portfolio;

public class ClearAllState {
    private final String messageUponClearing;
    private boolean cleared;
    public ClearAllState() {
        this.messageUponClearing = "Your portfolio has been successfully cleared.";
        this.cleared = false;
    }
    public String getMessageUponClearing() {
        return messageUponClearing;
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
