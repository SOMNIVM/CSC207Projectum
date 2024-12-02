package usecases.buy_stock;

public class AddStockOutputData {
    private final String stockName;
    private final int sharesPurchased;
    private final double currentPrice;

    public AddStockOutputData(String stockName, int sharesPurchased, double currentPrice) {
        this.stockName = stockName;
        this.sharesPurchased = sharesPurchased;
        this.currentPrice = currentPrice;
    }
    public String getStockName() {
        return stockName;
    }
    public int getSharesPurchased() {
        return sharesPurchased;
    }
    public double getCurrentPrice() {
        return currentPrice;
    }
}
