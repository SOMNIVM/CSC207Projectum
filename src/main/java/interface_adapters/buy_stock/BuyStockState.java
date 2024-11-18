package interface_adapters.buy_stock;

import interface_adapters.ModifyPortfolioState;

public class BuyStockState extends ModifyPortfolioState {
    private double buyingPrice;
    public BuyStockState() {
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
