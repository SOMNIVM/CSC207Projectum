package interface_adapters.reset_portfolio;

/**
 * The state characterizing the reset portfolio use case.
 */
public class ClearAllState {
    private boolean cleared;

    public ClearAllState() {
        this.cleared = false;
    }

    /**
     * Check if the portfolio has been cleared.
     * @return true if the portfolio has been cleared.
     */
    public boolean checkIfCleared() {
        return cleared;
    }

    /**
     * Mark the user's portfolio as cleared.
     */
    public void clear() {
        cleared = true;
    }

    /**
     * Mark the user's portfolio as uncleared.
     */
    public void unclear() {
        cleared = false;
    }
}
