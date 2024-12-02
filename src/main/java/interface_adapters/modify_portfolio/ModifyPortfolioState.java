
package interface_adapters.modify_portfolio;

// ...existing code...

public class ModifyPortfolioState {
    private String stockName;
    private int sharesChanged;
    private boolean isValid;
    private String error;

    // ...existing getters and setters...

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public void setSharesChanged(int sharesChanged) {
        this.sharesChanged = sharesChanged;
    }

    public void setAsValid() {
        this.isValid = true;
        this.error = "";
    }

    public void setAsInvalid(String error) {
        this.isValid = false;
        this.error = error;
    }

    // ...existing code...
}