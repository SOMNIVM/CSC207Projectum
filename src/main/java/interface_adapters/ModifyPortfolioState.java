package interface_adapters;

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
    public boolean checkIfValid() {
        return isValidInput;
    }
    public void setAsValid() {
        isValidInput = true;
    }
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
