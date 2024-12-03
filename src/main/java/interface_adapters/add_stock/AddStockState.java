package interface_adapters.add_stock;

import interface_adapters.ModifyPortfolioState;

/**
 * The state for the add stock use case.
 */
public class AddStockState extends ModifyPortfolioState {
    private double currentPrice;

    public AddStockState() {
        this.currentPrice = 0.0;
    }

    public void setCurrentPrice(double price) {
        currentPrice = price;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }
}
