package usecases.buy_stock;

public class BuyStockInputData {
    private final String stockName;
    private final int sharesToBuy;
    public BuyStockInputData(String stockName, int sharesToBuy) {
        this.stockName = stockName;
        this.sharesToBuy = sharesToBuy;
    }
    public String getStockName() {
        return stockName;
    }
    public int getSharesToBuy() {
        return sharesToBuy;
    }
}
