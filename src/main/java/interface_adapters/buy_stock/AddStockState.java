package interface_adapters.buy_stock;

import interface_adapters.ModifyPortfolioState;

public class AddStockState extends ModifyPortfolioState {
    private double buyingPrice;
    public AddStockState() {
        super();
        this.buyingPrice = 0.0;
    }
    public void setBuyingPrice(double price) {
        buyingPrice = price;
    }
    public double getBuyingPrice() {
        return buyingPrice;
    }
}
