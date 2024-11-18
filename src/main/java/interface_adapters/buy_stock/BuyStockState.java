package interface_adapters.buy_stock;

public class BuyStockState {
    private double buyingPrice;
    private boolean isValidInput;
    private String errorMessage;
    public BuyStockState() {
        this.buyingPrice = 0.0;
        this.isValidInput = true;
        this.errorMessage = "";
    }
    public boolean checkIfValid() {
        return isValidInput;
    }
    public void setBuyingPrice(double price) {
        buyingPrice = price;
    }
    public void setAsValid() {
        isValidInput = true;
    }
    public void setAsInvalid(String errorDescription) {
        isValidInput = false;
        errorMessage = errorDescription;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public double getBuyingPrice() {
        return buyingPrice;
    }
}
