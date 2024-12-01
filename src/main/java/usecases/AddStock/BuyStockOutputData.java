package usecases.AddStock;

public class BuyStockOutputData {
    private final String stockName;
    private final int sharesPurchased;
    private final double buyingPrice;

    public BuyStockOutputData(String stockName, int sharesPurchased, double buyingPrice) {
        this.stockName = stockName;
        this.sharesPurchased = sharesPurchased;
        this.buyingPrice = buyingPrice;
    }
    public String getStockName() {
        return stockName;
    }
    public int getSharesPurchased() {
        return sharesPurchased;
    }
    public double getBuyingPrice() {
        return buyingPrice;
    }
}
