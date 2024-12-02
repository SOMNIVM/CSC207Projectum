package interface_adapters.add_stock;

import interface_adapters.ModifyPortfolioState;

public class AddStockState extends ModifyPortfolioState {
    private double currentPrice;
    public AddStockState() {
        super();
        this.currentPrice = 0.0;
    }
    public void setCurrentPrice(double price) {
        currentPrice = price;
    }
    public double getCurrentPrice() {
        return currentPrice;
    }
}
