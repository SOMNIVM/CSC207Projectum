package interface_adapters;

/**
 * The state characterizing the modification to portfolio.
 */
public class ModifyPortfolioState {
    private String stockName;
    private int sharesChanged;
    private boolean isValidInput;
    private String errorMessage;

    public ModifyPortfolioState() {
        this.stockName = "";
        this.sharesChanged = 0;
        this.isValidInput = true;
        this.errorMessage = "";
    }

    public void setStockName(String newName) {
        this.stockName = newName;
    }

    public void setSharesChanged(int newShares) {
        this.sharesChanged = newShares;
    }

    /**
     * Check whether the use case is handling a valid input.
     * @return true if the use case is handling a valid input.
     */
    public boolean checkIfValid() {
        return isValidInput;
    }

    /**
     * Mark the use case as handling a valid input.
     */
    public void setAsValid() {
        isValidInput = true;
    }

    /**
     * Mark the uee case as handling an invalid input.
     * @param errorDescription a description of the error.
     */
    public void setAsInvalid(String errorDescription) {
        isValidInput = false;
        errorMessage = errorDescription;
    }

    public String getStockName() {
        return stockName;
    }

    public int getSharesChanged() {
        return sharesChanged;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
